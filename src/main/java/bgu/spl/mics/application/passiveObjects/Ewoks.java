package bgu.spl.mics.application.passiveObjects;

import java.util.LinkedList;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private static Ewoks INSTANCE;
    private LinkedList<Ewok> Ewoks;
    private  LinkedList<Ewok> inUse;

    public static Ewoks getInstance(){
        if(INSTANCE==null)
            INSTANCE=new Ewoks();
        return INSTANCE;
    }

    private Ewoks(){
        Ewoks = new LinkedList<>();
        inUse = new LinkedList<>();
    }

   /* private void addEwok(Ewok e){
        Ewoks.add(e);
    }*/


}