package com.vetlife.api.modules.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface FinancialRepository extends JpaRepository<FinancialRecord, Long> {

    @Query("SELECT COALESCE(SUM(f.valor), 0) FROM FinancialRecord f WHERE f.tipo = 'ENTRADA'")
    BigDecimal sumEntradas();

    @Query("SELECT COALESCE(SUM(f.valor), 0) FROM FinancialRecord f WHERE f.tipo = 'SAIDA'")
    BigDecimal sumSaidas();

    @Query("SELECT COALESCE(SUM(f.valor), 0) FROM FinancialRecord f")
    BigDecimal sumTotal();
    
    @Query("SELECT f FROM FinancialRecord f WHERE f.dataLancamento BETWEEN :startDate AND :endDate ORDER BY f.dataLancamento DESC")
    List<FinancialRecord> findByDataBetween(@Param("startDate") LocalDateTime startDate, 
                                            @Param("endDate") LocalDateTime endDate);
}