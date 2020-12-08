package bgu.spl.mics.application.services;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import bgu.spl.mics.Future;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.ExploseEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.messages.AttackEvent;

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
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		solved=new AtomicInteger();
		futures= new HashMap<>();
    }



    @Override
    protected void initialize() {

        
        for(int i=0;i<attacks.length;i++){
            AttackEvent add=new AttackEvent(attacks[i]);
            Future fut=sendEvent(add);
            futures.put(add,fut);
        }
        Iterator<Map.Entry<Message,Future>> iterator=futures.entrySet().iterator();
        while(iterator.hasNext()) { //chack if all attack are finished
            try {
                iterator.next().getValue().get();
            }
            catch (InterruptedException e){}
        }
        //now send to r2d2 message
        Future fut=sendEvent(new DeactivationEvent());
        try{
            fut.get(); //wait until event solved.
        }
        catch (InterruptedException e){}
        //after R2D2 finished, send message to lando.
        Future explosion=sendEvent(new ExploseEvent());
        try{
            explosion.get();
        }
        catch (InterruptedException e){}

        //after she finished she do nothing untill shutdown.
    }


}
