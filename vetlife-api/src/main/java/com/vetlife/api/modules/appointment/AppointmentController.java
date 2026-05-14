package com.vetlife.api.modules.appointment;

import com.vetlife.api.modules.appointment.dto.AppointmentRequest;
import com.vetlife.api.modules.appointment.dto.AppointmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Agendamento avançado de consultas")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentValidationService validationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Agendar consulta", description = "Agenda uma nova consulta com validação de conflitos")
    public ResponseEntity<AppointmentResponse> agendar(@RequestBody @Valid AppointmentRequest request) {
        AppointmentResponse response = service.agendar(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Atualizar consulta", description = "Atualiza uma consulta existente com validação de conflitos")
    public ResponseEntity<AppointmentResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid AppointmentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Cancelar consulta", description = "Cancela uma consulta agendada")
    public ResponseEntity<Map<String, String>> cancelar(@PathVariable Long id) {
        service.cancelar(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Consulta cancelada com sucesso");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available-slots")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Horários disponíveis", description = "Lista horários disponíveis para um veterinário em uma data")
    public ResponseEntity<List<LocalDateTime>> getAvailableSlots(
            @RequestParam Long vetId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        
        List<LocalDateTime> slots = validationService.getAvailableSlots(vetId, date);
        return ResponseEntity.ok(slots);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Listar consultas", description = "Lista todas as consultas (paginado)")
    public ResponseEntity<Page<AppointmentResponse>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }
}