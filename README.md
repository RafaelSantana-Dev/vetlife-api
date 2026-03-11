# 🐾 VetLife API

![Java CI](https://github.com/RafaelSantana-Dev/vetlife-api/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-green)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)
![License](https://img.shields.io/badge/License-MIT-purple)

**VetLife API** é um sistema backend robusto e escalável para gestão de clínicas veterinárias, desenvolvido com **arquitetura de referência** focada em qualidade de código, segurança e performance.

O projeto vai além do CRUD básico, implementando padrões de mercado como **Modular Monolith**, **Cache Distribuído**, **Soft Delete** e **Segurança Stateless**.

---

## 🚀 Tecnologias & Arquitetura

O sistema foi projetado para simular um ambiente de produção real de uma Big Tech.

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.2.3
- **Banco de Dados:** PostgreSQL 15 (Dockerizado)
- **Cache:** Redis 7 (Dockerizado)
- **Migrations:** Flyway (Versionamento de banco)
- **Segurança:** Spring Security 6 + JWT (JSON Web Token) + BCrypt
- **Documentação:** Swagger UI (OpenAPI 3)
- **Testes:** JUnit 5 + Mockito
- **CI/CD:** GitHub Actions

### 🏗️ Estrutura do Projeto (Package-by-Feature)
Diferente da arquitetura em camadas tradicional, este projeto organiza o código por **Domínios de Negócio**, facilitando manutenção e futura extração para microsserviços.

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
    ├── config        # Configurações (Swagger, Security, Redis)
    └── exception     # Tratamento Global de Erros (RFC 7807)
\\\

---

## ✨ Diferenciais Técnicos (Destaques)

### 🔒 Segurança Nível Bancário
- Autenticação via **Token JWT**.
- Senhas criptografadas no banco.
- Proteção contra ataques CSRF e rotas bloqueadas por padrão.

### ⚡ Performance com Redis
- A listagem de veterinários utiliza **Cache com Redis**.
- A primeira requisição busca no Postgres, as seguintes retornam em milissegundos direto da memória RAM.
- Cache é invalidado automaticamente (@CacheEvict) ao cadastrar novos dados.

### 🛡️ Confiabilidade de Dados
- **Soft Delete:** Clientes nunca são apagados fisicamente (DELETE). O sistema usa @SQLDelete para apenas inativá-los, mantendo histórico.
- **Transações:** Uso rigoroso de @Transactional.
- **Validações:** Regras de negócio fortes (ex: não permite e-mail duplicado, não permite consulta no passado).

---

## 📦 Como Rodar o Projeto

### Pré-requisitos
- Docker Desktop instalado e rodando.
- Java 21 (Opcional, pois usamos Maven Wrapper).

### 1️⃣ Clonar e Subir Infraestrutura
\\\ash
git clone https://github.com/RafaelSantana-Dev/vetlife-api.git
cd vetlife-api

# Subir Banco de Dados (Postgres) e Cache (Redis)
docker compose up -d
\\\

### 2️⃣ Rodar a Aplicação
\\\ash
# Linux / Mac
./mvnw clean spring-boot:run

# Windows (PowerShell)
.\mvnw.cmd clean spring-boot:run
\\\

A aplicação estará rodando em: \http://localhost:8080\

---

## 📚 Documentação (Swagger)

A API possui documentação interativa completa.

1.  Acesse: **http://localhost:8080/swagger-ui/index.html**
2.  Crie uma conta em \uth-controller\ -> \/register\.
3.  Faça login em \uth-controller\ -> \/login\.
4.  Copie o **token** gerado.
5.  Clique no botão 🔓 **Authorize** no topo da página e cole o token.

Pronto! Você tem acesso total aos endpoints de Clientes, Pets, Veterinários e Consultas.

---

## 🧪 Testes e Qualidade

O projeto possui pipeline de **CI (Integração Contínua)** configurado com **GitHub Actions**.
A cada *push* ou *pull request*, o sistema automaticamente:
1.  Sobe uma máquina virtual Ubuntu.
2.  Instala o Java 21.
3.  Compila o projeto.
4.  Executa a bateria de **Testes Unitários**.

Para rodar os testes localmente:
\\\ash
.\mvnw.cmd test
\\\

---

## 🤝 Contribuições

Este é um projeto de portfólio open-source. Sinta-se à vontade para sugerir melhorias.

1.  Faça um Fork.
2.  Crie uma branch (\git checkout -b feature/nova-feature\).
3.  Commit suas mudanças (\git commit -m 'feat: Adiciona nova feature'\).
4.  Push para a branch (\git push origin feature/nova-feature\).
5.  Abra um Pull Request.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.