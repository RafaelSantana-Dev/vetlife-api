package com.vetlife.api.modules.appointment;

import com.vetlife.api.modules.appointment.dto.AppointmentRequest;
import com.vetlife.api.modules.appointment.dto.AppointmentResponse;
import com.vetlife.api.modules.pet.Pet;
import com.vetlife.api.modules.vet.Veterinarian;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    public Appointment toEntity(AppointmentRequest dto, Pet pet, Veterinarian vet) {
        return Appointment.builder()
                .dataHora(dto.dataHora())
                .motivo(dto.motivo())
                .status("AGENDADA")
                .pet(pet)
                .vet(vet)
                .build();
    }

    public AppointmentResponse toResponse(Appointment entity) {
        return new AppointmentResponse(
                entity.getId(),
                entity.getDataHora(),
                entity.getMotivo(),
                entity.getStatus(),
                entity.getPet().getNome(),
                entity.getPet().getClient().getNome(),
                entity.getVet().getNome()
        );
    }
}