package com.vetlife.api.modules.medical;

import com.vetlife.api.modules.medical.dto.MedicalRecordRequest;
import com.vetlife.api.modules.medical.dto.MedicalRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
@Tag(name = "Prontuário Médico", description = "Gestão de histórico médico dos pets")
@SecurityRequirement(name = "bearer-key")
public class MedicalRecordController {
    
    private final MedicalRecordService service;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VET')")
    @Operation(summary = "Criar registro médico", description = "Cria um novo registro no prontuário (apenas ADMIN e VET)")
    public ResponseEntity<MedicalRecordResponse> create(@Valid @RequestBody MedicalRecordRequest request) {
        MedicalRecordResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Buscar registro por ID", description = "Retorna um registro médico específico")
    public ResponseEntity<MedicalRecordResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Buscar histórico do pet", description = "Retorna todo o histórico médico de um pet (paginado)")
    public ResponseEntity<Page<MedicalRecordResponse>> findByPetId(
            @PathVariable Long petId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findByPetId(petId, pageable));
    }
    
    @GetMapping("/pet/{petId}/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET')")
    @Operation(summary = "Buscar histórico completo do pet", description = "Retorna todo o histórico médico de um pet (sem paginação)")
    public ResponseEntity<List<MedicalRecordResponse>> findAllByPetId(@PathVariable Long petId) {
        return ResponseEntity.ok(service.findAllByPetId(petId));
    }
    
    @GetMapping("/vet/{vetId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET')")
    @Operation(summary = "Buscar registros do veterinário", description = "Retorna todos os registros de um veterinário")
    public ResponseEntity<Page<MedicalRecordResponse>> findByVetId(
            @PathVariable Long vetId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findByVetId(vetId, pageable));
    }
    
    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET')")
    @Operation(summary = "Buscar por tipo", description = "Retorna registros de um tipo específico (CONSULTA, VACINA, etc)")
    public ResponseEntity<Page<MedicalRecordResponse>> findByType(
            @PathVariable RecordType type,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findByType(type, pageable));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VET')")
    @Operation(summary = "Listar todos os registros", description = "Lista todos os registros médicos (paginado)")
    public ResponseEntity<Page<MedicalRecordResponse>> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar registro", description = "Deleta um registro médico (apenas ADMIN)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
