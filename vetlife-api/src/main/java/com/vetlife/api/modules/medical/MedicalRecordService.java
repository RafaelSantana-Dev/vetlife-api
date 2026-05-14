package com.vetlife.api.modules.medical;

import com.vetlife.api.modules.medical.dto.MedicalRecordRequest;
import com.vetlife.api.modules.medical.dto.MedicalRecordResponse;
import com.vetlife.api.modules.pet.Pet;
import com.vetlife.api.modules.pet.PetRepository;
import com.vetlife.api.modules.vet.Veterinarian;
import com.vetlife.api.modules.vet.VeterinarianRepository;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    
    private final MedicalRecordRepository repository;
    private final PetRepository petRepository;
    private final VeterinarianRepository vetRepository;
    private final MedicalRecordMapper mapper;
    
    @Transactional
    public MedicalRecordResponse create(MedicalRecordRequest request) {
        log.info("Criando registro médico para pet ID: {}", request.petId());
        
        Pet pet = petRepository.findById(request.petId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));
        
        Veterinarian vet = vetRepository.findById(request.vetId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado"));
        
        MedicalRecord record = mapper.toEntity(request, pet, vet);
        MedicalRecord saved = repository.save(record);
        
        log.info("Registro médico criado com ID: {}", saved.getId());
        return mapper.toResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public MedicalRecordResponse findById(Long id) {
        log.info("Buscando registro médico ID: {}", id);
        MedicalRecord record = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro médico não encontrado"));
        return mapper.toResponse(record);
    }
    
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponse> findByPetId(Long petId, Pageable pageable) {
        log.info("Buscando histórico médico do pet ID: {}", petId);
        return repository.findByPetId(petId, pageable)
                .map(mapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> findAllByPetId(Long petId) {
        log.info("Buscando todo histórico médico do pet ID: {}", petId);
        return repository.findAllByPetId(petId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponse> findByVetId(Long vetId, Pageable pageable) {
        log.info("Buscando registros do veterinário ID: {}", vetId);
        return repository.findByVetId(vetId, pageable)
                .map(mapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponse> findByType(RecordType type, Pageable pageable) {
        log.info("Buscando registros do tipo: {}", type);
        return repository.findByType(type, pageable)
                .map(mapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponse> findAll(Pageable pageable) {
        log.info("Listando todos os registros médicos");
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deletando registro médico ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Registro médico não encontrado");
        }
        repository.deleteById(id);
        log.info("Registro médico deletado com sucesso");
    }
}
