package com.vetlife.api.modules.client;

import com.vetlife.api.modules.client.dto.ClientRequest;
import com.vetlife.api.modules.client.dto.ClientResponse;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest dto) {
        return Client.builder()
                .nome(dto.nome())
                .email(dto.email())
                .telefone(dto.telefone())
                .build();
    }

    public ClientResponse toResponse(Client entity) {
        return new ClientResponse(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getTelefone(),
                entity.getCreatedAt()
        );
    }
}