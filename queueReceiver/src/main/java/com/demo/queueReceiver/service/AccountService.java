package com.demo.queueReceiver.service;

import com.demo.queueReceiver.dto.CreateAccountDTO;
import com.demo.queueReceiver.mappers.AccountBalancesMapper;
import com.demo.queueReceiver.mappers.AccountMapper;
import com.demo.queueReceiver.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountMapper accountMapper;

    private final AccountBalancesMapper balancesMapper;

    @Autowired
    public AccountService(AccountMapper accountMapper, AccountBalancesMapper balancesMapper) {
        this.accountMapper = accountMapper;
        this.balancesMapper = balancesMapper;
    }

    public Account createNewAccount(CreateAccountDTO dto){
        try{
            long createdAccountId= accountMapper.createUserAccount(dto.getCustomerId(),dto.getCountry());
            for (String currency : dto.getCurrencies()) {
                balancesMapper.createEmptyUserBalance(createdAccountId,currency);
            }
            return accountMapper.getUserAccountDetailsByAccountId(createdAccountId);
        }catch (RuntimeException e){
            return null;
        }
    }
}
