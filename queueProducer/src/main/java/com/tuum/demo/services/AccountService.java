package com.tuum.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuum.demo.DTO.CreateAccountDTO;
import com.tuum.demo.exceptions.AccountCreationException;
import com.tuum.demo.exceptions.QueueResponseException;
import com.tuum.demo.mappers.AccountMapper;
import com.tuum.demo.models.Account;
import com.tuum.demo.utils.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {


    private final AccountMapper accountMapper;

    private final RabbitMQPublisherService publisherService;

    private final ObjectMapper objectMapper;

    private static Set<String> supportedCurrencies;


    @Autowired
    public AccountService(AccountMapper accountMapper, RabbitMQPublisherService publisherService, ObjectMapper objectMapper) {
        this.accountMapper = accountMapper;
        this.publisherService = publisherService;
        this.objectMapper = objectMapper;
        //Take the Currency enum and convert it into a set.
        supportedCurrencies = Arrays.stream(Currency.values()).map(Enum::name).collect(Collectors.toSet());
    }

    public Account createNewAccount(CreateAccountDTO dto){
        //Publish the validated dto to account queue in RabbitMQ
        try {
            validateCreateAccountDto(dto);
            var response = publisherService.publishToAccountQueueAndReceive(dto);
            System.out.println(response);
            return objectMapper.readValue(response,Account.class);
        }catch (JsonProcessingException ex){
            throw new QueueResponseException("Exception when creating a new account.");
        }
    }

    public Account findAccountById(long id){
        return accountMapper.getUserAccountDetailsByAccountId(id);
    }


    /**
     * Takes the CreateAccountDTO object and validates the input fields.
     * If unwanted input is found then an exception will be thrown that
     * is handled by  CustomExceptionHandler.class
     * */
    private static void validateCreateAccountDto(CreateAccountDTO dto){
        //Validating dto country and customerId
        if(dto.getCountry().isEmpty() || dto.getCustomerId().isEmpty()){
            throw new AccountCreationException("Empty parameters in request.");
        }
        //Validating currencies;
        for (String currency : dto.getCurrencies()) {
            if(!supportedCurrencies.contains(currency)){
                throw new AccountCreationException("Currency "+currency+" is not supported");
            }
        }
    }
}
