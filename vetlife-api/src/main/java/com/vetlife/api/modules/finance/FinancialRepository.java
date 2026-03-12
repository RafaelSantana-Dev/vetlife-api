package com.vetlife.api.modules.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface FinancialRepository extends JpaRepository<FinancialRecord, Long> {

    @Query("SELECT COALESCE(SUM(f.valor), 0) FROM FinancialRecord f WHERE f.tipo = 'ENTRADA'")
    BigDecimal sumEntradas();

    @Query("SELECT COALESCE(SUM(f.valor), 0) FROM FinancialRecord f WHERE f.tipo = 'SAIDA'")
    BigDecimal sumSaidas();

    @Query("SELECT COALESCE(SUM(f.valor), 0) FROM FinancialRecord f")
    BigDecimal sumTotal();
}