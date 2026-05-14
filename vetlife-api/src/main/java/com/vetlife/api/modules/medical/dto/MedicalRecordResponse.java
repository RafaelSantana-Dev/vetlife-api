package com.vetlife.api.modules.medical.dto;

import com.vetlife.api.modules.medical.RecordType;

import java.time.LocalDateTime;

public record MedicalRecordResponse(
    Long id,
    Long petId,
    String petName,
    Long vetId,
    String vetName,
    RecordType type,
    String title,
    String description,
    String diagnosis,
    String treatment,
    String prescription,
    String attachmentPath,
    LocalDateTime createdAt,
    String observations
) {}
