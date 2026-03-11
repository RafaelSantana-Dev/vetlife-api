package com.vetlife.api.modules.appointment.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentRequest(
    @NotNull(message = "Data e hora são obrigatórias")
    @FutureOrPresent(message = "A consulta não pode ser agendada no passado")
    LocalDateTime dataHora,

    @NotBlank(message = "Motivo da consulta é obrigatório")
    String motivo,

    @NotNull(message = "ID do Pet é obrigatório")
    Long petId,

    @NotNull(message = "ID do Veterinário é obrigatório")
    Long vetId
) {}