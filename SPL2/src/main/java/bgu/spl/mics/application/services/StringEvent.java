package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;

public class StringEvent implements Event<String>{   // class just for test
    private String eventName;

    public StringEvent(String senderName) {
        this.eventName = senderName;
    }

    public String getEventName() {
        return eventName;
    }
}
