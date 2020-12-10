package bgu.spl.mics.application.passiveObjects;
import java.util.ArrayList;
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
    private ArrayList<Ewok> Ewoks;

    public static Ewoks getInstance(){
        if(INSTANCE==null)
            INSTANCE=new Ewoks();
        return INSTANCE;
    }

    private Ewoks(){
        Ewoks = new ArrayList<>();
    }
    public void addEwok(){
        int num=Ewoks.size()+1;
        Ewoks.add(new Ewok(num));
    }
    public synchronized boolean getSupply(Integer [] ewok){

        int size=ewok.length;
        if(size>Ewoks.size())
            throw new ArrayIndexOutOfBoundsException("too many ewoks");
        for(int i=0; i<size;i++) {
            if (!Ewoks.get(ewok[i]-1).getAvailable()) {
                return false;
            }
        }
        for(int i=0; i<ewok.length;i++) {
            Ewoks.get(ewok[i]-1).acquire();
        }
        return true;
    }
    public synchronized boolean releaseSupply(Integer [] ewok){

        for(int i=0; i<ewok.length;i++) {
            if (Ewoks.get(ewok[i]-1).getAvailable())
                return false;
        }
        for(int i=0; i<ewok.length;i++) {
            int number = ewok[i];
            Ewoks.get(ewok[i]-1).release();
        }
        return true;
    }
}