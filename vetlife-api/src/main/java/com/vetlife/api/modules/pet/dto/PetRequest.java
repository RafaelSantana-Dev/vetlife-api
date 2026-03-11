package com.vetlife.api.modules.pet.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
public record PetRequest(
    @NotBlank(message = "Nome do pet é obrigatório") String nome,
    @NotBlank(message = "Espécie é obrigatória") String especie,
    String raca,
    LocalDate dataNascimento,
    @NotNull(message = "ID do tutor é obrigatório") Long clientId
) {}