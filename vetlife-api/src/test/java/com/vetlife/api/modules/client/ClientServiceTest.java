package com.vetlife.api.modules.client;

import com.vetlife.api.modules.client.dto.ClientRequest;
import com.vetlife.api.modules.client.dto.ClientResponse;
import com.vetlife.api.shared.exception.BusinessException;
import com.vetlife.api.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientService - Testes Unitários")
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ClientService service;

    private ClientRequest validRequest;
    private Client validClient;
    private ClientResponse validResponse;

    @BeforeEach
    void setUp() {
        validRequest = new ClientRequest("João Silva", "joao@email.com", "11999999999");
        
        validClient = Client.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .telefone("11999999999")
                .ativo(true)
                .createdAt(OffsetDateTime.now())
                .build();
        
        validResponse = new ClientResponse(
                1L,
                "João Silva",
                "joao@email.com",
                "11999999999",
                true,
                OffsetDateTime.now()
        );
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso")
    void shouldCreateClientSuccessfully() {
        // Arrange
        when(repository.existsByEmail(validRequest.email())).thenReturn(false);
        when(mapper.toEntity(validRequest)).thenReturn(validClient);
        when(repository.save(any(Client.class))).thenReturn(validClient);
        when(mapper.toResponse(validClient)).thenReturn(validResponse);

        // Act
        ClientResponse result = service.create(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.nome());
        assertEquals("joao@email.com", result.email());
        verify(repository, times(1)).existsByEmail(validRequest.email());
        verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar cliente com email duplicado")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(repository.existsByEmail(validRequest.email())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.create(validRequest)
        );
        
        assertTrue(exception.getMessage().contains("Email já cadastrado"));
        verify(repository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Deve buscar cliente por ID com sucesso")
    void shouldFindClientByIdSuccessfully() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(validClient));
        when(mapper.toResponse(validClient)).thenReturn(validResponse);

        // Act
        ClientResponse result = service.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("João Silva", result.nome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente inexistente")
    void shouldThrowExceptionWhenClientNotFound() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(999L)
        );
        
        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void shouldDeleteClientSuccessfully() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // Act
        service.delete(1L);

        // Assert
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cliente inexistente")
    void shouldThrowExceptionWhenDeletingNonExistentClient() {
        // Arrange
        when(repository.existsById(999L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(999L)
        );
        
        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
        verify(repository, never()).deleteById(any());
    }
}
