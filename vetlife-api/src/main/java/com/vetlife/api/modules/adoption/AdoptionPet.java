package com.vetlife.api.modules.adoption;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name = "animais_adocao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdoptionPet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String especie;
    private String descricao;
    @Builder.Default
    private boolean disponivel = true;
}