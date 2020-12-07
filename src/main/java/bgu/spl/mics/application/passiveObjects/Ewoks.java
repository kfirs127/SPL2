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

    public static Ewoks getInstance(){
        if(INSTANCE==null)
            INSTANCE=new Ewoks();
        return INSTANCE;
    }

    private Ewoks(){
        Ewoks = new LinkedList<>();
    }
    public void addEwok(){
        int num=Ewoks.size()+1;
        Ewoks.add(new Ewok(num));
    }
    public boolean getSupply(Integer [] ewok){

        for(int i=0; i<ewok.length;i++) {
            int number = ewok[i];
            if (!Ewoks.get(number).getAvailable())
                return false;
        }
        for(int i=0; i<ewok.length;i++) {
            int number = ewok[i];
            Ewoks.get(number).acquire();
        }
        return true;
    }
    public boolean releaseSupply(Integer [] ewok){

        for(int i=0; i<ewok.length;i++) {
            int number = ewok[i];
            if (Ewoks.get(number).getAvailable())
                return false;
        }
        for(int i=0; i<ewok.length;i++) {
            int number = ewok[i];
            Ewoks.get(number).acquire();
        }
        return true;
    }
}