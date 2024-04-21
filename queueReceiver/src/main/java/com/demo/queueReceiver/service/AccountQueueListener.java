package com.demo.queueReceiver.service;


import com.demo.queueReceiver.dto.CreateAccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountQueueListener {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private final AccountService accountService;

    @Autowired
    public AccountQueueListener(RabbitTemplate rabbitTemplate, AccountService accountService) {
        this.rabbitTemplate = rabbitTemplate;
        this.accountService = accountService;
        this.objectMapper = new ObjectMapper();

    }

    @RabbitListener(queues = "account")
    public Object consumeAccountMessage(Message message) {
        try {
            // Convert the message to a String
            String convertedMessage = (String) rabbitTemplate.getMessageConverter().fromMessage(message);
            // Convert it back into a dto object
            CreateAccountDTO dto = objectMapper.readValue(convertedMessage, CreateAccountDTO.class);
            // Create account in account service
            var account = accountService.createNewAccount(dto);
            // Return created account details
            var response = objectMapper.writeValueAsString(account);
            System.out.println(response);
            return response;
        }catch (JsonProcessingException ex){
            System.out.println("[ERROR] JsonProcessingException when adding new account");
            return null;
        }
    }

}
