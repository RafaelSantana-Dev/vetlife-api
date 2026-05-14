# 📝 Changelog - VetLife API

## [v2.2.0] - 2026-05-14

### 🎉 Novas Funcionalidades

#### Sistema de Notificações por Email
- ✅ Envio assíncrono de emails com `@Async`
- ✅ Email de confirmação de consulta
- ✅ Email de lembrete de consulta
- ✅ Email de boas-vindas para novos clientes
- ✅ Templates personalizados e formatados
- ✅ Configuração via `application.yaml` (habilitável/desabilitável)
- ✅ Suporte a SMTP (Gmail, SendGrid, etc)

#### Sistema de Estoque Avançado
- ✅ Campos adicionais: descrição, categoria, fornecedor, estoque mínimo
- ✅ Soft delete (campo `ativo`)
- ✅ Auditoria automática (created_at, updated_at)
- ✅ Alerta de estoque baixo (endpoint `/low-stock`)
- ✅ Movimentações de estoque (entrada e saída)
- ✅ Busca por categoria e nome
- ✅ DTOs completos (ProductRequest, ProductResponse)
- ✅ Validações de negócio aprimoradas

### 🔧 Melhorias Técnicas
- ✅ `@EnableAsync` para processamento assíncrono
- ✅ Dependência `spring-boot-starter-mail` adicionada
- ✅ Migration V7 para melhorias na tabela produtos
- ✅ ProductMapper para conversão de DTOs
- ✅ Queries customizadas no ProductRepository
- ✅ Logs estruturados em todos os serviços

### 📚 Documentação
- ✅ README atualizado com novos módulos
- ✅ Documentação de configuração de email
- ✅ Exemplos de uso dos novos endpoints

### 🗄️ Banco de Dados
```sql
-- Migration V7
ALTER TABLE produtos 
ADD COLUMN descricao VARCHAR(500),
ADD COLUMN estoque_minimo INTEGER NOT NULL DEFAULT 10,
ADD COLUMN categoria VARCHAR(50),
ADD COLUMN fornecedor VARCHAR(50),
ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT true,
ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();
```

### 📋 Novos Endpoints

#### Notificações
- `POST /api/v1/notifications/test-email` - Testar envio de email (ADMIN)
- `POST /api/v1/notifications/appointment-confirmation` - Enviar confirmação
- `POST /api/v1/notifications/appointment-reminder` - Enviar lembrete

#### Loja (Estoque Avançado)
- `POST /api/v1/store` - Criar produto
- `GET /api/v1/store/{id}` - Buscar produto por ID
- `GET /api/v1/store` - Listar produtos (paginado)
- `GET /api/v1/store/categoria/{categoria}` - Buscar por categoria
- `GET /api/v1/store/search?nome=` - Buscar por nome
- `GET /api/v1/store/low-stock` - Produtos com estoque baixo
- `PUT /api/v1/store/{id}` - Atualizar produto
- `POST /api/v1/store/{id}/add-stock` - Adicionar estoque
- `POST /api/v1/store/{id}/remove-stock` - Remover estoque
- `POST /api/v1/store/{id}/sell/{quantity}` - Vender produto
- `DELETE /api/v1/store/{id}` - Inativar produto (ADMIN)

### ⚙️ Configuração de Email
```yaml
app:
  email:
    enabled: false  # Alterar para true quando configurar SMTP

spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
```

---

## [v2.1.0] - 2026-05-14

### 🎉 Novas Funcionalidades

#### Sistema de Upload de Arquivos
- ✅ Upload de fotos de pets (JPG, JPEG, PNG)
- ✅ Upload de documentos (PDF, DOC, DOCX)
- ✅ Validação automática de tipo e tamanho (máximo 5MB)
- ✅ Download de arquivos
- ✅ Deleção de arquivos (apenas ADMIN)
- ✅ Organização por categoria (pets, documents)

#### Prontuário Eletrônico (Histórico Médico)
- ✅ Registro completo de histórico médico dos pets
- ✅ Tipos de registro: CONSULTA, EMERGENCIA, CIRURGIA, VACINA, EXAME, RETORNO, OUTRO
- ✅ Campos detalhados: diagnóstico, tratamento, prescrição, observações
- ✅ Suporte para anexos (exames, laudos)
- ✅ Busca por pet, veterinário e tipo de registro
- ✅ Auditoria automática com data de criação

### 🔧 Melhorias Técnicas
- ✅ Campo `photoPath` adicionado na entidade Pet
- ✅ Configuração de multipart no Spring Boot
- ✅ Tratamento de exceção `StorageException`
- ✅ Migration V5 para adicionar photo_path em pets
- ✅ Migration V6 para criar tabela medical_records

### 📚 Documentação
- ✅ README atualizado com novos módulos
- ✅ Tabela de permissões atualizada
- ✅ Documentação dos novos endpoints

