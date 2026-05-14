package com.vetlife.api.modules.pet.dto;
import java.time.LocalDate;
public record PetResponse(
    Long id, String nome, String especie, String raca, LocalDate dataNascimento, String photoPath, Long clientId, String nomeTutor
) {}