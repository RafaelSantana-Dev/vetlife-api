package com.vetlife.api.modules.pet;

import com.vetlife.api.modules.client.Client;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50) private String nome;
    @Column(nullable = false, length = 30) private String especie;
    @Column(length = 50) private String raca;
    private LocalDate dataNascimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}