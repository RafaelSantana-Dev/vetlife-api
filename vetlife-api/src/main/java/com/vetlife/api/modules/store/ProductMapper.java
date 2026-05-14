package com.vetlife.api.modules.store;

import com.vetlife.api.modules.store.dto.ProductRequest;
import com.vetlife.api.modules.store.dto.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    
    public Product toEntity(ProductRequest dto) {
        return Product.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .estoque(dto.estoque())
                .estoqueMinimo(dto.estoqueMinimo())
                .categoria(dto.categoria())
                .fornecedor(dto.fornecedor())
                .ativo(dto.ativo() != null ? dto.ativo() : true)
                .build();
    }
    
    public ProductResponse toResponse(Product entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getEstoque(),
                entity.getEstoqueMinimo(),
                entity.getCategoria(),
                entity.getFornecedor(),
                entity.getAtivo(),
                entity.isEstoqueBaixo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
    
    public void updateEntity(Product entity, ProductRequest dto) {
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPreco(dto.preco());
        entity.setEstoque(dto.estoque());
        entity.setEstoqueMinimo(dto.estoqueMinimo());
        entity.setCategoria(dto.categoria());
        entity.setFornecedor(dto.fornecedor());
        if (dto.ativo() != null) {
            entity.setAtivo(dto.ativo());
        }
    }
}
