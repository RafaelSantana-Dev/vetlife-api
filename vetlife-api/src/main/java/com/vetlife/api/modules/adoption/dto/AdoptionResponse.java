package com.vetlife.api.modules.adoption.dto;

public record AdoptionResponse(
    Long id,
    String nome,
    String especie,
    String descricao,
    boolean disponivel
) {}