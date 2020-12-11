package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.FinishEvent;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    private Diary diary;

    public HanSoloMicroservice() {
        super("Han");
        diary=Diary.getInstance();
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
                    HanSoloMicroservice.super.complete(c, true);   // finish attack.
                    diary.addAttack();                               // tell the diary we finish attack.
                }
                catch (NullPointerException ignored){  System.out.println("exception in hansolo call1"); }
                catch (InterruptedException ignored){  System.out.println("exception in hansolo call2");}
            }
        };
        subscribeBroadcast(FinishEvent.class, c -> { diary.setFinish(this,startTime); }) ;
        // end message
        super.subscribeEvent(AttackEvent.class,callback);
    }
}
