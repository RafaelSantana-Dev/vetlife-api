# 📝 Changelog - VetLife API

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
