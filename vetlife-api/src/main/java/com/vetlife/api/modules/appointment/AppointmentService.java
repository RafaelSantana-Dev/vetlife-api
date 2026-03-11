package com.vetlife.api.modules.appointment;
import com.vetlife.api.modules.pet.*;
import com.vetlife.api.modules.vet.*;
import com.vetlife.api.modules.finance.*;
import com.vetlife.api.modules.appointment.dto.*;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service @RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VeterinarianRepository vetRepository;
    private final FinancialRepository financialRepository;
    private final AppointmentMapper mapper;

    @Transactional
    public AppointmentResponse agendar(AppointmentRequest request) {
        Pet pet = petRepository.findById(request.petId()).orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));
        Veterinarian vet = vetRepository.findById(request.vetId()).orElseThrow(() -> new ResourceNotFoundException("Vet não encontrado"));
        
        Appointment saved = appointmentRepository.save(mapper.toEntity(request, pet, vet));

        // Financeiro: Toda consulta gera uma entrada de R$ 150,00 (Valor Fixo Exemplo)
        financialRepository.save(FinancialRecord.builder()
            .descricao("Consulta Pet: " + pet.getNome() + " com " + vet.getNome())
            .valor(new BigDecimal("150.00"))
            .tipo("ENTRADA")
            .build());

        return mapper.toResponse(saved);
    }
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> listar(Pageable pageable) { return appointmentRepository.findAll(pageable).map(mapper::toResponse); }
}