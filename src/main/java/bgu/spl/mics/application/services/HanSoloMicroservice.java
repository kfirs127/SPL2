package bgu.spl.mics.application.services;


import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
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
                    Ewoks.getInstance().getSupply(c.getSerials());
                }
                catch (NullPointerException ignored){}
                try {
                    Thread.sleep(c.getDuration());
                }
                catch (InterruptedException ignored){}
                Ewoks.getInstance().releaseSupply(c.getSerials());
                HanSoloMicroservice.super.complete(c, true);
                diary.addAttack();
            }
        };
        // end message
    }
}
