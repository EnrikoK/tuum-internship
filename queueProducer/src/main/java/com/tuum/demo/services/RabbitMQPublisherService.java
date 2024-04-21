package com.tuum.demo.services;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisherService {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQPublisherService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Publishes to the DTO objet to the account queue and returns the response in String format.
     * */
    String publishToAccountQueueAndReceive(Object message){
        return publish(message,"account");
    }

    /**
     * Publishes the DTO object to the transaction queue and returns the response in String format.
     * */
    String publishToTransactionQueueAndReceive(Object message){
        return publish(message,"transaction");
    }


    /**
     * @param message dto to be converted and published to the queue
     * @param routingKey routing key of the queue
     * Helper method to publish to RabbitMQ Queue
     * */
    private String publish(Object message,String routingKey){
        try {
            String msg = objectMapper.writeValueAsString(message);
            return (String) rabbitTemplate.convertSendAndReceive("bankingApplication",routingKey,msg);
        }catch (Exception e){
            System.out.println("Exception when publishing to queue");
        }
        return null;
    }

}
