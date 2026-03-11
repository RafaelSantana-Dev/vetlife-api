CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE veterinarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    crmv VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE pets (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    especie VARCHAR(30) NOT NULL,
    raca VARCHAR(50),
    data_nascimento DATE,
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_pet_cliente FOREIGN KEY (client_id) REFERENCES clientes (id)
);

CREATE TABLE consultas (
    id BIGSERIAL PRIMARY KEY,
    data_hora TIMESTAMP NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    status VARCHAR(30) NOT NULL,
    pet_id BIGINT NOT NULL,
    vet_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_consulta_pet FOREIGN KEY (pet_id) REFERENCES pets (id),
    CONSTRAINT fk_consulta_vet FOREIGN KEY (vet_id) REFERENCES veterinarios (id)
);