package com.vetlife.api.modules.adoption.dto;

import jakarta.validation.constraints.NotBlank;

public record AdoptionRequest(
    @NotBlank(message = "Nome e obrigatorio")
    String nome,

    @NotBlank(message = "Especie e obrigatoria")
    String especie,

    String descricao
) {}