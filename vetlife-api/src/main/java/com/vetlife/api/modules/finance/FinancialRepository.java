package com.vetlife.api.modules.finance;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FinancialRepository extends JpaRepository<FinancialRecord, Long> {}