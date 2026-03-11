# 🐾 VetLife API (ERP Veterinário)

![Java CI](https://github.com/RafaelSantana-Dev/vetlife-api/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-brightgreen?style=flat&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Cache-red?style=flat&logo=redis&logoColor=white)
![Security](https://img.shields.io/badge/Spring_Security-JWT-critical?style=flat&logo=springsecurity&logoColor=white)

Sistema backend (API REST) atuando como um **ERP completo** para gestão de clínicas veterinárias, desenvolvido com **Java 21** e **Spring Boot 3**. O sistema segue o padrão de arquitetura **Modular Monolith** e foca em alta performance, segurança e regras de negócio interligadas.

A aplicação automatiza e centraliza todas as operações de uma clínica: **Atendimento, Adoção, E-commerce (Loja) e Fluxo de Caixa (Financeiro)**.

---

## 🖥️ Demonstração (GIF)

> **Nota:** Em breve será adicionado um GIF demonstrando a execução da aplicação e a utilização dos endpoints da API.

<p align="center">
  <img src="docs/demo.gif" alt="Demonstração do VetLife API" width="100%" style="border-radius: 5px; box-shadow: 0px 4px 10px rgba(0,0,0,0.2);" />
</p>

---

## ⚙️ Funcionalidades & Integrações (O Core do Negócio)

### 🏥 Módulo de Clínica & Consultas
- **Gestão de Tutores (Clientes):** CRUD completo com técnica de Soft Delete (inativação lógica).
- **Gestão de Pacientes (Pets):** Vinculados obrigatoriamente aos seus tutores.
- **Corpo Clínico (Veterinários):** Validação de CRMV e consultas otimizadas via **Redis Cache**.
- **Agendamento Inteligente:** Validação de datas. **Integração Automática:** Ao agendar uma consulta, o sistema gera automaticamente uma receita (entrada) no Módulo Financeiro.

### 🛍️ Módulo de Loja (E-commerce)
- Cadastro de produtos e controle de estoque real.
- **Processamento de Vendas:** Valida se há estoque suficiente, realiza a baixa automática do produto e **registra o faturamento diretamente no Módulo Financeiro**.

### 🐾 Módulo de Adoção
- Plataforma integrada para cadastro de animais disponíveis e controle de status de adoção.

### 💰 Módulo Financeiro & Dashboard
- Registro centralizado de todo o fluxo de caixa da clínica.
- Endpoint de Dashboard sumarizando o total de clientes, pets e o faturamento global em tempo real.

### 🔐 Módulo de Autenticação
- Login e registro blindados. Acesso aos módulos restrito por **Token JWT**.

---

## 🚀 Diferenciais Técnicos e Boas Práticas

Este projeto não é apenas um CRUD padrão. Ele implementa desafios reais de engenharia de software corporativa:

*   🔒 **Segurança Stateless:** Autenticação via **JWT (Auth0)** com senhas hasheadas via **BCrypt**.
*   ⚡ **Alta Performance:** Uso de **Redis** para cachear listagens e reduzir drasticamente a carga no banco de dados.
*   🛡️ **Confiabilidade de Dados:** Implementação de **Soft Delete** (`@SQLDelete`).
*   🏗️ **Isolamento de Dados:** Uso de **Java Records** para DTOs imutáveis de entrada e saída.
*   🚨 **Tratamento de Erros:** Padrão global RFC 7807 (`ProblemDetail`).
*   🗄️ **Migrations:** Evolução do esquema de banco de dados 100% controlada via **Flyway** (Tabelas criadas via scripts SQL).
*   🔄 **CI/CD:** Pipeline de Integração Contínua com **GitHub Actions** que roda a suíte de testes (JUnit/Mockito) automaticamente a cada commit.

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

O projeto segue a arquitetura **Package-by-Feature** (Modular Monolith). Cada módulo segue o padrão: *Controller → Service → Mapper → Repository → Entity*.

Estrutura base do projeto:

```text
src/main/java/com/vetlife/api
├── modules
│   ├── adoption      # Gestão de adoção de pets
│   ├── appointment   # Consultas e Agendamentos
│   ├── auth          # Autenticação e JWT
│   ├── client        # Gestão de Tutores (Soft Delete)
│   ├── finance       # Lançamentos e fluxo de caixa automático
│   ├── pet           # Gestão de Pacientes
│   ├── store         # Produtos e baixa de estoque
│   ├── system        # Dashboard (Estatísticas) e Health Check
│   └── vet           # Gestão de Veterinários (Redis Cache)
└── shared
    ├── config        # Configurações Globais (Swagger, Filter)
    └── exception     # Tratamento de Erros Global (RFC 7807)


```


Essa organização facilita manutenção, escalabilidade e a realização de testes da aplicação.

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos
- Java 21
- Docker e Docker Compose (Obrigatório para o banco e cache)
- Git instalado
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