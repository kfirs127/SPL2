package bgu.spl.mics.application;

import bgu.spl.mics.Broadcast;

public class StringBroadcast implements Broadcast { // class just for test
    private String broadcastName;

    public StringBroadcast(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public String getBroadcastName() {
        return broadcastName;
    }
}
