package com.vetlife.api.modules.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockMovementRequest(
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    Integer quantidade,
    
    String observacao
) {}
