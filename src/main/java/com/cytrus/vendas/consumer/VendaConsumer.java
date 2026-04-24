package com.cytrus.vendas.consumer;

import com.cytrus.vendas.dto.VendaDto;
import com.cytrus.vendas.repository.VendaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class VendaConsumer {
    private final VendaRepository vendaRepository;
    private static final Logger log = LoggerFactory.getLogger(VendaConsumer.class);

    public VendaConsumer(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @RabbitListener(queues = "venda.queue")
    public void consumirVenda(VendaDto vendaDto) {
        log.info("mensagem recebida: {}", vendaDto);

        vendaRepository.findById(vendaDto.id()).ifPresent(venda -> {
            venda.setStatus("PROCESSADO");
            vendaRepository.save(venda);
            log.info("Venda ID: {} atualizado para PROCESSADO", venda.getId());
        });
    }
}