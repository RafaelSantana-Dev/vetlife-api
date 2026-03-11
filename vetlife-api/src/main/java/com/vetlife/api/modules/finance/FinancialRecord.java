package com.vetlife.api.modules.finance;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name = "lancamentos_financeiros")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FinancialRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private String tipo; // ENTRADA ou SAIDA
    private LocalDateTime dataLancamento;
    @PrePersist void onCreate() { this.dataLancamento = LocalDateTime.now(); }
}