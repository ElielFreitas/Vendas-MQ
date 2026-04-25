package com.cytrus.vendas.service;

import org.springframework.stereotype.Service;
import com.cytrus.vendas.dto.VendaDto;
import com.cytrus.vendas.model.Venda;
import com.cytrus.vendas.repository.VendaRepository;
import com.cytrus.vendas.producer.VendaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class VendaService {
    private final VendaRepository vendaRepository;
    private final VendaProducer vendaProducer;
    private static final Logger log = LoggerFactory.getLogger(VendaService.class);

    public VendaService(VendaRepository vendaRepository, VendaProducer vendaProducer) {
        this.vendaRepository = vendaRepository;
        this.vendaProducer = vendaProducer;
    }

    public Venda criarVenda(VendaDto vendaDto) {
        Venda venda = new Venda();
        venda.setProduto(vendaDto.produto());
        venda.setValor(vendaDto.valor());
        venda.setStatus("PENDENTE");

        Venda vendaSalva = vendaRepository.save(venda);
        log.info("Venda salva com ID: {}", vendaSalva.getId());

        VendaDto dto = new VendaDto(vendaSalva.getId(), vendaSalva.getProduto(), vendaSalva.getValor());
        vendaProducer.sendMenssage(dto);

        return vendaSalva;
    }

    public List<Venda> listarVendas() {
        return (List<Venda>) vendaRepository.findAll();
    }
}