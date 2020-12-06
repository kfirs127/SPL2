package bgu.spl.mics;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl INSTANCE;
	private ConcurrentHashMap<Class, LinkedList<Message>> queues;

	public static MessageBusImpl getInstance(){
		if(INSTANCE==null)
			INSTANCE=new MessageBusImpl();
		return INSTANCE;
	}
	private MessageBusImpl(){
		INSTANCE=null;
		queues=new ConcurrentHashMap<>();
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
	}

	@Override
	public void sendBroadcast(Broadcast b) {
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
        return null;
	}

	@Override
	public void register(MicroService m) {

		queues.put(m.getClass(), new LinkedList<Message>() {
		});
	}

	@Override
	public void unregister(MicroService m) {
		queues.remove(m.getClass());
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		
		return null;
	}
}
