package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;

public class CopyEvent implements Event<String>{
    private String eventName;

    public CopyEvent(String senderName) {
        this.eventName = senderName;
    }

    public String getEventName() {
        return eventName;
    }
}
