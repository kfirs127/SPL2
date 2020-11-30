package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;

public class CopyEvent implements Event<String>{
    private String senderName;

    public CopyEvent(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
