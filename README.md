# 🐾 VetLife API

![Java CI](https://github.com/RafaelSantana-Dev/vetlife-api/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-brightgreen?style=flat&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Cache-red?style=flat&logo=redis&logoColor=white)
![Security](https://img.shields.io/badge/Spring_Security-JWT-critical?style=flat&logo=springsecurity&logoColor=white)

Sistema backend (API REST) para **gestão de clínicas veterinárias**, desenvolvido com **Java 21** e **Spring Boot 3**, com arquitetura RESTful e foco total em **boas práticas**, **organização modular**, **performance** e **segurança**.

A aplicação centraliza operações de uma clínica em três módulos principais: **🐾 Adoção**, **🩺 Clínica Veterinária** e **🛍️ Loja de produtos**.

---

## 🖥️ Demonstração (GIF)

> Coloque seu GIF em docs/demo.gif (ou ajuste o caminho abaixo).

![Demonstração do VetLife API](docs/demo.gif)

---

## 📌 Visão Geral

O **VetLife API** simula um sistema completo para clínicas veterinárias, permitindo gerenciar:

- 🩺 **Clínica Veterinária**: pacientes, veterinários, consultas e histórico de atendimentos.
- 🐾 **Adoção**: cadastro de animais, processos de adoção e acompanhamento de status.
- 🛍️ **Loja**: produtos, estoque, vendas e atualização automática de estoque.

Toda a especificação de rotas e modelos está documentada via **Swagger/OpenAPI**.

---

## ⚙️ Funcionalidades

### 🐾 Módulo de Adoção
- Cadastro de animais disponíveis para adoção.
- Listagem e consulta por filtros.
- Registro de processos de adoção.
- Acompanhamento do status das adoções.

### 🩺 Módulo de Clínica Veterinária & Autenticação
- **Autenticação:** Login seguro via Token JWT.
- **Cadastro de pacientes:** Vinculado obrigatoriamente a um Tutor.
- **Cadastro de veterinários:** Com validação de CRMV e cache de listagem.
- **Agendamento de consultas:** Validando conflitos de datas.
- **Histórico:** Histórico médico de atendimentos veterinários.

### 🛍️ Módulo de Loja
- Cadastro de produtos.
- Controle de estoque.
- Registro de vendas.
- Baixa automática do estoque ao vender.

---

## 🧪 Tecnologias Utilizadas

- ☕ **Java 21**
- 🌱 **Spring Boot 3.2.3**
- 🗄️ **Spring Data JPA**
- 🐘 **PostgreSQL** (Banco de dados principal)
- 🔴 **Redis** (Cache de alta performance)
- 🐳 **Docker / Docker Compose**
- 📦 **Maven / Maven Wrapper**
- 📑 **Swagger / OpenAPI 3**
- ⚡ **Lombok**
- 🔁 **Flyway** (Migrações de banco de dados)
- 🔒 **Spring Security + JWT + BCrypt**
- 🧪 **JUnit 5 e Mockito**

---

## 🏗️ Arquitetura do Projeto

O projeto segue a arquitetura **Package-by-Feature** (Modular Monolith).

Cada módulo segue boas práticas de arquitetura RESTful, com separação clara entre as responsabilidades: **Controller, Service, Mapper, Repository, DTOs e Entities**.

Estrutura base do projeto:

    src/main/java/com/vetlife/api
    ├── modules
    │   ├── auth          # Autenticação e JWT
    │   ├── appointment   # Consultas e Agendamentos
    │   ├── client        # Gestão de Tutores
    │   ├── pet           # Gestão de Pacientes
    │   ├── vet           # Gestão de Veterinários
    │   └── system        # Health Check
    └── shared
        ├── config        # Configurações Globais
        └── exception     # Tratamento de Erros

Essa organização facilita manutenção, escalabilidade e a realização de testes da aplicação.

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos
- Java 21
- Docker e Docker Compose (Obrigatório para o banco e cache)
- (Opcional) Maven — o projeto já inclui **Maven Wrapper**

### 1️⃣ Clonar o repositório
    git clone https://github.com/RafaelSantana-Dev/vetlife-api.git
    cd vetlife-api

### 2️⃣ Subir infraestrutura (PostgreSQL + Redis) com Docker
    docker compose up -d

### 3️⃣ Rodar a aplicação

Linux/macOS:
    ./mvnw clean spring-boot:run

Windows (PowerShell):
    .\mvnw.cmd clean spring-boot:run

🔁 **As migrações do banco são executadas automaticamente via Flyway na inicialização.**

---

## 🔧 Configuração

As configurações ficam em:
`src/main/resources/application.yaml`

**Recomendação profissional:**
- não commitar credenciais reais
- usar variáveis de ambiente e/ou arquivos de configuração locais

**Sugestão de arquivos:**
- `application.yaml` (padrão do projeto)
- `application-local.yaml` (local, adicionar no `.gitignore`)
- `application.example.yaml` (exemplo para quem clonar)

---

## 📚 Documentação da API (Swagger / OpenAPI)

Após iniciar a aplicação, acesse:
**http://localhost:8080/swagger-ui/index.html**

A interface do Swagger permite testar todos os endpoints da API diretamente pelo navegador. 
*Lembre-se de criar uma conta em `/auth/register`, fazer login, copiar o Token e colar no cadeado "Authorize" para ter acesso liberado às rotas.*

---

## 🧭 Boas práticas adotadas

- Separação modular (Package-by-feature).
- DTOs (Data Transfer Objects) para entrada/saída, nunca expondo a Entidade.
- Migrações versionadas com Flyway.
- Tratamento global de erros utilizando a padronização RFC 7807 (ProblemDetail).
- **Soft Delete** (Inativação lógica de registros sem deletar do banco de dados).
- Documentação centralizada no Swagger/OpenAPI.
- Testes unitários com JUnit e Mockito.

---

## 🧪 Testes

Rodar a suíte de testes unitários automatizados:

    .\mvnw.cmd test

---

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para ajudar com o desenvolvimento dos módulos de Loja e Adoção.

1. Faça um fork do repositório
2. Crie uma branch:
    `git checkout -b minha-feature`
3. Faça as alterações e rode os testes:
    `.\mvnw.cmd test`
4. Commit das mudanças (sugestão: Conventional Commits):
    `git commit -m "feat: adicionar endpoint de agendamento"`
5. Envie para o seu fork:
    `git push origin minha-feature`
6. Abra um Pull Request no GitHub apontando para a branch main do repositório original.
7. (Se solicitado) Ajuste o PR e envie novos commits — o PR será atualizado automaticamente.
8. Após o merge, você pode deletar a branch no GitHub para manter o repositório organizado.

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License**.

Isso significa que o código pode ser usado, modificado e distribuído livremente, desde que os créditos ao autor original sejam mantidos.

Consulte o arquivo `LICENSE` para mais detalhes.