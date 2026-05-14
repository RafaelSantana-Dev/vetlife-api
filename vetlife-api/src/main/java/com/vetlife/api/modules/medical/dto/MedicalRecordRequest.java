package com.vetlife.api.modules.medical.dto;

import com.vetlife.api.modules.medical.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicalRecordRequest(
    @NotNull(message = "ID do pet é obrigatório") 
    Long petId,
    
    @NotNull(message = "ID do veterinário é obrigatório") 
    Long vetId,
    
    @NotNull(message = "Tipo de registro é obrigatório") 
    RecordType type,
    
    @NotBlank(message = "Título é obrigatório") 
    String title,
    
    String description,
    String diagnosis,
    String treatment,
    String prescription,
    String attachmentPath,
    String observations
) {}
