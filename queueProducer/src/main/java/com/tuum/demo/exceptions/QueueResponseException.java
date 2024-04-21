package com.tuum.demo.exceptions;

public class QueueResponseException extends RuntimeException{
    public QueueResponseException(String message) {
        super(message);
    }
}
