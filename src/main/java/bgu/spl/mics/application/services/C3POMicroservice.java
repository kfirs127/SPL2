package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
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
	
    public C3POMicroservice() {
        super("C3PO");
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
                C3POMicroservice.super.complete(c, true);
            }
        };
        // end message
    }
}
