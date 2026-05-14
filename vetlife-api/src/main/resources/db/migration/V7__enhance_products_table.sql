-- Adicionar novos campos na tabela produtos
ALTER TABLE produtos 
ADD COLUMN descricao VARCHAR(500),
ADD COLUMN estoque_minimo INTEGER NOT NULL DEFAULT 10,
ADD COLUMN categoria VARCHAR(50),
ADD COLUMN fornecedor VARCHAR(50),
ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT true,
ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();

-- Atualizar constraint de nome para NOT NULL
ALTER TABLE produtos ALTER COLUMN nome SET NOT NULL;
ALTER TABLE produtos ALTER COLUMN preco SET NOT NULL;
ALTER TABLE produtos ALTER COLUMN estoque SET NOT NULL;

-- Criar índices para melhorar performance
CREATE INDEX idx_produtos_categoria ON produtos(categoria);
CREATE INDEX idx_produtos_ativo ON produtos(ativo);
CREATE INDEX idx_produtos_estoque_baixo ON produtos(estoque, estoque_minimo) WHERE estoque <= estoque_minimo;

-- Comentários para documentação
COMMENT ON COLUMN produtos.estoque_minimo IS 'Quantidade mínima de estoque antes de gerar alerta';
COMMENT ON COLUMN produtos.ativo IS 'Indica se o produto está ativo no sistema';
