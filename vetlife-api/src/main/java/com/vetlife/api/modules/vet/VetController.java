package com.vetlife.api.modules.vet;

import com.vetlife.api.modules.vet.dto.VetRequest;
import com.vetlife.api.modules.vet.dto.VetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/vets")
@RequiredArgsConstructor
public class VetController {

    private final VetService service;

    @PostMapping
    public ResponseEntity<VetResponse> create(@RequestBody @Valid VetRequest request) {
        VetResponse response = service.create(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<VetResponse>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}