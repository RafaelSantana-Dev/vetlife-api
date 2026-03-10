package com.vetlife.api.modules.client;

import com.vetlife.api.modules.client.dto.ClientRequest;
import com.vetlife.api.modules.client.dto.ClientResponse;
import com.vetlife.api.shared.exception.BusinessException;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Transactional
    public ClientResponse create(ClientRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado: " + request.email());
        }
        Client saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponse> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado ID: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado ID: " + id);
        }
        repository.deleteById(id);
    }
}