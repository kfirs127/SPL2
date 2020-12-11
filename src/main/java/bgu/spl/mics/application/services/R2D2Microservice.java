package bgu.spl.mics.application.services;
import bgu.spl.mics.Callback;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateMessage;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {
    private long duration;
    private Diary diary;
    public R2D2Microservice(long duration) {
        super("R2D2");
        this.duration = duration;
        diary = Diary.getInstance();
    }

    @Override
    protected void initialize() {
        //defines callback function for deactivate
        Callback <DeactivationEvent> deactivae= c -> {
            try{
                Thread.sleep(duration);
            }
            catch (InterruptedException e){ System.out.println("exception in r2d2 call");}
            complete(c,true);
            diary.setR2D2Deactivate(this,startTime);
    };
        super.subscribeEvent(DeactivationEvent.class,deactivae);
    }


}
