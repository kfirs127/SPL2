package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import java.util.concurrent.TimeUnit;

public class StringFuture extends Future<String>{  // class for test , copy the action of future

    private boolean isDone;
    private String result;

    public StringFuture() {
        isDone = false;
        result = null;
    }

    public String get() {
        return result;
    }

    public void resolve(String result) {
        this.result = result;
    }

    public boolean isDone() {
        if (result != null) isDone = true;
        return isDone;
    }

    public String get(long timeout, TimeUnit unit) { return result; }
}
