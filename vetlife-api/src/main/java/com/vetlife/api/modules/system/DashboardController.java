package com.vetlife.api.modules.system;

import com.vetlife.api.modules.client.ClientRepository;
import com.vetlife.api.modules.pet.PetRepository;
import com.vetlife.api.modules.finance.FinancialRepository;
import com.vetlife.api.modules.finance.FinancialRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ClientRepository clientRepo;
    private final PetRepository petRepo;
    private final FinancialRepository financeRepo;

    @GetMapping
    public Map<String, Object> getStats() {
        BigDecimal totalFaturamento = financeRepo.findAll().stream()
                .map(FinancialRecord::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
            "total_clientes", clientRepo.count(),
            "total_pets", petRepo.count(),
            "faturamento_total", totalFaturamento
        );
    }
}