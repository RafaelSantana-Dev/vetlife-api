package com.vetlife.api.modules.appointment.dto;
import java.time.LocalDateTime;

public record AppointmentResponse(
    Long id,
    LocalDateTime dataHora,
    String motivo,
    String status,
    String nomePet,
    String nomeTutor,
    String nomeVet
) {}