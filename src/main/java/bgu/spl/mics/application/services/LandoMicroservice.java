package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.ExploseEvent;
import bgu.spl.mics.application.messages.TerminateMessage;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long duration;
    private Diary diary;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration=duration;
        diary= Diary.getInstance();
    }

    @Override
    protected void initialize() {
        //defines callback function for deactivate
        Callback<ExploseEvent> explotion= c -> {
            try{
                Thread.sleep(duration);
            }
            catch (InterruptedException e){  System.out.println("exception in lando call"); }
            complete(c,true);
            super.sendBroadcast(new TerminateMessage());
        };
        super.subscribeEvent(ExploseEvent.class,explotion);
    }
}
