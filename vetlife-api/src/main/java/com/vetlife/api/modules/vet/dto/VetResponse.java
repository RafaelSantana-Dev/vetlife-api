package com.vetlife.api.modules.vet.dto;

import java.time.OffsetDateTime;

public record VetResponse(
    Long id,
    String nome,
    String crmv,
    String especialidade,
    String email,
    OffsetDateTime createdAt
) {}