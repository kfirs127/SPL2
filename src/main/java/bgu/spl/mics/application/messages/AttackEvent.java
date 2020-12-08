package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

public class AttackEvent implements Event<Boolean> {

    private int duration;
    private Attack attack;

    public AttackEvent(){};

    public AttackEvent(Attack attack){
        this.duration = attack.getDuration();
        this.attack=attack;
    }
}
