package com.tuum.demo.exceptions;

public class TransactionDetailsException extends RuntimeException{
    public TransactionDetailsException(String message) {
        super(message);
    }
}
