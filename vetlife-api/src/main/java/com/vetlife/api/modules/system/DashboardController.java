package com.vetlife.api.modules.system;

import com.vetlife.api.modules.client.ClientRepository;
import com.vetlife.api.modules.pet.PetRepository;
import com.vetlife.api.modules.finance.FinancialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return Map.of(
            "total_clientes", clientRepo.count(),
            "total_pets", petRepo.count(),
            "faturamento_total", financeRepo.sumTotal(),
            "total_entradas", financeRepo.sumEntradas(),
            "total_saidas", financeRepo.sumSaidas()
        );
    }
}