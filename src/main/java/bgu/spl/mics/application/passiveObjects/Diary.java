package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.application.services.*;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private int totalAttack;
    private long HanSoloFinish;
    private long C3POFinish;
    private long C3PODeactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;

    public Diary( ){
        totalAttack=0;
        HanSoloFinish=0;
        C3POFinish=0;
        C3PODeactivate=0;
        LeiaTerminate=0;
        HanSoloTerminate=0;
        C3POTerminate=0;
        R2D2Terminate=0;
        LandoTerminate=0;
    }
    public void setTotalAttack(int total){ totalAttack= total;  }
    public void setHanSoloFinish(HanSoloMicroservice obj){  HanSoloFinish=System.currentTimeMillis();}
    public void setC3POFinish(C3POMicroservice obj){C3POFinish=System.currentTimeMillis();}
    public void setC3PODeactivate(C3POMicroservice obj){C3PODeactivate=System.currentTimeMillis();}
    public void setLeiaTerminate(LeiaMicroservice obj){LeiaTerminate=System.currentTimeMillis();}
    public void setHanSoloTerminate(HanSoloMicroservice obj){HanSoloTerminate=System.currentTimeMillis();}
    public void setC3POTerminate(C3POMicroservice obj){C3POTerminate=System.currentTimeMillis();}
    private void setR2D2Terminate(R2D2Microservice obj){R2D2Terminate=System.currentTimeMillis();}
    private void setLandoTerminate(LandoMicroservice obj){LandoTerminate=System.currentTimeMillis();}
    public void addAttack(){totalAttack++;}
    public int getTotalAttack(){ return totalAttack; }
    public long getHanSoloFinish(HanSoloMicroservice obj){return HanSoloFinish;}
    public long getC3POFinish(C3POMicroservice obj){return C3POFinish;}
    public long getC3PODeactivate(C3POMicroservice obj){return C3PODeactivate;}
    public long getLeiaTerminate(LeiaMicroservice obj){return LeiaTerminate;}
    public long getHanSoloTerminate(HanSoloMicroservice obj){return HanSoloTerminate;}
    public long getC3POTerminate(C3POMicroservice obj){return C3POTerminate;}
    private long getR2D2Terminate(R2D2Microservice obj){return R2D2Terminate;}
    private long getLandoTerminate(LandoMicroservice obj){return LandoTerminate;}

}
