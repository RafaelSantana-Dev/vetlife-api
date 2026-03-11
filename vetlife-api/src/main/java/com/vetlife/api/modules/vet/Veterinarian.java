package com.vetlife.api.modules.vet;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "veterinarios", indexes = {
    @Index(name = "idx_vet_crmv", columnList = "crmv", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Veterinarian {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 20, unique = true)
    private String crmv;

    @Column(nullable = false, length = 50)
    private String especialidade; // Ex: CLINICA_GERAL, CIRURGIA

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist void onCreate() { this.createdAt = OffsetDateTime.now(); }
}