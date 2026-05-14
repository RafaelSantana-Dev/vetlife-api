package com.vetlife.api.modules.store.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank(message = "Nome do produto é obrigatório")
    String nome,
    
    String descricao,
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    BigDecimal preco,
    
    @NotNull(message = "Estoque é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    Integer estoque,
    
    @NotNull(message = "Estoque mínimo é obrigatório")
    @Min(value = 1, message = "Estoque mínimo deve ser pelo menos 1")
    Integer estoqueMinimo,
    
    String categoria,
    String fornecedor,
    Boolean ativo
) {}
