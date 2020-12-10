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
		solved=new AtomicInteger();
		futures= new HashMap<>();
		diary=Diary.getInstance();
    }



    @Override
    protected void initialize() {
       // System.out.println("Leia attacks size: "+ attacks.length);
        sendEvent(new StartMessage());
        for (int i = 0; i < attacks.length; i++) {
            AttackEvent add = new AttackEvent(attacks[i]);
         //   System.out.println("Leia sends message type: "+ add.getClass()+ " duration: "+add.getDuration()+" serials: "+ add.getSerials().toString()  );
            Future fut = sendEvent(add);
            futures.put(add, fut);
        }
        Iterator<Map.Entry<Message, Future>> iterator = futures.entrySet().iterator();
        while (iterator.hasNext()) { //chack if all attack are finished
            iterator.next().getValue().get();
        }
        sendBroadcast(new FinishEvent());
         // Syst  em.out.println(" Hansolo and c3po finished attacks, leia calls r2d2 to determinate");
            Future fut = sendEvent(new DeactivationEvent());
            fut.get(); //wait until event solved.
       //   System.out.println("deactivation future is "+ fut.isDone());
         //   System.out.println(" r2d2 finished deactivate, leia calls lando to explode");
            //after R2D2 finished, send message to lando.
            Future explosion = sendEvent(new ExploseEvent());
            explosion.get();
          //  System.out.println(" lando finished explode");
            //after she finished she do nothing untill shutdown.

    }


}
