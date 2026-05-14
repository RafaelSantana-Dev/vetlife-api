package com.vetlife.api.modules.store.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer estoque,
    Integer estoqueMinimo,
    String categoria,
    String fornecedor,
    Boolean ativo,
    Boolean estoqueBaixo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
