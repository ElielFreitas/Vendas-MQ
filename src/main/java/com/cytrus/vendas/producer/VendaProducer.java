package com.cytrus.vendas.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.cytrus.vendas.dto.VendaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class VendaProducer {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(VendaProducer.class);

    @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.rounting-key}")
    private String routingKey;

    public VendaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMenssage(VendaDto vendaDto) {
        log.info("Enviando mensagem para a fila: {}", vendaDto);
        rabbitTemplate.convertAndSend(exchange, routingKey, vendaDto);
    }
}