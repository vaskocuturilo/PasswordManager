package com.example.passwordmanager.exceptions;

public class PasswordNotFoundException extends Exception {

    public PasswordNotFoundException(String message) {
        super(message);
    }

    public PasswordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
