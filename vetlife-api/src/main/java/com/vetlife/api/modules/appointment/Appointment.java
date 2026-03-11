package com.vetlife.api.modules.appointment;

import com.vetlife.api.modules.pet.Pet;
import com.vetlife.api.modules.vet.Veterinarian;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false, length = 200)
    private String motivo;

    @Column(nullable = false, length = 30)
    private String status; // AGENDADA, CANCELADA, REALIZADA

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id", nullable = false)
    private Veterinarian vet;

    private LocalDateTime createdAt;
    @PrePersist void onCreate() { this.createdAt = LocalDateTime.now(); }
}