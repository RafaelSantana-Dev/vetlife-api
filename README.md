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

<p align="center">
  <img src="docs/demo.gif" alt="Demonstração do VetLife API" width="100%" style="border-radius: 5px; box-shadow: 0px 4px 10px rgba(0,0,0,0.2);" />
</p>

### 🔍 O que está acontecendo na demonstração?

O GIF acima ilustra a forte integração entre os módulos do sistema, provando como o ERP automatiza o trabalho da clínica na prática:

- **1. Dashboard Financeiro Inicial:** Começamos consumindo o endpoint `GET /api/v1/painel`, que nos retorna o resumo financeiro atual da clínica. Como esperado, o `faturamento_total` inicia em `R$ 0,00`.
- **2. Agendamento de Consulta:** Acessamos o endpoint `POST /api/v1/compromissos` e realizamos o agendamento de uma consulta de rotina, vinculando os IDs do Pet e do Veterinário. O sistema processa a regra de negócio e retorna sucesso (`201 Created`).
- **3. Integração Automática de Caixa:** Retornamos ao painel de controle financeiro e executamos a mesma requisição inicial. O `faturamento_total` agora é de `R$ 150,00`.

💡 **Como isso realmente funciona:**  
No exato momento em que a consulta é confirmada, o módulo de Atendimento publica um evento de domínio (`ConsultaAgendadaEvent`). O módulo Financeiro, que está escutando esse evento, cria imediatamente o lançamento de receita no fluxo de caixa — tudo de forma síncrona ou com transação compensatória, garantindo consistência total dos dados e **zero necessidade de intervenção manual**.

É essa comunicação automática entre módulos que transforma o VetLife em um ERP verdadeiramente integrado, eliminando retrabalho e erros comuns em sistemas fragmentados.

---

## ⚙️ Funcionalidades & Integrações (O Núcleo do Negócio)

### 🏥 Módulo de Clínica & Consultas
* **Gestão de Tutores (Clientes):** CRUD completo com técnica de Soft Delete (inativação lógica).
* **Gestão de Pacientes (Pets):** Vinculados obrigatoriamente aos seus tutores.
* **Corpo Clínico (Veterinários):** Validação de CRMV e consultas otimizadas via `Redis Cache`.
* **Agendamento Inteligente e Integração:** Ao agendar uma consulta, o sistema valida os dados e gera automaticamente uma receita (entrada) no Módulo Financeiro.

### 🛍️ Módulo de Loja (E-commerce)
* Cadastro de produtos e controle real de estoque.
* **Processamento de Vendas:** Valida se há estoque suficiente, realiza a baixa automática do produto e registra o faturamento diretamente no Módulo Financeiro.

### 🐾 Módulo de Adoção
* Plataforma integrada para cadastro de animais disponíveis e controle de status de adoção.

### 💰 Módulo Financeiro e Dashboard
* Registro centralizado de todo o fluxo de caixa da clínica.
* Endpoint de Dashboard resumindo o total de clientes, pets e o faturamento global em tempo real.

### 🔐 Módulo de Autenticação
* Login e registro seguros. Acesso aos módulos restritos por token `JWT`.

---

## 🚀 Diferenciais Técnicos e Boas Práticas

Este projeto não é apenas um CRUD padrão. Ele implementa desafios reais de engenharia de software corporativo:

* 🔒 **Segurança Stateless:** Autenticação via `JWT` (Auth0) com senhas cacheadas/hasheadas via `BCrypt`.
* ⚡ **Alta Performance:** Uso de `Redis` para armazenar listas (como a de Veterinários) e reduzir drasticamente a carga no banco de dados.
* 🛡️ **Confiabilidade de Dados:** Implementação de Soft Delete (`@SQLDelete`).
* 🏗️ **Isolamento de Dados:** Uso de `Records` do Java para DTOs imutáveis de entrada e saída.
* 🚨 **Tratamento de Erros:** Padrão global RFC 7807 (`ProblemDetail`).
* 🗄️ **Migrações:** Evolução do esquema de banco de dados 100% controlado via `Flyway`.
* 🔄 **CI/CD:** Pipeline de Integração Contínua com GitHub Actions que roda a suíte de testes (`JUnit`/`Mockito`) automaticamente a cada `commit`.

---

## 🧪 Tecnologias Utilizadas

* ☕ **Java 21** (Uso de Records)
* 🌱 **Spring Boot 3.2.3**
* 🗄️ **Spring Data JPA**
* 🐘 **PostgreSQL 15** (Banco de dados principal)
* 🔴 **Redis 7** (Cache de alta performance)
* 🐳 **Docker / Docker Compose**
* 📦 **Maven** (com Maven Wrapper)
* 📑 **Swagger / OpenAPI 3**
* ⚡ **Lombok**
* 🔁 **Flyway** (Migrations)
* 🔒 **Spring Security + JWT (Auth0) + BCrypt**
* 🧪 **JUnit 5 e Mockito**

---

## 🏗️ Arquitetura do Projeto

