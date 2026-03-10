package com.vetlife.api.modules.client;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
@Entity
@Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100) private String nome;
    @Column(nullable = false, length = 150, unique = true) private String email;
    @Column(length = 20) private String telefone;
    @Column(nullable = false, updatable = false) private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @PrePersist void onCreate() { this.createdAt = OffsetDateTime.now(); this.updatedAt = OffsetDateTime.now(); }
    @PreUpdate void onUpdate() { this.updatedAt = OffsetDateTime.now(); }
}