### 🗄️ Banco de Dados
```sql
-- Migration V5
ALTER TABLE pets ADD COLUMN photo_path VARCHAR(255);

-- Migration V6
CREATE TABLE medical_records (
    id BIGSERIAL PRIMARY KEY,
    pet_id BIGINT NOT NULL,
    vet_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    diagnosis VARCHAR(200),
    treatment TEXT,
    prescription TEXT,
    attachment_path VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    observations VARCHAR(500),
    CONSTRAINT fk_medical_records_pet FOREIGN KEY (pet_id) REFERENCES pets(id),
    CONSTRAINT fk_medical_records_vet FOREIGN KEY (vet_id) REFERENCES veterinarios(id)
);
```

### 📋 Novos Endpoints

#### Storage
- `POST /api/v1/storage/upload/pet-photo` - Upload de foto de pet
- `POST /api/v1/storage/upload/document` - Upload de documento
- `GET /api/v1/storage/download/{category}/{filename}` - Download de arquivo
- `DELETE /api/v1/storage/{category}/{filename}` - Deletar arquivo (ADMIN)

#### Prontuário Médico
- `POST /api/v1/medical-records` - Criar registro médico (ADMIN, VET)
- `GET /api/v1/medical-records/{id}` - Buscar registro por ID
- `GET /api/v1/medical-records/pet/{petId}` - Histórico do pet (paginado)
- `GET /api/v1/medical-records/pet/{petId}/all` - Histórico completo do pet
- `GET /api/v1/medical-records/vet/{vetId}` - Registros do veterinário
- `GET /api/v1/medical-records/type/{type}` - Buscar por tipo
- `GET /api/v1/medical-records` - Listar todos os registros
- `DELETE /api/v1/medical-records/{id}` - Deletar registro (ADMIN)

---

## [v2.0.0] - 2026-05-14

### 🎉 Novas Funcionalidades

#### Sistema de Roles e Permissões
- ✅ Implementado sistema de roles com 3 níveis: ADMIN, VET e RECEPTIONIST
- ✅ Controle de acesso granular usando `@PreAuthorize`
- ✅ Novos endpoints para gestão de usuários (apenas ADMIN)
- ✅ Campo `active` para ativar/desativar usuários

#### Auditoria Automática
- ✅ Rastreamento automático de criação (`@CreatedDate`)
- ✅ Rastreamento automático de modificação (`@LastModifiedDate`)
- ✅ Habilitado JPA Auditing em toda a aplicação

#### Novos Endpoints
- `GET /api/v1/auth/me` - Obter informações do usuário logado
- `GET /api/v1/auth/users` - Listar todos os usuários (apenas ADMIN)
- `PATCH /api/v1/auth/users/{id}/toggle` - Ativar/desativar usuário (apenas ADMIN)

### 🔧 Melhorias Técnicas
- ✅ Adicionado `@EnableMethodSecurity` para controle de acesso por anotações
- ✅ Atualizado `LoginResponse` para incluir informações de role
- ✅ Criado `UserResponse` DTO para retorno de dados de usuário
- ✅ Migration V4 para adicionar colunas de role e auditoria

### 📚 Documentação
- ✅ README atualizado com novas funcionalidades
- ✅ Adicionada tabela de permissões por role
- ✅ Documentação dos novos endpoints
- ✅ Roadmap de melhorias futuras

### 🗄️ Banco de Dados
```sql
-- Migration V4
ALTER TABLE usuarios 
ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'RECEPTIONIST',
ADD COLUMN active BOOLEAN NOT NULL DEFAULT true,
ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();

CREATE INDEX idx_usuarios_role ON usuarios(role);
CREATE INDEX idx_usuarios_active ON usuarios(active);
```

### 🔄 Breaking Changes
- ⚠️ O endpoint `POST /api/v1/auth/register` agora requer o campo `role`
- ⚠️ O endpoint `POST /api/v1/auth/login` agora retorna `role` na resposta
- ⚠️ Usuários existentes receberão automaticamente a role `RECEPTIONIST`

### 📋 Próximos Passos
1. Implementar paginação e filtros avançados
2. Adicionar testes automatizados (cobertura 80%+)
3. Sistema de upload de arquivos
4. Histórico médico completo
5. Notificações por email
6. Relatórios em PDF

---

## [v1.0.0] - 2026-03-01

### 🎉 Lançamento Inicial
- ✅ Módulo de Autenticação (JWT)
- ✅ Módulo de Clientes (Soft Delete)
- ✅ Módulo de Pets
- ✅ Módulo de Veterinários (Redis Cache)
- ✅ Módulo de Consultas
- ✅ Módulo de Adoção
- ✅ Módulo de Loja (E-commerce)
- ✅ Módulo Financeiro
- ✅ Dashboard com estatísticas
- ✅ Documentação Swagger/OpenAPI
- ✅ Docker Compose (PostgreSQL + Redis)
- ✅ Flyway Migrations
- ✅ Tratamento global de exceções (RFC 7807)
