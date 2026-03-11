package com.vetlife.api.modules.appointment;

import com.vetlife.api.modules.appointment.dto.AppointmentRequest;
import com.vetlife.api.modules.appointment.dto.AppointmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public ResponseEntity<AppointmentResponse> agendar(@RequestBody @Valid AppointmentRequest request) {
        AppointmentResponse response = service.agendar(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }
}