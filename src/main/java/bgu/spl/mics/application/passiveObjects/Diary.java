package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.services.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static Diary INSTANCE=null;
    private AtomicInteger totalAttack;
    private  long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;
    private long terminateTime;
    private long finishTime;

    private Diary( ){
        totalAttack=new AtomicInteger(0);
        HanSoloFinish=0;
        C3POFinish=0;
        R2D2Deactivate=0;
        LeiaTerminate=0;
        HanSoloTerminate=0;
        C3POTerminate=0;
        R2D2Terminate=0;
        LandoTerminate=0;
        terminateTime=0;
        finishTime=0;
    }
    public static Diary getInstance(){
        if(INSTANCE==null)
            INSTANCE=new Diary();
        return INSTANCE;
    }

    public void setFinish(MicroService obj){
        if(finishTime==0)
          finishTime=System.currentTimeMillis();
        if(obj instanceof HanSoloMicroservice) {
            HanSoloFinish = System.currentTimeMillis();
         //   System.out.println(" wrote hansolo finish in diary "+ HanSoloFinish );
        }
        else if(obj instanceof C3POMicroservice) {
            C3POFinish = System.currentTimeMillis();
         //   System.out.println(" wrote hansolo finish in diary "+ C3POFinish );
        }
    }
    public void setR2D2Deactivate(MicroService obj) {
        if(obj instanceof R2D2Microservice) {
            R2D2Deactivate = System.currentTimeMillis();
          //  System.out.println(" wrote deactivation in diary "+ System.currentTimeMillis() );
        }
    }
    public void Terminate(MicroService obj) {
        if(terminateTime==0);
        terminateTime = System.currentTimeMillis();
        if (C3POMicroservice.class.equals(obj.getClass())) {
            C3POTerminate = terminateTime;
        }
        else if (HanSoloMicroservice.class.equals(obj.getClass())) {
            HanSoloTerminate = terminateTime;
        }
        else if (R2D2Microservice.class.equals(obj.getClass())) {
            R2D2Terminate = terminateTime;
        }
        else if (LeiaMicroservice.class.equals(obj.getClass())) {
            LeiaTerminate = terminateTime;
        }
        else if (LandoMicroservice.class.equals(obj.getClass())) {
            LandoTerminate = terminateTime;
        }
    }
    public void addAttack(){
        while(!totalAttack.compareAndSet(totalAttack.get(),totalAttack.get()+1));
        if(totalAttack.get()==2)
            finishTime=System.currentTimeMillis();
    }

}
