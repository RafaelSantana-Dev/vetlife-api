package com.vetlife.api.modules.appointment;

import com.vetlife.api.modules.appointment.dto.AppointmentRequest;
import com.vetlife.api.modules.appointment.dto.AppointmentResponse;
import com.vetlife.api.modules.pet.Pet;
import com.vetlife.api.modules.pet.PetRepository;
import com.vetlife.api.modules.vet.Veterinarian;
import com.vetlife.api.modules.vet.VeterinarianRepository;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VeterinarianRepository vetRepository;
    private final AppointmentMapper mapper;

    @Transactional
    public AppointmentResponse agendar(AppointmentRequest request) {
        Pet pet = petRepository.findById(request.petId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado ID: " + request.petId()));

        Veterinarian vet = vetRepository.findById(request.vetId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado ID: " + request.vetId()));

        Appointment consulta = mapper.toEntity(request, pet, vet);
        Appointment saved = appointmentRepository.save(consulta);

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<AppointmentResponse> listar(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(mapper::toResponse);
    }
}