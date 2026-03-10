package com.vetlife.api.modules.client.dto;

import java.time.OffsetDateTime;

public record ClientResponse(
    Long id,
    String nome,
    String email,
    String telefone,
    OffsetDateTime createdAt
) {}