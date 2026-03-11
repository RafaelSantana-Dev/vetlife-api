package com.vetlife.api.modules.client;

import com.vetlife.api.modules.client.dto.ClientRequest;
import com.vetlife.api.modules.client.dto.ClientResponse;
import com.vetlife.api.shared.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ClientService service;

    @Test
    @DisplayName("Deve criar um cliente com sucesso quando o e-mail não existir")
    void create_Success() {
        // Arrange (Preparação)
        ClientRequest request = new ClientRequest("Rafael", "rafael@email.com", "11999999999");
        Client entity = new Client(1L, "Rafael", "rafael@email.com", "11999999999", OffsetDateTime.now(), null);
        ClientResponse response = new ClientResponse(1L, "Rafael", "rafael@email.com", "11999999999", OffsetDateTime.now());

        // Simulando que o banco diz que o e-mail NÃO existe
        when(repository.existsByEmail("rafael@email.com")).thenReturn(false);
        
        // Simulando a conversão e o salvamento
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(any(Client.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        // Act (Ação)
        ClientResponse result = service.create(request);

        // Assert (Verificação)
        assertNotNull(result);
        assertEquals("Rafael", result.nome());
        verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Deve estourar BusinessException ao tentar criar cliente com e-mail já existente")
    void create_EmailAlreadyExists_ThrowsBusinessException() {
        // Arrange
        ClientRequest request = new ClientRequest("Duplicado", "rafael@email.com", "11888888888");

        // Simulando que o banco diz que o e-mail JÁ existe
        when(repository.existsByEmail("rafael@email.com")).thenReturn(true);

        // Act & Assert (Ação e Verificação embutida esperando o erro)
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.create(request);
        });

        assertEquals("Email já cadastrado: rafael@email.com", exception.getMessage());
        
        // Garante que NUNCA tentou salvar no banco
        verify(repository, never()).save(any(Client.class));
    }
}