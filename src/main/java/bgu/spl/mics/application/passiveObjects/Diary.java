package bgu.spl.mics.application.passiveObjects;

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

    public void setFinish(MicroService obj,long start){
        if(obj instanceof HanSoloMicroservice) HanSoloFinish = System.currentTimeMillis() - start;
        else if(obj instanceof C3POMicroservice) C3POFinish = System.currentTimeMillis() - start;
    }
    public void setR2D2Deactivate(MicroService obj,long start) {
        if(obj instanceof R2D2Microservice) R2D2Deactivate = System.currentTimeMillis()-start;
    }
    public void Terminate(MicroService obj,long start) {
        if ( C3POMicroservice.class.equals(obj.getClass()) ) C3POTerminate = System.currentTimeMillis()-start;
        else if ( HanSoloMicroservice.class.equals(obj.getClass()) ) HanSoloTerminate = System.currentTimeMillis()-start;
        else if ( R2D2Microservice.class.equals(obj.getClass()) ) R2D2Terminate = System.currentTimeMillis()-start;
        else if ( LeiaMicroservice.class.equals(obj.getClass()) ) LeiaTerminate = System.currentTimeMillis()-start;
        else if ( LandoMicroservice.class.equals(obj.getClass()) ) LandoTerminate =System.currentTimeMillis()-start;
    }

    public void addAttack(){
        while(!totalAttack.compareAndSet(totalAttack.get(),totalAttack.get()+1));
    }

    public long getHanSoloFinish() { return HanSoloFinish; }

    public long getC3POFinish() { return C3POFinish; }

    public long getLandoTerminate() {
        return LandoTerminate;
    }
    
    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }
    
    public AtomicInteger getNumberOfAttacks(){
        return totalAttack;
    }

    public void resetNumberAttacks() {
    }
}
