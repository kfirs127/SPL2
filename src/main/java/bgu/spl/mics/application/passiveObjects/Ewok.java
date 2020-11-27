package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
	
  
    /**
     * Acquires an Ewok
     */
    public void acquire() {
		if(available) available = false;
    }

    /**
     * release an Ewok
     */
    public void release() {
        if(!available) available = true;
    }

    public boolean getAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public int getSerialNumber(){
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber){
        this.serialNumber = serialNumber;
    }
}
