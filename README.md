# 🐾 VetLife API

![Java CI](https://github.com/RafaelSantana-Dev/vetlife-api/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-brightgreen?style=flat&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Cache-red?style=flat&logo=redis&logoColor=white)
![Security](https://img.shields.io/badge/Spring_Security-JWT-critical?style=flat&logo=springsecurity&logoColor=white)

Sistema backend (API REST) para **gestão de clínicas veterinárias**, desenvolvido com **Java 21** e **Spring Boot 3**, seguindo padrões de arquitetura de software modernos (**Modular Monolith**) e foco total em escalabilidade e segurança.

A aplicação centraliza operações de uma clínica, incluindo **Autenticação**, **Gestão de Tutores**, **Pacientes**, **Veterinários** e **Agendamento de Consultas**.

---

## 🖥️ Demonstração (GIF)

> Coloque seu GIF em \docs/demo.gif\ (ou ajuste o caminho abaixo).

<p align="center">
  <img src="docs/demo.gif" alt="Demonstração do VetLife API" width="100%" style="border-radius: 5px; box-shadow: 0px 4px 10px rgba(0,0,0,0.2);" />
</p>

---

## 🚀 Diferenciais Técnicos (Por que este projeto é relevante?)

Este não é apenas um CRUD. O projeto implementa desafios reais de engenharia de software:

*   🔒 **Segurança Stateless:** Autenticação via **JWT (JSON Web Token)** com criptografia BCrypt.
*   ⚡ **Alta Performance:** Uso de **Redis** para cachear listagens (ex: Veterinários) e reduzir carga no banco.
*   🛡️ **Confiabilidade de Dados:** Implementação de **Soft Delete** (os dados nunca são apagados fisicamente, apenas inativados via \@SQLDelete\).
*   🏗️ **Arquitetura Modular:** Código organizado por *Features* (Domínios) e não por camadas técnicas, facilitando manutenção e microsserviços futuros.
*   🔄 **CI/CD:** Pipeline de Integração Contínua com **GitHub Actions** rodando testes automaticamente.
*   🗄️ **Migrations:** Versionamento de banco de dados com **Flyway**.

---

## ⚙️ Funcionalidades Implementadas

### 🔐 Módulo de Autenticação (Auth)
- Registro de usuários (Sign up).
- Login seguro com retorno de Token Bearer (Sign in).
- Proteção de rotas (apenas usuários logados acessam o sistema).

### 🩺 Módulo de Clínica & Agendamentos
- **Clientes (Tutores):** CRUD completo com Soft Delete.
- **Pacientes (Pets):** Cadastro com vínculo obrigatório ao Tutor.
- **Veterinários:** Cadastro com validação de CRMV e Cache de listagem.
- **Consultas:** Agendamento validado (não permite datas passadas) vinculando Pet + Vet.

---

## 🧪 Tecnologias Utilizadas

- ☕ **Java 21**
- 🌱 **Spring Boot 3.2.3**
- 🗄️ **Spring Data JPA**
- 🐘 **PostgreSQL** (Banco de dados principal)
- 🔴 **Redis** (Cache distribuído)
- 🐳 **Docker / Docker Compose**
- 📦 **Maven**
- 📑 **Swagger / OpenAPI 3**
- ⚡ **Lombok**
- 🔁 **Flyway** (Migrações de banco)
- 🧪 **JUnit 5, Mockito & GitHub Actions**

---

## 🏗️ Arquitetura do Projeto

O projeto segue a arquitetura **Package-by-Feature** (Modular):

\\\	ext
src/main/java/com/vetlife/api
├── modules
│   ├── auth          # Login, Registro e Token Service
│   ├── appointment   # Agendamentos (Consultas)
│   ├── client        # Gestão de Tutores (Soft Delete ativo)
│   ├── pet           # Gestão de Pacientes
│   ├── vet           # Gestão de Veterinários (Cache ativo)
│   └── system        # Health Check
└── shared
    ├── config        # Configurações Globais (Swagger, Security, Redis)
    └── exception     # Tratamento Global de Erros (RFC 7807)
\\\

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos
- **Docker Desktop** instalado e rodando (Obrigatório).
- **Git** instalado.

### 1️⃣ Clonar o repositório
\\\ash
git clone https://github.com/RafaelSantana-Dev/vetlife-api.git
cd vetlife-api
\\\

### 2️⃣ Subir a Infraestrutura (Postgres + Redis)
Não é necessário instalar banco de dados na sua máquina. O Docker faz tudo:
\\\ash
docker compose up -d 
\\\

### 3️⃣ Rodar a aplicação
**Linux/macOS:**
\\\ash
./mvnw clean spring-boot:run
\\\

**Windows (PowerShell):**
\\\powershell
.\mvnw.cmd clean spring-boot:run
\\\

---

## 📚 Documentação da API (Swagger)

A API é 100% documentada e testável via navegador.

1.  Acesse: **http://localhost:8080/swagger-ui/index.html**
2.  **Autentique-se:**
    *   Crie uma conta em \uth-controller\ -> \/register\.
    *   Faça login em \uth-controller\ -> \/login\.
    *   Copie o **Token**.
    *   Clique no cadeado 🔓 **Authorize** (topo da página) e cole o token.
3.  Pronto! Você pode testar todos os endpoints.

---

## 🧪 Testes

Para rodar a suíte de testes unitários e garantir a integridade das regras de negócio:

\\\ash
.\mvnw.cmd test
\\\

---

## 🤝 Contribuições

Contribuições são bem-vindas!

1.  Faça um fork do repositório.
2.  Crie uma branch: \git checkout -b feature/minha-melhoria\.
3.  Faça as alterações e commite: \git commit -m "feat: minha melhoria"\.
4.  Envie para o fork: \git push origin feature/minha-melhoria\.
5.  Abra um Pull Request.

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License**. Consulte o arquivo LICENSE para mais detalhes.