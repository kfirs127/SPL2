package bgu.spl.mics;

import bgu.spl.mics.application.messages.StartMessage;
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
	private AtomicInteger completed;
	private AtomicInteger subs;
	private Diary diary;

	public synchronized static MessageBusImpl getInstance(){
		if(INSTANCE==null)
			INSTANCE=new MessageBusImpl();
		return INSTANCE;
	}
	private MessageBusImpl(){
		completed=new AtomicInteger(0);
		queues= new HashMap<>();
		subscribe=new HashMap<>();
		futureObjects=new HashMap<>();
		subs=new AtomicInteger();
		subs.set(0);
		diary =Diary.getInstance();
	}

	@Override
	public synchronized  <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m)  {
		subscribe(type, m);
		subs.compareAndSet(subs.get(),subs.get()+1);
		System.out.println("subscribers: "+subs);
		System.out.println(m.getName() + " subscribe to "+type + " events");
		if(subs.get()==4) {
			System.out.println(" there are 4 thread subscribed so call leia up");
			start(true);
		}
	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		System.out.println(m.getName() + " subscribe to "+type + " events");
		subscribe (type,m);
    }

	@Override @SuppressWarnings("unchecked")
	public  <T> void complete(Event<T> e, T result) {
		System.out.println("resolve "+e.getClass() +" event.  the resolt is: " + result.toString());
		Iterator<Future> iter=futureObjects.get(e).iterator();
		while(iter.hasNext()){
			iter.next().resolve(result);
		}
		while(!completed.compareAndSet(completed.get(),completed.get()+1));
		System.out.println("completed tasks: "+completed);

	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {

		System.out.println("broadcast of type "+ b.getClass() + " has sent.");
	//	if(!futureObjects.containsKey(b))
	//		futureObjects.put(b, new LinkedBlockingQueue<Future>());
		System.out.println("num of subscribers to broadcast "+b.getClass().getName() + " is : "+ subscribe.get(b.getClass()).size());
		for(MicroService micro: subscribe.get(b.getClass())) {
			queues.get(micro).add(b);
			System.out.println("added broadcast of type "+ b.getClass()+ " to "+ micro.getName());
		}
			Future ret=new Future();
			futureObjects.get(b).add(ret);
		}

	@Override
	public synchronized  <T> Future<T> sendEvent(Event<T> e) {

		if (e.getClass().equals(StartMessage.class) && subs.get() < 4) {
			System.out.println("not all subscribers registerd so call start function");
			start(false); //make leia start only after everyone are subscribe to queues
			return null;
		}
		//System.out.println("to message type " + e.getClass() + " num of subscribers is: " + subscribe.get(e.getClass()).size());
		//System.out.println("num of subscribers to class" + e.getClass().getName() + " is " + subscribe.get(e.getClass()).size());
		else {
			MicroService m = subscribe.get(e.getClass()).peek();
			System.out.println(m.getName() + " chosen from the queue's head");
			try {
				subscribe.get(e.getClass()).put(subscribe.get(e.getClass()).take());
				System.out.println("Readd " + m.getName() + " to the event " + e.getClass() + " subscribers queue");
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}

			//System.out.println(" add to queue of " + m.getName() + e.getClass().getName() + " event");
			queues.get(m).add(e);
			//System.out.println("send event " + e.getClass() + " to " + m.getName() + " queue");
			Future ret = new Future();
			futureObjects.put(e, new LinkedBlockingQueue<Future>());
			futureObjects.get(e).add(ret);
			return ret;
		}
	}

	@Override
	public synchronized void register(MicroService m) {
		queues.put(m, new LinkedBlockingQueue<>());
		System.out.println(m.getName() +" registered to messageBus");

	}

	@Override
	public void unregister(MicroService m) {
		queues.remove(m.getClass());
		System.out.println(m.getName() +" registered to messageBus");
	}

	@Override
	public Message awaitMessage(MicroService m){
		System.out.println(m.getName()+ " awaits message ");
		try {
				synchronized (queues.get(m)) {
				Message mess = queues.get(m).take(); //returns the first message available
			//	System.out.println(" returns message" + mess.toString() + "to" + m.getName());
				return mess;
			}
			}
		catch(InterruptedException e){
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
			try {
				subscribe.put(type, new LinkedBlockingQueue<MicroService>());
				subscribe.get(type).put(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	//	System.out.println(m.getName()+ " succeeded to subscribe "+ type.getName());
	//	System.out.println("number of subscribers to event "+type.getName()+" is now "+ subscribe.get(type).size() );
	}
	private void start(boolean val) {

		if (!val) {
			try {
				System.out.println("leia waits in thread number " + Thread.currentThread().getName());
				wait();
			} catch (InterruptedException e) {
			}
		} else {
			System.out.println(Thread.currentThread().getName() + " wakes leia up ");
			notifyAll(); //will notify leia's thread it can start send messages after everyone are subscribed.
		}
	}
}
