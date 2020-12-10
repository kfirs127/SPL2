package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.FinishEvent;
import bgu.spl.mics.application.messages.TerminateMessage;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {

    private Diary diary;
	
    public C3POMicroservice() {
        super("C3PO");
        diary=Diary.getInstance();
    }

    @Override
    protected void initialize() {
        Callback<AttackEvent> callback = new Callback<AttackEvent>() {
            @Override
            public void call(AttackEvent c) {
                try {
                 //   System.out.println(this.getClass().getName()+" try get supply");
                    while(!Ewoks.getInstance().getSupply(c.getSerials()));
                    Thread.sleep(c.getDuration());
                    Ewoks.getInstance().releaseSupply(c.getSerials());
                  //  System.out.println("sent event "+c.getClass().getName() +" to complete");
                    C3POMicroservice.super.complete(c, true);
                    diary.addAttack();
                }
                catch (NullPointerException ignored){ System.out.println("exception in c3po call");}
                catch (InterruptedException inter){
                    System.out.println("interrupt exception call c3po sleep ");
                }

            }
        };
        subscribeBroadcast(FinishEvent.class, c -> { diary.setFinish(this,startTime); }) ;
        // end message
        super.subscribeEvent(AttackEvent.class,callback);
    }
}
