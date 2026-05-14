-- Adicionar colunas de role e auditoria na tabela usuarios
ALTER TABLE usuarios 
ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'RECEPTIONIST',
ADD COLUMN active BOOLEAN NOT NULL DEFAULT true,
ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();

-- Criar índice para melhorar performance de consultas por role
CREATE INDEX idx_usuarios_role ON usuarios(role);
CREATE INDEX idx_usuarios_active ON usuarios(active);
