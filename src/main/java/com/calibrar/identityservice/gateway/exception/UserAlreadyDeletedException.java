package com.calibrar.identityservice.gateway.exception;

public class UserAlreadyDeletedException extends RuntimeException{
    public UserAlreadyDeletedException(String message) {
        super(message);
    }
}
