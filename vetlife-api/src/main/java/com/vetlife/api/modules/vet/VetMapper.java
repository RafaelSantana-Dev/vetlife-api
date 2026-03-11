package com.vetlife.api.modules.vet;

import com.vetlife.api.modules.vet.dto.VetRequest;
import com.vetlife.api.modules.vet.dto.VetResponse;
import org.springframework.stereotype.Component;

@Component
public class VetMapper {

    public Veterinarian toEntity(VetRequest dto) {
        return Veterinarian.builder()
                .nome(dto.nome())
                .crmv(dto.crmv())
                .especialidade(dto.especialidade())
                .email(dto.email())
                .build();
    }

    public VetResponse toResponse(Veterinarian entity) {
        return new VetResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCrmv(),
                entity.getEspecialidade(),
                entity.getEmail(),
                entity.getCreatedAt()
        );
    }
}