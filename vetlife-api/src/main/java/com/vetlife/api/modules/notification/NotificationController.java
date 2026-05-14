package com.vetlife.api.modules.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "Envio de notificações por email")
@SecurityRequirement(name = "bearer-key")
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/test-email")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Testar envio de email", description = "Envia um email de teste (apenas ADMIN)")
    public ResponseEntity<Map<String, String>> testEmail(@RequestParam String email) {
        emailService.sendWelcomeEmail(email, "Usuário Teste");
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Email de teste enviado para: " + email);
        response.put("note", "Verifique os logs para confirmar o envio");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/appointment-confirmation")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Enviar confirmação de consulta", description = "Envia email de confirmação de consulta")
    public ResponseEntity<Map<String, String>> sendAppointmentConfirmation(
            @RequestParam String email,
            @RequestParam String clientName,
            @RequestParam String petName,
            @RequestParam String appointmentDate,
            @RequestParam String vetName) {
        
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDate);
        emailService.sendAppointmentConfirmation(email, clientName, petName, dateTime, vetName);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Email de confirmação enviado");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/appointment-reminder")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Enviar lembrete de consulta", description = "Envia email de lembrete de consulta")
    public ResponseEntity<Map<String, String>> sendAppointmentReminder(
            @RequestParam String email,
            @RequestParam String clientName,
            @RequestParam String petName,
            @RequestParam String appointmentDate,
            @RequestParam String vetName) {
        
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDate);
        emailService.sendAppointmentReminder(email, clientName, petName, dateTime, vetName);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Email de lembrete enviado");
        
        return ResponseEntity.ok(response);
    }
}
