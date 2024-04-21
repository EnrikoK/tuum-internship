package com.tuum.demo.utils;

import com.tuum.demo.exceptions.AccountCreationException;
import com.tuum.demo.exceptions.AccountNotFoundException;
import com.tuum.demo.exceptions.TransactionDetailsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status",HttpStatus.NOT_FOUND,"message",ex.getMessage()));
    }

    @ExceptionHandler(TransactionDetailsException.class)
    public ResponseEntity<Object> handleInvalidCurrencyException(TransactionDetailsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status",HttpStatus.BAD_REQUEST,"message",ex.getMessage()));
    }


    @ExceptionHandler(AccountCreationException.class)
    public ResponseEntity<Object> handleAccountCreationException(AccountCreationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status",HttpStatus.BAD_REQUEST,"message",ex.getMessage()));
    }

}
