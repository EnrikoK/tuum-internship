package com.demo.queueReceiver.service;

import com.demo.queueReceiver.dto.CreateTransactionDTO;
import com.demo.queueReceiver.dto.TransactionResponseDTO;
import com.demo.queueReceiver.mappers.AccountBalancesMapper;
import com.demo.queueReceiver.mappers.AccountMapper;
import com.demo.queueReceiver.mappers.TransactionMapper;
import com.demo.queueReceiver.models.Account;
import com.demo.queueReceiver.models.Balance;
import com.demo.queueReceiver.models.Transaction;
import com.demo.queueReceiver.utils.enums.Currency;
import com.demo.queueReceiver.utils.enums.TransactionDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    AccountBalancesMapper balancesMapper;

    @Autowired
    AccountMapper accountMapper;

    private static final Set<String> supportedCurrencies = Arrays.stream(Currency.values()).map(Currency::name).collect(Collectors.toSet());


    public TransactionResponseDTO createNewTransaction(CreateTransactionDTO dto) {
        //Return null if the dto is faulty
        if(!validateTransaction(dto)) return null;

        Transaction transaction = transactionMapper.createNewTransaction(
                dto.getAccountId(),
                dto.getAmount(),
                dto.getCurrency(),
                dto.getDirection(),
                dto.getDescription()
        );

        double updatedBalance = updateBalance(transaction);

        return TransactionResponseDTO.builder()
                .transactionId(transaction.getTransactionId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .direction(transaction.getDirection())
                .description(transaction.getDescription())
                .balance(updatedBalance)
                .build();
    }


    /**
     * Checks if the user has a balance in the given currency,
     * if not then a new empty balance is created for them.
     * Updates the user's balance according to the transaction.
     * */
    private double updateBalance(Transaction transaction){
        Double balance = balancesMapper.getCurrencyAmountForAccount(transaction.getAccountId(),transaction.getCurrency());
        if(balance == null){
            balancesMapper.createEmptyUserBalance(transaction.getAccountId(),transaction.getCurrency());
            balance = (double) 0;
        }
        switch (transaction.getDirection()){
            case "IN" -> {
                return balancesMapper.updateBalance(
                        transaction.getAccountId(),
                        balance+ transaction.getAmount(),
                        transaction.getCurrency());
            }

            case "OUT" -> {
                return balancesMapper.updateBalance(
                        transaction.getAccountId(),
                        balance- transaction.getAmount(),
                        transaction.getCurrency());
            }
        }
        return -1;
    }

    private boolean validateTransaction(CreateTransactionDTO dto) {
        if(!supportedCurrencies.contains(dto.getCurrency())){
            return false;
        }
        if(!(Objects.equals(dto.getDirection(), TransactionDirection.IN.name()) || Objects.equals(dto.getDirection(), TransactionDirection.OUT.name()))){
            return false;
        }
        if(dto.getAmount() < 0){
            return false;
        }
        if(dto.getDirection().equals("OUT") && !validateUserFunds(dto.getAccountId(),dto.getCurrency(), dto.getAmount())){
            return false;
        }
        if(dto.getDescription() == null || dto.getDescription().isEmpty()){
            return false;
        }
        return true;
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
}
