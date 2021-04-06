package com.fy.engineserver.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class PlayerInMatingSessionException extends Exception {

    private Throwable nestedThrowable = null;

    public PlayerInMatingSessionException() {
        super();
    }

    public PlayerInMatingSessionException(String msg) {
        super(msg);
    }

    public PlayerInMatingSessionException(Throwable nestedThrowable) {
        this.nestedThrowable = nestedThrowable;
    }

    public PlayerInMatingSessionException(String msg, Throwable nestedThrowable) {
        super(msg);
        this.nestedThrowable = nestedThrowable;
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(pw);
        }
    }
}
