package com.vetlife.api.modules.storage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
@Tag(name = "Storage", description = "Gerenciamento de arquivos (fotos e documentos)")
@SecurityRequirement(name = "bearer-key")
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/upload/pet-photo")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Upload de foto de pet", description = "Faz upload de uma foto de pet (JPG, PNG)")
    public ResponseEntity<Map<String, String>> uploadPetPhoto(@RequestParam("file") MultipartFile file) {
        String filePath = storageService.uploadFile(file, "pets");
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Foto enviada com sucesso");
        response.put("filePath", filePath);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload/document")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Upload de documento", description = "Faz upload de um documento (PDF, DOC, DOCX)")
    public ResponseEntity<Map<String, String>> uploadDocument(@RequestParam("file") MultipartFile file) {
        String filePath = storageService.uploadFile(file, "documents");
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Documento enviado com sucesso");
        response.put("filePath", filePath);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{category}/{filename}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Download de arquivo", description = "Faz download de um arquivo pelo caminho")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String category,
            @PathVariable String filename) {
        
        String filePath = category + "/" + filename;
        byte[] data = storageService.downloadFile(filePath);
        ByteArrayResource resource = new ByteArrayResource(data);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{category}/{filename}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar arquivo", description = "Deleta um arquivo (apenas ADMIN)")
    public ResponseEntity<Map<String, String>> deleteFile(
            @PathVariable String category,
            @PathVariable String filename) {
        
        String filePath = category + "/" + filename;
        storageService.deleteFile(filePath);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Arquivo deletado com sucesso");
        
        return ResponseEntity.ok(response);
    }
}
