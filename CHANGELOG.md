# 📝 Changelog - VetLife API

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
