package com.vetlife.api.modules.store;

import com.vetlife.api.modules.finance.FinancialRecord;
import com.vetlife.api.modules.finance.FinancialRepository;
import com.vetlife.api.modules.store.dto.ProductRequest;
import com.vetlife.api.modules.store.dto.ProductResponse;
import com.vetlife.api.shared.exception.BusinessException;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service 
@RequiredArgsConstructor
public class StoreService {
    private final ProductRepository productRepository;
    private final FinancialRepository financialRepository;
    private final ProductMapper mapper;

    @Transactional
    public ProductResponse create(ProductRequest request) {
        log.info("Criando produto: {}", request.nome());
        Product product = mapper.toEntity(request);
        Product saved = productRepository.save(product);
        log.info("Produto criado com ID: {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        log.info("Buscando produto ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return mapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        log.info("Listando todos os produtos ativos");
        return productRepository.findAllActive(pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findByCategoria(String categoria, Pageable pageable) {
        log.info("Buscando produtos da categoria: {}", categoria);
        return productRepository.findByCategoria(categoria, pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findByNome(String nome, Pageable pageable) {
        log.info("Buscando produtos por nome: {}", nome);
        return productRepository.findByNomeContaining(nome, pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findLowStockProducts() {
        log.info("Buscando produtos com estoque baixo");
        return productRepository.findLowStockProducts().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        log.info("Atualizando produto ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        mapper.updateEntity(product, request);
        Product updated = productRepository.save(product);
        log.info("Produto atualizado com sucesso");
        return mapper.toResponse(updated);
    }

    @Transactional
    public void addStock(Long productId, Integer quantidade, String observacao) {
        log.info("Adicionando {} unidades ao produto ID: {}", quantidade, productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        product.setEstoque(product.getEstoque() + quantidade);
        productRepository.save(product);
        log.info("Estoque atualizado. Novo estoque: {}", product.getEstoque());
    }

    @Transactional
    public void removeStock(Long productId, Integer quantidade, String observacao) {
        log.info("Removendo {} unidades do produto ID: {}", quantidade, productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        if (product.getEstoque() < quantidade) {
            throw new BusinessException("Estoque insuficiente. Disponível: " + product.getEstoque());
        }
        
        product.setEstoque(product.getEstoque() - quantidade);
        productRepository.save(product);
        log.info("Estoque atualizado. Novo estoque: {}", product.getEstoque());
    }

    @Transactional
    public void sell(Long productId, Integer quantity) {
        log.info("Processando venda de {} unidades do produto ID: {}", quantity, productId);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        
        if (product.getEstoque() < quantity) {
            throw new BusinessException("Estoque insuficiente. Disponível: " + product.getEstoque());
        }
        
        product.setEstoque(product.getEstoque() - quantity);
        productRepository.save(product);

        // Registro Automático no Financeiro
        BigDecimal totalVenda = product.getPreco().multiply(new BigDecimal(quantity));
        financialRepository.save(FinancialRecord.builder()
            .descricao("Venda de Produto: " + product.getNome())
            .valor(totalVenda)
            .tipo("ENTRADA")
            .build());
        
        log.info("Venda processada. Total: R$ {}", totalVenda);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Inativando produto ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        product.setAtivo(false);
        productRepository.save(product);
        log.info("Produto inativado com sucesso");
    }
}