package com.vetlife.api.modules.adoption;

import com.vetlife.api.modules.adoption.dto.AdoptionRequest;
import com.vetlife.api.modules.adoption.dto.AdoptionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/adoption")
@RequiredArgsConstructor
public class AdoptionPetController {

    private final AdoptionService service;

    @PostMapping
    public ResponseEntity<AdoptionResponse> create(@RequestBody @Valid AdoptionRequest request) {
        AdoptionResponse response = service.create(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AdoptionResponse>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @PatchMapping("/{id}/adopt")
    public ResponseEntity<AdoptionResponse> adopt(@PathVariable Long id) {
        return ResponseEntity.ok(service.adopt(id));
    }
}