O projeto segue a arquitetura **Package-by-Feature** (Modular Monolith). Cada módulo segue o fluxo: `Controller` → `Service` → `Mapper` → `Repository` → `Entity`.

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
    ├── config        # Configurações Globais (Swagger, Security Filter)
    └── exception     # Tratamento de Erros Global (RFC 7807) 

```


Essa organização facilita a manutenção, a escalabilidade e a realização de testes da aplicação.

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos
- **Java 21**
- **Docker** e **Docker Compose** (Obrigatório para o banco de dados e cache)
- **Git** instalado
- (Opcional) Maven — o projeto já inclui o **Maven Wrapper** (`mvnw`)

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/RafaelSantana-Dev/vetlife-api.git
cd vetlife-api
```

### 2️⃣ Subir a infraestrutura (PostgreSQL + Redis)
```bash
docker compose up -d
```

### 3️⃣ Rodar a aplicação

**Linux / macOS:**
```bash
./mvnw clean spring-boot:run
```

**Windows (PowerShell):**
```powershell
.\mvnw.cmd clean spring-boot:run
```

> 🔁 **Atenção:** As migrações do banco de dados são executadas automaticamente via `Flyway` na inicialização do sistema.

---

## 🔧 Configuração

As configurações principais da aplicação ficam em:  
📁 `src/main/resources/application.yaml`

💡 **Recomendação profissional:**
Nunca faça o `commit` de credenciais reais (senhas de banco, secret do JWT, etc). Utilize variáveis de ambiente ou arquivos de configuração locais.

**Sugestão de estrutura para o seu ambiente local:**
- `application.yaml` (Configuração padrão da aplicação)
- `application-local.yaml` (Suas configurações locais — adicione este arquivo no `.gitignore`)
- `application.example.yaml` (Um template seguro para quem clonar o projeto)

---

## 📚 Documentação da API (Swagger / OpenAPI)

Após iniciar a aplicação, acesse a documentação interativa em:  
👉 **http://localhost:8080/swagger-ui/index.html**

A interface do Swagger permite testar todos os endpoints da API diretamente pelo navegador. 

**Como acessar as rotas protegidas (Importante):**  
Para garantir a segurança, quase todas as requisições exigem autenticação. Siga os passos abaixo para liberar o Swagger:

1. Crie uma conta na rota `POST /api/v1/auth/register`.
2. Faça o login em `POST /api/v1/auth/login` (usando os mesmos dados).
3. O servidor retornará um **Token JWT**. Copie apenas o texto do token.
4. Suba ao topo da página do Swagger e clique no botão 🔓 **Authorize** (ícone de cadeado).
5. Cole o seu token no campo *Value* e clique em **Authorize**.

Pronto! O cadeado será fechado e você terá permissão total para testar os endpoints de Clientes, Pets, Veterinários e Consultas.

---

## 🧭 Boas Práticas Adotadas

- 📦 **Separação Modular:** Arquitetura *Package-by-feature* (Modular Monolith).
- 🛡️ **DTOs (Data Transfer Objects):** Entrada e saída de dados seguras, nunca expondo as Entidades do banco.
- 🗄️ **Migrations:** Controle de versão do banco de dados com `Flyway`.
- 🚨 **Tratamento de Erros:** Respostas padronizadas globalmente utilizando a RFC 7807 (`ProblemDetail`).
- 🗑️ **Soft Delete:** Inativação lógica de registros (ex: Tutores) sem deletar os dados do banco.
- 📑 **Documentação:** Centralizada e interativa com Swagger/OpenAPI.
- 🧪 **Qualidade:** Testes unitários com `JUnit 5` e `Mockito`.

---

## 🧪 Testes

Para rodar a suíte de testes unitários automatizados:

**Linux / macOS:**
```bash
./mvnw test
```

**Windows:**
```powershell
.\mvnw.cmd test
```

---

## 🤝 Como Contribuir

Contribuições são muito bem-vindas! Sinta-se à vontade para ajudar, especialmente com o desenvolvimento dos módulos de Loja e Adoção.

1. Faça um `fork` deste repositório.
2. Crie uma nova `branch` para a sua alteração:
   ```bash
   git checkout -b minha-feature
   ```
3. Faça as alterações necessárias e garanta que os testes continuam passando (`./mvnw test`).
4. Faça o `commit` das suas mudanças (sugestão: utilize o padrão *Conventional Commits*):
   ```bash
   git commit -m "feat: adicionar endpoint de agendamento"
   ```
5. Envie o código para o seu `fork`:
   ```bash
   git push origin minha-feature
   ```
6. Abra um **Pull Request (PR)** no GitHub apontando para a branch `main` deste repositório original.
7. *Caso seja solicitado algum ajuste na revisão, basta fazer os novos commits na sua branch local e dar `push` novamente. O PR será atualizado automaticamente.*
8. Após o `merge`, você pode deletar a sua branch no GitHub para manter seu repositório organizado.

---

## 📄 Licença

Este projeto está licenciado sob a licença **MIT**.

Isso significa que o código pode ser usado, modificado e distribuído livremente para fins comerciais ou privados, desde que os créditos ao autor original sejam mantidos.

Consulte o arquivo `LICENSE` no repositório para mais detalhes.
