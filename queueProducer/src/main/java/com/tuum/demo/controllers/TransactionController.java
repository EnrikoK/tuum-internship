package com.tuum.demo.controllers;

import com.tuum.demo.DTO.CreateTransactionDTO;
import com.tuum.demo.DTO.TransactionResponseDTO;
import com.tuum.demo.models.Transaction;
import com.tuum.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionDTO dto){
        var response = transactionService.createNewTransaction(dto);
        if(response == null) return ResponseEntity.status(500).body(Map.of("message","Something went wrong with the request..."));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserTransactions(@RequestParam(name = "id") long id){
        var response = transactionService.getAllUserTransactions(id);
        return ResponseEntity.ok(response);
    }
}
