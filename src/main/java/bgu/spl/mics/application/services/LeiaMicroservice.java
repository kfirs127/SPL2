package bgu.spl.mics.application.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import bgu.spl.mics.Future;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent }.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
	private AtomicInteger solved;
    private HashMap<Message, Future> futures;
    private Diary diary;
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		solved = new AtomicInteger();
		futures = new HashMap<>();
		diary = Diary.getInstance();
    }

    @Override
    protected void initialize() {
        sendEvent(new StartMessage());
        for (int i = 0; i < attacks.length; i++) {
            AttackEvent add = new AttackEvent(attacks[i]);
            Future fut = sendEvent(add);
            futures.put(add, fut);
        }
        sendBroadcast(new FinishEvent());
        Iterator<Map.Entry<Message, Future>> iterator = futures.entrySet().iterator();
        while (iterator.hasNext()) {  //check if all attack are finished
            iterator.next().getValue().get();
        }
        Future fut = sendEvent(new DeactivationEvent());
        fut.get();  //wait until event solved.
        Future explosion = sendEvent(new ExploseEvent());
        explosion.get();
    }


}
