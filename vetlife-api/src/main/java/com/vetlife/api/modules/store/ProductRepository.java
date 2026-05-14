package com.vetlife.api.modules.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p WHERE p.ativo = true")
    Page<Product> findAllActive(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.estoque <= p.estoqueMinimo AND p.ativo = true")
    List<Product> findLowStockProducts();
    
    @Query("SELECT p FROM Product p WHERE p.categoria = :categoria AND p.ativo = true")
    Page<Product> findByCategoria(String categoria, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND p.ativo = true")
    Page<Product> findByNomeContaining(String nome, Pageable pageable);
}