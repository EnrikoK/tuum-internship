package com.tuum.demo.controllers;

import com.tuum.demo.DTO.CreateAccountDTO;
import com.tuum.demo.exceptions.AccountNotFoundException;
import com.tuum.demo.mappers.AccountBalancesMapper;
import com.tuum.demo.mappers.AccountMapper;
import com.tuum.demo.models.Account;
import com.tuum.demo.services.RabbitMQPublisherService;
import com.tuum.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;


    @GetMapping("/")
    public ResponseEntity<?> getAccountById(@RequestParam(value = "id")long id){
        Account account = accountService.findAccountById(id);
        if(account == null) throw new AccountNotFoundException("Account with id: "+id+" not found.");
        return ResponseEntity.ok(account);
    }

    @PostMapping("/")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountDTO dto){
        var account = accountService.createNewAccount(dto);
        return ResponseEntity.ok(account);
    }
}
