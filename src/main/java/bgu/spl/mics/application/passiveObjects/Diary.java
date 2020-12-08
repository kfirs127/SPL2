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
    }
    public static Diary getInstance(){
        if(INSTANCE==null)
            INSTANCE=new Diary();
        return INSTANCE;
    }
    public void setTotalAttack(int total){ totalAttack.compareAndSet(totalAttack.get(), total);  }

    public void setHanSoloFinish(MicroService obj) throws Exception {
        if(obj instanceof HanSoloMicroservice)
            throw new Exception("wrong microservice tried to set hansolo finish");
        HanSoloFinish=System.currentTimeMillis();
    }
    public void setC3POFinish(MicroService obj)throws Exception {
        if(obj instanceof C3POMicroservice)
            throw new Exception("wrong microservice tried to set hansolo finish");
        C3POFinish=System.currentTimeMillis();
    }
    public void setR2D2Deactivate(MicroService obj)throws Exception {
        if(obj instanceof C3POMicroservice)
            throw new Exception("wrong microservice tried to set hansolo finish");
        R2D2Deactivate=System.currentTimeMillis();
    }
    public void Terminate(MicroService obj)throws Exception {
        long terminate = System.currentTimeMillis();
        C3POTerminate = terminate;
        HanSoloTerminate = terminate;
        LeiaTerminate = terminate;
        R2D2Terminate = terminate;
        LandoTerminate = terminate;
    }
    public void addAttack(){totalAttack.compareAndSet(totalAttack.get(),totalAttack.get()+1);}
    public int getTotalAttack(){ return totalAttack.get(); }
    public long getHanSoloFinish(HanSoloMicroservice obj){return HanSoloFinish;}
    public long getC3POFinish(C3POMicroservice obj){return C3POFinish;}
    public long getR2D2Deactivate(C3POMicroservice obj){return R2D2Deactivate;}
    public long getLeiaTerminate(LeiaMicroservice obj){return LeiaTerminate;}
    public long getHanSoloTerminate(HanSoloMicroservice obj){return HanSoloTerminate;}
    public long getC3POTerminate(C3POMicroservice obj){return C3POTerminate;}
    private long getR2D2Terminate(R2D2Microservice obj){return R2D2Terminate;}
    private long getLandoTerminate(LandoMicroservice obj){return LandoTerminate;}

}
