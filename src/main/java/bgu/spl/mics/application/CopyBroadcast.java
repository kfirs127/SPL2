package bgu.spl.mics.application;

import bgu.spl.mics.Broadcast;

public class CopyBroadcast implements Broadcast {
    private String broadcastName;

    public CopyBroadcast(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public String getBroadcastName() {
        return broadcastName;
    }
}
