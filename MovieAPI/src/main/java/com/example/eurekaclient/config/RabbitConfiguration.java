package com.example.eurekaclient.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    
    private final AppProperties appProperties;

    public RabbitConfiguration(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue senderQueue() {
        return new Queue(appProperties.sendingQueue());
    }


    @Bean
    DirectExchange senderExchange() {
        return new DirectExchange(appProperties.sendingQueue() + "Exchange", true, false);
    }

    @Bean
    Binding senderBinding() {
        return BindingBuilder.bind(senderQueue()).to(senderExchange()).with(appProperties.sendingQueue() + "RoutingKey");
    }


    @Bean
    public Queue receiveQueue() {
        return new Queue(appProperties.receivingQueue());
    }
    @Bean
    DirectExchange receiveExchange() {
        return new DirectExchange(appProperties.receivingQueue() + "Exchange", true, false);
    }

    @Bean
    Binding receiveBinding() {
        return BindingBuilder.bind(receiveQueue()).to(receiveExchange()).with(appProperties.receivingQueue() + "RoutingKey");
    }
}