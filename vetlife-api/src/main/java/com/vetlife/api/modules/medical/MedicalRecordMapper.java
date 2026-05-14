package com.vetlife.api.modules.medical;

import com.vetlife.api.modules.medical.dto.MedicalRecordRequest;
import com.vetlife.api.modules.medical.dto.MedicalRecordResponse;
import com.vetlife.api.modules.pet.Pet;
import com.vetlife.api.modules.vet.Veterinarian;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {
    
    public MedicalRecord toEntity(MedicalRecordRequest dto, Pet pet, Veterinarian vet) {
        return MedicalRecord.builder()
                .pet(pet)
                .vet(vet)
                .type(dto.type())
                .title(dto.title())
                .description(dto.description())
                .diagnosis(dto.diagnosis())
                .treatment(dto.treatment())
                .prescription(dto.prescription())
                .attachmentPath(dto.attachmentPath())
                .observations(dto.observations())
                .build();
    }
    
    public MedicalRecordResponse toResponse(MedicalRecord entity) {
        return new MedicalRecordResponse(
                entity.getId(),
                entity.getPet().getId(),
                entity.getPet().getNome(),
                entity.getVet().getId(),
                entity.getVet().getNome(),
                entity.getType(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDiagnosis(),
                entity.getTreatment(),
                entity.getPrescription(),
                entity.getAttachmentPath(),
                entity.getCreatedAt(),
                entity.getObservations()
        );
    }
}
