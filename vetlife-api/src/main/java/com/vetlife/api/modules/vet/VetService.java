package com.vetlife.api.modules.vet;

import com.vetlife.api.modules.vet.dto.VetRequest;
import com.vetlife.api.modules.vet.dto.VetResponse;
import com.vetlife.api.shared.exception.BusinessException;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VeterinarianRepository repository;
    private final VetMapper mapper;

    @Transactional
    public VetResponse create(VetRequest request) {
        if (repository.existsByCrmv(request.crmv())) {
            throw new BusinessException("Já existe um veterinário com este CRMV.");
        }
        if (repository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um veterinário com este E-mail.");
        }

        Veterinarian saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<VetResponse> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public VetResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado ID: " + id));
    }
}