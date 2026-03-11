package com.vetlife.api.modules.pet;
import com.vetlife.api.modules.pet.dto.PetRequest;
import com.vetlife.api.modules.pet.dto.PetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService service;
    @PostMapping
    public ResponseEntity<PetResponse> create(@RequestBody @Valid PetRequest request) {
        PetResponse response = service.create(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
    @GetMapping
    public ResponseEntity<Page<PetResponse>> list(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }
}