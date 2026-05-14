-- Adicionar campo photo_path na tabela pets
ALTER TABLE pets ADD COLUMN photo_path VARCHAR(255);

-- Criar índice para melhorar performance de busca
CREATE INDEX idx_pets_photo_path ON pets(photo_path) WHERE photo_path IS NOT NULL;
