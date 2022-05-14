package com.example.utils;

public class BussinessException extends Exception {

    private String message;

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage() {
        return message;
    }

}
