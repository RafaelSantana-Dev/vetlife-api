package com.vetlife.api.modules.finance;

import com.vetlife.api.modules.finance.dto.FinancialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialService {

    private final FinancialRepository repository;
    private final FinancialMapper mapper;

    @Transactional(readOnly = true)
    public Page<FinancialResponse> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }
}