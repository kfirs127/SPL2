package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Diary;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl INSTANCE=null;
	private HashMap< MicroService,  BlockingQueue<Message>> queues;
	private HashMap<Class<? extends Message>, BlockingQueue< MicroService>> subscribe;
	private HashMap<Message, BlockingQueue<Future>> futureObjects;
	private AtomicInteger subs;
	private Diary diary;

	public synchronized static MessageBusImpl getInstance(){
		if(INSTANCE==null)
			INSTANCE=new MessageBusImpl();
		return INSTANCE;
	}
	private MessageBusImpl(){
		queues= new HashMap<>();
		subscribe=new HashMap<>();
		futureObjects=new HashMap<>();
		subs=new AtomicInteger();
		subs.set(0);
		diary =Diary.getInstance();
	}

	@Override
	public synchronized <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m)  {
		subscribe(type,m);
		subs.compareAndSet(subs.get(),subs.get()+1);
		if(subs.get()==4)
			start(true);
	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {

		subscribe (type,m);
    }

	@Override @SuppressWarnings("unchecked")
	public synchronized  <T> void complete(Event<T> e, T result) {
		Iterator<Future> iter=futureObjects.get(e).iterator();
		while(iter.hasNext()){
			iter.next().resolve(result);
		}
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {

		if(!futureObjects.containsKey(b))
			futureObjects.put(b, new LinkedBlockingQueue<Future>());
		for(MicroService micro: subscribe.get(b))
			queues.get(micro).add(b);
			Future ret=new Future();
			futureObjects.get(b).add(ret);
		}

	@Override
	public synchronized <T> Future<T> sendEvent(Event<T> e) {
	//	if(subs.get()<4)
	//		start(false); //make leia start only after everyone are subscribe to queues
		MicroService m=subscribe.get(e.getClass()).poll();
		try {subscribe.get(e.getClass()).put(m);}
		catch (InterruptedException interruptedException) {}
		queues.get(m).add(e);
		Future ret=new Future();
		futureObjects.put(e, new LinkedBlockingQueue<Future>());
		futureObjects.get(e).add(ret);
		return ret;
	}

	@Override
	public synchronized void register(MicroService m) {
		queues.put(m, new LinkedBlockingQueue<>(3));
	}

	@Override
	public void unregister(MicroService m) {
		queues.remove(m.getClass());
	}

	@Override
	public Message awaitMessage(MicroService m){

		Iterator<Map.Entry<MicroService, BlockingQueue<Message>>> iter=queues.entrySet().iterator();
		try {
			return queues.get(m).take(); //returns the first message available
		}
		catch (InterruptedException e) {
			return null;
		}
	}

	private synchronized <T> void subscribe(Class<? extends Message> type, MicroService m){
		System.out.println("try to subscribe " + m.getClass()+ " to class "+ type);
		if (subscribe.containsKey(type)) {
			try {
				subscribe.get(type).put(m);
			} catch (InterruptedException e) {}
		}
		else {
			subscribe.put(type, new LinkedBlockingQueue<MicroService>() {
			});
		}
	}
	private synchronized void start(boolean val){

		if(!val) {
			try {
				wait();
			}
			catch (InterruptedException e){}
		}
		else
			notifyAll(); //will notify leia's thread it can start send messages after everyone are subscribed.
	}
}
