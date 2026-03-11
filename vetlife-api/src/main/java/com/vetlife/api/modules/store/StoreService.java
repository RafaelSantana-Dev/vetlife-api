package com.vetlife.api.modules.store;
import com.vetlife.api.modules.finance.FinancialRecord;
import com.vetlife.api.modules.finance.FinancialRepository;
import com.vetlife.api.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service @RequiredArgsConstructor
public class StoreService {
    private final ProductRepository productRepository;
    private final FinancialRepository financialRepository;

    @Transactional
    public void sell(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        if (product.getEstoque() < quantity) throw new BusinessException("Estoque insuficiente");
        
        product.setEstoque(product.getEstoque() - quantity);
        productRepository.save(product);

        // Registro Automático no Financeiro
        BigDecimal totalVenda = product.getPreco().multiply(new BigDecimal(quantity));
        financialRepository.save(FinancialRecord.builder()
            .descricao("Venda de Produto: " + product.getNome())
            .valor(totalVenda)
            .tipo("ENTRADA")
            .build());
    }
}