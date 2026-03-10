package com.vetlife.api.modules.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequest(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100)
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    String email,

    @Size(max = 20)
    String telefone
) {}