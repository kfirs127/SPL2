package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

public class AttackEvent implements Event<Boolean> {
    private int duration;
    private Attack attack;
    private Integer []serials;

    public AttackEvent(){};

    public AttackEvent(Attack attack){
        this.attack=attack;
        this.duration = attack.getDuration();
        this.serials = attack.getSerials().toArray(new Integer[0]);
    }

    public long getDuration() { return duration; }

    public Integer[] getSerials() { return serials; }
}
