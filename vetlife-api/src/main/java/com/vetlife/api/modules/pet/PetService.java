package com.vetlife.api.modules.pet;
import com.vetlife.api.modules.client.Client;
import com.vetlife.api.modules.client.ClientRepository;
import com.vetlife.api.modules.pet.dto.PetRequest;
import com.vetlife.api.modules.pet.dto.PetResponse;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final ClientRepository clientRepository;
    private final PetMapper mapper;
    @Transactional
    public PetResponse create(PetRequest request) {
        Client dono = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com ID: " + request.clientId()));
        Pet pet = mapper.toEntity(request, dono);
        Pet saved = petRepository.save(pet);
        return mapper.toResponse(saved);
    }
    @Transactional(readOnly = true)
    public Page<PetResponse> listAll(Pageable pageable) {
        return petRepository.findAll(pageable).map(mapper::toResponse);
    }
}