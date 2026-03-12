package com.vetlife.api.modules.finance;

import com.vetlife.api.modules.finance.dto.FinancialResponse;
import org.springframework.stereotype.Component;

@Component
public class FinancialMapper {

    public FinancialResponse toResponse(FinancialRecord entity) {
        return new FinancialResponse(
                entity.getId(),
                entity.getDescricao(),
                entity.getValor(),
                entity.getTipo(),
                entity.getDataLancamento()
        );
    }
}