package com.vetlife.api.modules.vet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VetRequest(
    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message = "CRMV é obrigatório")
    @Size(max = 20)
    String crmv,

    @NotBlank(message = "Especialidade é obrigatória")
    String especialidade,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email
) {}