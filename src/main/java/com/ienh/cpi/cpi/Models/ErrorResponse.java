package com.ienh.cpi.cpi.Models;

public class ErrorResponse {

    private String message;
    private String error;

    public ErrorResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return this.message;
    }

    public String getError() {
        return this.error;
    }
}
