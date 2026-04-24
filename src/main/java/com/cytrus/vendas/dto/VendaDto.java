package com.cytrus.vendas.dto;

public record VendaDto(
    long id,
    String produto,
    double valor) {
}
