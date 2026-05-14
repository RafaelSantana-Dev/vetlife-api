package com.vetlife.api.modules.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class StorageService {

    @Value("${app.storage.upload-dir:uploads}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".pdf", ".doc", ".docx"};

    public String uploadFile(MultipartFile file, String category) {
        validateFile(file);
        
        try {
            // Criar diretório se não existir
            Path uploadPath = Paths.get(uploadDir, category);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Gerar nome único para o arquivo
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename);
            String newFilename = UUID.randomUUID().toString() + extension;
            
            // Salvar arquivo
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            log.info("Arquivo salvo: {}", filePath);
            return category + "/" + newFilename;
            
        } catch (IOException e) {
            log.error("Erro ao fazer upload do arquivo", e);
            throw new StorageException("Erro ao fazer upload do arquivo: " + e.getMessage());
        }
    }

    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(uploadDir, filePath);
            Files.deleteIfExists(path);
            log.info("Arquivo deletado: {}", path);
        } catch (IOException e) {
            log.error("Erro ao deletar arquivo", e);
            throw new StorageException("Erro ao deletar arquivo: " + e.getMessage());
        }
    }

    public byte[] downloadFile(String filePath) {
        try {
            Path path = Paths.get(uploadDir, filePath);
            if (!Files.exists(path)) {
                throw new StorageException("Arquivo não encontrado: " + filePath);
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Erro ao baixar arquivo", e);
            throw new StorageException("Erro ao baixar arquivo: " + e.getMessage());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Arquivo vazio");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new StorageException("Arquivo muito grande. Tamanho máximo: 5MB");
        }

        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new StorageException("Nome do arquivo inválido");
        }

        String extension = getFileExtension(filename).toLowerCase();
        boolean isAllowed = false;
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (extension.equals(allowedExt)) {
                isAllowed = true;
                break;
            }
        }

        if (!isAllowed) {
            throw new StorageException("Tipo de arquivo não permitido. Permitidos: jpg, jpeg, png, pdf, doc, docx");
        }
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex);
    }
}
