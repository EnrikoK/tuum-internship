package com.demo.queueReceiver.service;


import com.demo.queueReceiver.dto.CreateAccountDTO;
import com.demo.queueReceiver.dto.CreateTransactionDTO;
import com.demo.queueReceiver.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionQueueListener {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private final TransactionService transactionService;

    @Autowired
    public TransactionQueueListener(RabbitTemplate rabbitTemplate, TransactionService transactionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = "transaction")
    public Object consumeTransactionMessage(Message message){
        try {
            // Convert the message to a String
            String convertedMessage = (String) rabbitTemplate.getMessageConverter().fromMessage(message);
            // Convert it back into a dto object
            CreateTransactionDTO transaction = objectMapper.readValue(convertedMessage, CreateTransactionDTO.class);
            // Create account in account service
            var transactionResponse = transactionService.createNewTransaction(transaction);
            // Return created account details
            return objectMapper.writeValueAsString(transactionResponse);

        }catch (JsonProcessingException ex){
            return null;
        }
    }
}
