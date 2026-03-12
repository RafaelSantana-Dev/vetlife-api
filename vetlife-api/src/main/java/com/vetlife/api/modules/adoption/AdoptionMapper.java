package com.vetlife.api.modules.adoption;

import com.vetlife.api.modules.adoption.dto.AdoptionRequest;
import com.vetlife.api.modules.adoption.dto.AdoptionResponse;
import org.springframework.stereotype.Component;

@Component
public class AdoptionMapper {

    public AdoptionPet toEntity(AdoptionRequest dto) {
        return AdoptionPet.builder()
                .nome(dto.nome())
                .especie(dto.especie())
                .descricao(dto.descricao())
                .disponivel(true)
                .build();
    }

    public AdoptionResponse toResponse(AdoptionPet entity) {
        return new AdoptionResponse(
                entity.getId(),
                entity.getNome(),
                entity.getEspecie(),
                entity.getDescricao(),
                entity.isDisponivel()
        );
    }
}