package com.vetlife.api.modules.pet;
import com.vetlife.api.modules.client.Client;
import com.vetlife.api.modules.pet.dto.PetRequest;
import com.vetlife.api.modules.pet.dto.PetResponse;
import org.springframework.stereotype.Component;
@Component
public class PetMapper {
    public Pet toEntity(PetRequest dto, Client client) {
        return Pet.builder()
                .nome(dto.nome()).especie(dto.especie()).raca(dto.raca())
                .dataNascimento(dto.dataNascimento()).client(client).build();
    }
    public PetResponse toResponse(Pet entity) {
        return new PetResponse(
                entity.getId(), entity.getNome(), entity.getEspecie(), entity.getRaca(),
                entity.getDataNascimento(), entity.getClient().getId(), entity.getClient().getNome()
        );
    }
}