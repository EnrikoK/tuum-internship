package com.tuum.demo.utils;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    @Bean
    public Queue accountQueue(){
        return new Queue("account");
    }

    @Bean
    public Queue transactionQueue(){
        return new Queue("transaction");
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("bankingApplication");
    }

    @Bean
    public Binding accountBinding(Queue accountQueue, DirectExchange exchange){
        return BindingBuilder
                .bind(accountQueue)
                .to(exchange)
                .with("account");
    }

    @Bean
    public Binding transactionBinding(Queue transactionQueue, DirectExchange exchange){
        return BindingBuilder
                .bind(transactionQueue)
                .to(exchange)
                .with("transaction");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange("bankingApplication");
        return template;
    }
}
