package com.cytrus.vendas.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String EXCHANGE = "venda.exchange";
    public static final String QUEUE = "venda.queue";
    public static final String ROUTING_KEY = "venda.routing.key";

    @Bean
    public Queue vendaQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public DirectExchange vendaExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding vendaBinding(Queue vendaQueue, DirectExchange vendaExchange) {
        return BindingBuilder.bind(vendaQueue).to(vendaExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}