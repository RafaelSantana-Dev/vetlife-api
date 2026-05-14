-- Criar tabela de registros médicos (prontuário eletrônico)
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
    
    CONSTRAINT fk_medical_records_pet FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE,
    CONSTRAINT fk_medical_records_vet FOREIGN KEY (vet_id) REFERENCES veterinarios(id) ON DELETE RESTRICT
);

-- Criar índices para melhorar performance
CREATE INDEX idx_medical_records_pet_id ON medical_records(pet_id);
CREATE INDEX idx_medical_records_vet_id ON medical_records(vet_id);
CREATE INDEX idx_medical_records_type ON medical_records(type);
CREATE INDEX idx_medical_records_created_at ON medical_records(created_at DESC);

-- Comentários para documentação
COMMENT ON TABLE medical_records IS 'Prontuário eletrônico - histórico médico dos pets';
COMMENT ON COLUMN medical_records.type IS 'Tipo: CONSULTA, EMERGENCIA, CIRURGIA, VACINA, EXAME, RETORNO, OUTRO';
COMMENT ON COLUMN medical_records.attachment_path IS 'Caminho para anexos (exames, laudos, etc)';
