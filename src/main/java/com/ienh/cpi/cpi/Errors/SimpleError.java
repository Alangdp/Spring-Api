package com.ienh.cpi.cpi.Errors;

public class SimpleError extends Exception {

    public SimpleError(String message) {
        super(message);
    }

    public SimpleError(String message, Throwable cause) {
        super(message, cause);
    }
}
