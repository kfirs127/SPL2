package bgu.spl.mics;

import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.services.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private static MessageBusImpl INSTANCE = null;
	private HashMap< MicroService,  BlockingQueue<Message>> queues;
	private HashMap<Class<? extends Message>, BlockingQueue< MicroService>> subscribe;
	private HashMap<Message, BlockingQueue<Future>> futureObjects;
	private AtomicInteger completed;
	private AtomicInteger subs;
	private Diary diary;

	public synchronized static MessageBusImpl getInstance(){
		if(INSTANCE == null) INSTANCE = new MessageBusImpl();
		return INSTANCE;
	}
	private MessageBusImpl(){
		completed = new AtomicInteger(0);
		queues = new HashMap<>();
		subscribe = new HashMap<>();
		futureObjects = new HashMap<>();
		subs = new AtomicInteger();
		subs.set(0);
		diary = Diary.getInstance();
	}

	@Override
	public synchronized  <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m){
		subscribe(type, m);
		subs.compareAndSet(subs.get(),subs.get()+1);
		if(subs.get()==4) start(true);
	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) { subscribe (type,m); }

	@Override @SuppressWarnings("unchecked")
	public  <T> void complete(Event<T> e, T result) {
		Iterator<Future> iter=futureObjects.get(e).iterator();
		while(iter.hasNext()){
			iter.next().resolve(result);
		}
		while(!completed.compareAndSet(completed.get(),completed.get()+1));
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		for (MicroService micro : subscribe.get(b.getClass())) {
			queues.get(micro).add(b);
		}
	}

	@Override
	public synchronized  <T> Future<T> sendEvent(Event<T> e) {
		if (e.getClass().equals(StartMessage.class) && subs.get() < 4) { //make leia start only after everyone are subscribe to queues
			start(false);
			return null;
		}
		if(subscribe.get(e.getClass())==null)
			throw new NullPointerException("no subscribers to send to");
			MicroService m = subscribe.get(e.getClass()).peek();
		try {
			subscribe.get(e.getClass()).put(subscribe.get(e.getClass()).take());
		}
		catch (InterruptedException interruptedException) {
			System.out.println("exception in messageBus send event");
		}
		queues.get(m).add(e);
		Future ret = new Future();
		futureObjects.put(e, new LinkedBlockingQueue<Future>());
		futureObjects.get(e).add(ret);
		return ret;
	}

	@Override
	public synchronized void register(MicroService m) { queues.put(m, new LinkedBlockingQueue<>()); }

	@Override
	public void unregister(MicroService m) {
			for (Map.Entry<Class<? extends Message>, BlockingQueue<MicroService>> pair : subscribe.entrySet()) {
				if (subscribe.get(pair.getKey()).contains(m)) {
					subscribe.get(pair.getKey()).remove(m);
				}
			}
			if (queues.containsKey(m)) {
				queues.remove(m);
			}
			subs.compareAndSet(subs.get(), subs.get() - 1);

		/*
		if(m.getClass().equals(HanSoloMicroservice.class)) {
			String str=AttackEvent.class.getName();
			int str1=subscribe.get(AttackEvent.class).size();
			if(subscribe.get(AttackEvent.class).contains(m))
				subscribe.get(AttackEvent.class).remove(m);
		}
		else if(m.getClass().equals(C3POMicroservice.class)){
			if(subscribe.get(AttackEvent.class).contains(m))
				subscribe.get(AttackEvent.class).remove(m);
		}
		else if(m.getClass().equals(R2D2Microservice.class)) {
			if(subscribe.get(DeactivationEvent.class).contains(m))
				subscribe.get(DeactivationEvent.class).remove(m);
		}
		else if(m.getClass().equals(LandoMicroservice.class)) {
			if(subscribe.get(ExploseEvent.class).contains(m))
				subscribe.get(ExploseEvent.class).remove(m);
		}
		if (subscribe.get(TerminateMessage.class).contains(m))
				subscribe.get(TerminateMessage.class).remove(m);
*/
	}

	@Override
	public Message awaitMessage(MicroService m){
		try {
			if(!queues.containsKey(m))
				return null;
			synchronized (queues.get(m)) {
				Message mess = queues.get(m).take(); //returns the first message available
				return mess;
			}
		}
		catch(InterruptedException e){
			System.out.println("exception in messageBus await message");
			return null;
		}
	}

	private synchronized <T> void subscribe(Class<? extends Message> type, MicroService m){
		if (subscribe.containsKey(type)){
			try {
				subscribe.get(type).put(m);
			}
			catch (InterruptedException e) { System.out.println("exception in messageBus subscribe"); }
		}
		else {
			try {
				subscribe.put(type, new LinkedBlockingQueue<MicroService>());
				subscribe.get(type).put(m);
			}
			catch (InterruptedException e) {
				System.out.println("exception in messageBus subscribe");
			}
		}
	}

	private void start(boolean val) {
		if (!val) {
			try {
				wait();
			} catch (InterruptedException e) {System.out.println("exception in msgbs start");}
		}
		else {
			notifyAll(); //will notify leia's thread it can start send messages after everyone are subscribed.
		}
	}
}
