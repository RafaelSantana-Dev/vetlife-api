package com.vetlife.api.modules.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FinancialResponse(
    Long id,
    String descricao,
    BigDecimal valor,
    String tipo,
    LocalDateTime dataLancamento
) {}