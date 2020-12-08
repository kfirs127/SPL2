package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

public class DeactivationEvent implements Event<Boolean> {

    private long duration;
    private Attack attack;
        public DeactivationEvent(long dur){ duration=dur;}
    public DeactivationEvent(Attack attack) {
        this.duration = attack.getDuration();
        this.attack = attack;
    }
    public long getDuration() { return duration; }
}
