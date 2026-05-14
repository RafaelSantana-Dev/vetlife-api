package com.vetlife.api.modules.store;

import com.vetlife.api.modules.store.dto.ProductRequest;
import com.vetlife.api.modules.store.dto.ProductResponse;
import com.vetlife.api.modules.store.dto.StockMovementRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController 
@RequestMapping("/api/v1/store") 
@RequiredArgsConstructor
@Tag(name = "Loja", description = "Gestão de produtos e estoque")
@SecurityRequirement(name = "bearer-key")
public class StoreController {
    private final StoreService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Criar produto", description = "Cadastra um novo produto no estoque")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Listar produtos", description = "Lista todos os produtos ativos (paginado)")
    public ResponseEntity<Page<ProductResponse>> findAll(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/categoria/{categoria}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Buscar por categoria", description = "Lista produtos de uma categoria específica")
    public ResponseEntity<Page<ProductResponse>> findByCategoria(
            @PathVariable String categoria,
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.findByCategoria(categoria, pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Buscar por nome", description = "Busca produtos por nome (parcial)")
    public ResponseEntity<Page<ProductResponse>> findByNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.findByNome(nome, pageable));
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Produtos com estoque baixo", description = "Lista produtos com estoque abaixo do mínimo")
    public ResponseEntity<List<ProductResponse>> findLowStockProducts() {
        return ResponseEntity.ok(service.findLowStockProducts());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PostMapping("/{id}/add-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Adicionar estoque", description = "Adiciona unidades ao estoque do produto")
    public ResponseEntity<Map<String, String>> addStock(
            @PathVariable Long id,
            @Valid @RequestBody StockMovementRequest request) {
        service.addStock(id, request.quantidade(), request.observacao());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Estoque adicionado com sucesso");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/remove-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Remover estoque", description = "Remove unidades do estoque do produto")
    public ResponseEntity<Map<String, String>> removeStock(
            @PathVariable Long id,
            @Valid @RequestBody StockMovementRequest request) {
        service.removeStock(id, request.quantidade(), request.observacao());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Estoque removido com sucesso");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/sell/{quantity}") 
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Vender produto", description = "Processa venda e atualiza estoque e financeiro")
    public ResponseEntity<Map<String, String>> sell(
            @PathVariable Long id, 
            @PathVariable Integer quantity) {
        service.sell(id, quantity);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Venda realizada e estoque atualizado!");
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Inativar produto", description = "Inativa um produto (soft delete)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}