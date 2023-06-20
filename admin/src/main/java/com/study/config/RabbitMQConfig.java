package com.study.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue emailQueue() {
        return new Queue("emailQueue");
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange("emailExchange");
    }

    @Bean
    public Binding emailBinding() {
        return new Binding("emailQueue", Binding.DestinationType.QUEUE, "emailExchange", "emailKey", null);
    }
}
