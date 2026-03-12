package com.vetlife.api.modules.finance;

import com.vetlife.api.modules.finance.dto.FinancialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
public class FinancialController {

    private final FinancialService service;

    @GetMapping
    public ResponseEntity<Page<FinancialResponse>> list(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }
}