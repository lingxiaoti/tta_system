package com.sie.saaf.schedule.exception;

public class NotLoginException extends Exception {
    public NotLoginException(String string, Throwable throwable, boolean b, boolean b1) {
        super(string, throwable, b, b1);
    }

    public NotLoginException(Throwable throwable) {
        super(throwable);
    }

    public NotLoginException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public NotLoginException(String string) {
        super(string);
    }

    public NotLoginException() {
        super();
    }
}
