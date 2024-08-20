package com.messagingservice.notificationservice.exception;

public class InvalidAuthHeaderException extends RuntimeException {
    public InvalidAuthHeaderException(String message) {
        super(message);
    }
}
