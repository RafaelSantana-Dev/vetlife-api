package com.vetlife.api.modules.store;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity 
@Table(name = "produtos")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    @Column(nullable = false)
    private Integer estoque;
    
    @Column(nullable = false)
    private Integer estoqueMinimo;
    
    @Column(length = 50)
    private String categoria;
    
    @Column(length = 50)
    private String fornecedor;
    
    @Column(nullable = false)
    private Boolean ativo = true;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public boolean isEstoqueBaixo() {
        return estoque <= estoqueMinimo;
    }
}