package com.vetlife.api.modules.client;

import com.vetlife.api.modules.client.dto.ClientRequest;
import com.vetlife.api.modules.client.dto.ClientResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid ClientRequest request) {
        ClientResponse response = service.create(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> list(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}