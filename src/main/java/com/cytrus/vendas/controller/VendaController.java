package com.cytrus.vendas.controller;

import com.cytrus.vendas.dto.VendaDto;
import com.cytrus.vendas.model.Venda;
import com.cytrus.vendas.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaDto vendaDto) {
        Venda venda = vendaService.criarVenda(vendaDto);
        return ResponseEntity.ok(venda);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        return ResponseEntity.ok(vendaService.listarVendas());
    }
}