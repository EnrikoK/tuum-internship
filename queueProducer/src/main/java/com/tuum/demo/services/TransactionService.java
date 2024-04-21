package com.tuum.demo.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuum.demo.DTO.CreateTransactionDTO;
import com.tuum.demo.DTO.TransactionResponseDTO;
import com.tuum.demo.exceptions.AccountNotFoundException;
import com.tuum.demo.exceptions.QueueResponseException;
import com.tuum.demo.exceptions.TransactionDetailsException;
import com.tuum.demo.mappers.AccountMapper;
import com.tuum.demo.mappers.TransactionMapper;
import com.tuum.demo.models.Account;
import com.tuum.demo.models.Balance;
import com.tuum.demo.models.Transaction;
import com.tuum.demo.utils.enums.Currency;
import com.tuum.demo.utils.enums.TransactionDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {


    private final AccountMapper accountMapper;

    private final TransactionMapper transactionMapper;

    private final RabbitMQPublisherService publisherService;

    private final ObjectMapper objectMapper;

    private static final Set<String> supportedCurrencies = Arrays.stream(Currency.values()).map(Currency::name).collect(Collectors.toSet());

    @Autowired
    public TransactionService(AccountMapper accountMapper, TransactionMapper transactionMapper, RabbitMQPublisherService publisherService, ObjectMapper objectMapper) {
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
        this.publisherService = publisherService;
        this.objectMapper = objectMapper;
    }

    public Object createNewTransaction(CreateTransactionDTO dto){
        validateTransaction(dto);
        var response = publisherService.publishToTransactionQueueAndReceive(dto);
        try{
            return objectMapper.readValue(response, TransactionResponseDTO.class);
        }catch (JsonProcessingException ex){
            throw new QueueResponseException("Exception when processing transaction");
        }

    }

    private void validateTransaction(CreateTransactionDTO dto) {
        if(!supportedCurrencies.contains(dto.getCurrency())){
            throw new TransactionDetailsException("Currency "+dto.getCurrency()+" is not supported");
        }
        if(!(Objects.equals(dto.getDirection(), TransactionDirection.IN.name()) || Objects.equals(dto.getDirection(), TransactionDirection.OUT.name()))){
            throw  new TransactionDetailsException("Invalid transaction direction: "+dto.getDirection());
        }
        if(dto.getAmount() < 0){
            throw new TransactionDetailsException("Invalid amount: "+dto.getAmount());
        }
        if(!isAccountIdValid(dto.getAccountId())){
            throw new TransactionDetailsException("Account id: "+dto.getAccountId()+" is missing");
        }
        if(dto.getDirection().equals("OUT") && !validateUserFunds(dto.getAccountId(),dto.getCurrency(), dto.getAmount())){
            throw new TransactionDetailsException("Insufficient funds");
        }
        if(dto.getDescription() == null || dto.getDescription().isEmpty()){
            throw new TransactionDetailsException("Missing transaction description");
        }


    }



    private boolean isAccountIdValid(long id){
        Account account = accountMapper.getUserAccountDetailsByAccountId(id);
        return account != null;
    }

    private boolean validateUserFunds(long id, String currency , double amount){
        Account account = accountMapper.getUserAccountDetailsByAccountId(id);
        for (Balance balance : account.getBalances()) {
            if(balance.getCurrency().name().equals(currency)){
                return balance.getAmount() >= amount;
            }
        }
        return false;
    }

    public List<Transaction> getAllUserTransactions(long id) {
        if(isAccountIdValid(id)) return transactionMapper.getAllTransactionsByAccountId(id);
        else throw new AccountNotFoundException("No account with id: " + id);
    }
}
