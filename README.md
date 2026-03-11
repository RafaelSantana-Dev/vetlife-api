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

> **Nota:** Em breve será adicionado um GIF demonstrando a execução da aplicação e a utilização dos endpoints da API.

<p align="center">
  <img src="docs/demo.gif" alt="Demonstração do VetLife API" width="100%" style="border-radius: 5px; box-shadow: 0px 4px 10px rgba(0,0,0,0.2);" />
</p>

---

## 📌 Visão Geral

O **VetLife API** simula um sistema completo para clínicas veterinárias, permitindo gerenciar:

- 🩺 **Clínica Veterinária**: pacientes, veterinários, consultas e histórico de atendimentos.
- 🐾 **Adoção**: cadastro de animais, processos de adoção e acompanhamento de status.
- 🛍️ **Loja**: produtos, estoque, vendas e atualização automática de estoque.

Toda a especificação de rotas e modelos está documentada via **Swagger/OpenAPI**.

---

## ⚙️ Funcionalidades

### 🐾 Módulo de Adoção (Roadmap)
- Cadastro de animais disponíveis para adoção.
- Listagem e consulta por filtros.
- Registro de processos de adoção.
- Acompanhamento do status das adoções.

### 🩺 Módulo de Clínica Veterinária & Autenticação (MVP)
- **Autenticação:** Login seguro via Token JWT.
- **Cadastro de pacientes:** Vinculado obrigatoriamente a um Tutor.
- **Cadastro de veterinários:** Com validação de CRMV e cache de listagem.
- **Agendamento de consultas:** Validando conflitos de datas.
- **Histórico:** Histórico médico de atendimentos veterinários.

### 🛍️ Módulo de Loja (Roadmap)
- Cadastro de produtos.
- Controle de estoque.
- Registro de vendas.
- Baixa automática do estoque ao vender.

---

## 🚀 Diferenciais Técnicos e Boas Práticas

Este projeto não é apenas um CRUD padrão. Ele implementa desafios reais de engenharia de software corporativa:

*   🔒 **Segurança Stateless:** Autenticação via **JWT (JSON Web Token)** com senhas hasheadas via **BCrypt**.
*   ⚡ **Alta Performance:** Uso de **Redis** para cachear listagens e reduzir drasticamente a carga no banco de dados.
*   🛡️ **Confiabilidade de Dados:** Implementação de **Soft Delete** (`@SQLDelete`). Os dados nunca são apagados fisicamente, apenas inativados.
*   🏗️ **Isolamento de Dados:** Uso de **Java Records** para criação de DTOs imutáveis de entrada e saída.
*   🚨 **Tratamento de Erros:** Uso do padrão global RFC 7807 (`ProblemDetail`) para retornos de erro limpos e padronizados.
*   🗄️ **Migrations:** Evolução do esquema de banco de dados 100% controlada via **Flyway**.
*   🔄 **CI/CD:** Pipeline de Integração Contínua com **GitHub Actions** que roda a suíte de testes automaticamente a cada commit.

---

## 🧪 Tecnologias Utilizadas

- ☕ **Java 21** (Uso de Records)
- 🌱 **Spring Boot 3.2.3**
- 🗄️ **Spring Data JPA**
- 🐘 **PostgreSQL 15** (Banco de dados principal)
- 🔴 **Redis 7** (Cache de alta performance)
- 🐳 **Docker / Docker Compose**
- 📦 **Maven / Maven Wrapper**
- 📑 **Swagger / OpenAPI 3**
- ⚡ **Lombok**
- 🔁 **Flyway** (Migrações de banco de dados)
- 🔒 **Spring Security + JWT (Auth0) + BCrypt**
- 🧪 **JUnit 5 e Mockito**

---

## 🏗️ Arquitetura do Projeto

O projeto segue a arquitetura **Package-by-Feature** (Modular Monolith).

Cada módulo segue boas práticas de arquitetura RESTful, com separação clara entre as responsabilidades: **Controller, Service, Mapper, Repository, DTOs e Entities**.

Estrutura base do projeto:

```text
src/main/java/com/vetlife/api
├── modules
│   ├── auth          # Autenticação e JWT
│   ├── appointment   # Consultas e Agendamentos
│   ├── client        # Gestão de Tutores (Soft Delete)
│   ├── pet           # Gestão de Pacientes
│   ├── vet           # Gestão de Veterinários (Redis Cache)
│   └── system        # Health Check
└── shared
    ├── config        # Configurações Globais (Swagger, Filter)
    └── exception     # Tratamento de Erros Global


```


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
👉 **http://localhost:8080/swagger-ui/index.html**

A interface do Swagger permite testar todos os endpoints da API diretamente pelo navegador. 

**Como acessar as rotas protegidas (Importante):**
Para garantir a segurança da API, todas as requisições exigem autenticação. Siga os passos abaixo para liberar o Swagger:

1.  Crie uma conta usando a rota `POST /api/v1/auth/register`.
2.  Faça o login em `POST /api/v1/auth/login` (usando os mesmos dados).
3.  O servidor retornará um **Token JWT**. Copie apenas o texto do token.
4.  Suba ao topo da página do Swagger, clique no botão 🔓 **Authorize** (cadeado).
5.  Cole o seu Token no campo de valor e clique em *Authorize*.

Pronto! O cadeado será fechado e você terá permissão total para testar os endpoints de Clientes, Pets, Veterinários e Consultas.

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