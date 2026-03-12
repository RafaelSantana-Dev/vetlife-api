package com.vetlife.api.modules.adoption;

import com.vetlife.api.modules.adoption.dto.AdoptionRequest;
import com.vetlife.api.modules.adoption.dto.AdoptionResponse;
import com.vetlife.api.shared.exception.BusinessException;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final AdoptionPetRepository repository;
    private final AdoptionMapper mapper;

    @Transactional
    public AdoptionResponse create(AdoptionRequest request) {
        AdoptionPet saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<AdoptionResponse> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional
    public AdoptionResponse adopt(Long id) {
        AdoptionPet pet = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado ID: " + id));

        if (!pet.isDisponivel()) {
            throw new BusinessException("Este animal ja foi adotado.");
        }

        pet.setDisponivel(false);
        repository.save(pet);
        return mapper.toResponse(pet);
    }
}