package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.FinishEvent;
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
        diary = Diary.getInstance();
    }

    @Override
    protected void initialize() {
        Callback<AttackEvent> callback = new Callback<AttackEvent>() {
            @Override
            public void call(AttackEvent c) {
                try {
                    while(!Ewoks.getInstance().getSupply(c.getSerials()));  // get the ewoks for war
                    Thread.sleep(c.getDuration());                  // simulate the attack by sleeping.
                    Ewoks.getInstance().releaseSupply(c.getSerials());  // return the ewoks from the war.
                    C3POMicroservice.super.complete(c, true);   // finish attack.
                    diary.addAttack();                               // tell the diary we finish attack.
                }
                catch (NullPointerException ignored){ System.out.println("exception in C3PO call");}
                catch (InterruptedException inter){ System.out.println("interrupt exception call C3PO sleep "); }
            }
        };
        subscribeBroadcast(FinishEvent.class, c -> { diary.setFinish(this,startTime); }) ;
        super.subscribeEvent(AttackEvent.class,callback);
    }

}
