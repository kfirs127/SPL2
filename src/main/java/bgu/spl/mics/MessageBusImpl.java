package bgu.spl.mics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl INSTANCE;
	private HashMap< MicroService, Queue<Message>> queues;
	private HashMap<Class<? extends Message>, BlockingQueue< MicroService>> subscribe;
	private HashMap<Message, BlockingQueue<Future>> futureObjects;

	public static MessageBusImpl getInstance(){
		if(INSTANCE==null)
			INSTANCE=new MessageBusImpl();
		return INSTANCE;
	}
	private MessageBusImpl(){
		INSTANCE=null;
		queues= new HashMap<>();
		subscribe=new HashMap<>();
		futureObjects=new HashMap<>();
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m)  {

		subscribe(type,m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {

		subscribe (type,m);
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {

		Iterator<Future> iter=futureObjects.get(e).iterator();
		while(iter.hasNext()){
			iter.next().resolve(result);
		}
		notifyAll();
	}

	@Override
	public void sendBroadcast(Broadcast b) {

		if(!futureObjects.containsKey(b))
			futureObjects.put(b, new LinkedBlockingQueue<Future>());
		for(MicroService micro: subscribe.get(b))
			queues.get(micro).add(b);
			Future ret=new Future();
			futureObjects.get(b).add(ret);
		}

	@Override
	public <T> Future<T> sendEvent(Event<T> e) {

		MicroService m=subscribe.get(e.getClass()).remove();
		queues.get(m).add(e);
		Future ret=new Future();
		futureObjects.put(e, new LinkedBlockingQueue<Future>());
		futureObjects.get(e).add(ret);
		return ret;
	}

	@Override
	public void register(MicroService m) {

		queues.put(m, new LinkedList<Message>());
	}

	@Override
	public void unregister(MicroService m) {
		queues.remove(m.getClass());
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		while(queues.get(m.getClass()).size()==0)
		{
			m.wait(); //waiting for message
		}
			return queues.get(m.getClass()).remove(); //returns the first message available
	}

	private void subscribe(Class<? extends Message> type, MicroService m){
		if (subscribe.containsKey(type.getClass())) {
			subscribe.get(type.getClass()).add(m);
		}
		else {
			Class classN=type.getClass();
			subscribe.put(classN, new LinkedBlockingQueue<MicroService>() {
			});
		}
	}
}
