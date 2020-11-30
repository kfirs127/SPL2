package bgu.spl.mics.application;

import bgu.spl.mics.Broadcast;

public class CopyBroadcast implements Broadcast {
    private String senderId;

    public CopyBroadcast(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderId() {
        return senderId;
    }
}
