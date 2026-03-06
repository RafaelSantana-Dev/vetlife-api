# 🐾 VetLife API

Sistema backend (API REST) para **gestão de clínicas veterinárias**, desenvolvido com **Java 21** e **Spring Boot**, com arquitetura RESTful e foco em **boas práticas**, **organização em camadas**, **testes** e **migrações de banco**.

A aplicação centraliza operações de uma clínica em três módulos: **🐾 Adoção**, **🩺 Clínica** e **🛍️ Loja**.

---

## 🖥️ Demonstração (GIF)

> Coloque seu GIF em `docs/demo.gif` (ou ajuste o caminho abaixo).

<p align="center">
  <img src="docs/demo.gif" alt="Demonstração do VetLife API" width="900" />
</p>

---

## 📌 Visão Geral

O **VetLife API** simula um sistema completo para clínicas veterinárias, permitindo gerenciar:

- 🩺 **Clínica Veterinária**: pacientes, veterinários, consultas e histórico de atendimentos  
- 🐾 **Adoção**: cadastro de animais, processos de adoção e acompanhamento de status  
- 🛍️ **Loja**: produtos, estoque, vendas e atualização automática de estoque  

Toda a especificação de rotas e modelos está documentada via **Swagger/OpenAPI**.

---

## ⚙️ Funcionalidades

### 🐾 Módulo de Adoção
- Cadastro de animais disponíveis para adoção  
- Listagem e consulta por filtros  
- Registro de processos de adoção  
- Acompanhamento do status das adoções  

### 🩺 Módulo de Clínica Veterinária
- Cadastro de pacientes  
- Cadastro de veterinários  
- Agendamento de consultas  
- Histórico de atendimentos veterinários  

### 🛍️ Módulo de Loja
- Cadastro de produtos  
- Controle de estoque  
- Registro de vendas  
- Baixa automática do estoque ao vender  

---

## 🧪 Tecnologias Utilizadas

- ☕ **Java 21**
- 🌱 **Spring Boot**
- 🗄️ **Spring Data JPA**
- 🐬 **MySQL**
- 🐳 **Docker / Docker Compose**
- 📦 **Maven / Maven Wrapper**
- 📑 **Swagger / OpenAPI**
- ⚡ **Lombok**
- 🔁 **Flyway** (migrações de banco)
- 🧪 **JUnit 5** e **Mockito**

---

## 🏗️ Arquitetura do Projeto

Padrão em camadas:

**Controller → Service → Repository → Database**

Estrutura base (pode variar conforme evolução)

src/main/java/com/vetlife/api
├── controller
├── service
├── repository
├── entity (ou model)
├── dto
└── config


Essa organização facilita manutenção, escalabilidade e testes.

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos
- Java 21
- Docker e Docker Compose
- (Opcional) Maven — o projeto já inclui **Maven Wrapper**

---

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/RafaelSantana-Dev/vetlife-api.git
cd vetlife-api
```

### 2️⃣ Subir infraestrutura (MySQL) com Docker

```bash
docker compose up -d 
```

### 3️⃣ Rodar a aplicação

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
mvnw.cmd spring-boot:run
```

### 🔁 As migrações do banco são executadas automaticamente via Flyway na inicialização.

🔧 Configuração
As configurações ficam em:

src/main/resources/application.yaml
Recomendação profissional:

não commitar credenciais reais
usar variáveis de ambiente e/ou arquivos de configuração locais
Sugestão de arquivos:

application.yaml (padrão do projeto)
application-local.yaml (local, no .gitignore)
application.example.yaml (exemplo para quem clonar)

📚 Documentação da API (Swagger / OpenAPI)

Após iniciar a aplicação, acesse:

Swagger UI: http://localhost:8080/swagger-ui.html
(dependendo da versão: http://localhost:8080/swagger-ui/index.html)
OpenAPI JSON: http://localhost:8080/v3/api-docs

A interface do Swagger permite testar os endpoints diretamente pelo navegador.

### 🧪 Testes

Rodar testes:
./mvnw test

### 🧭 Boas práticas adotadas
Separação por camadas (Controller/Service/Repository)
DTOs para entrada/saída quando necessário
Migrações versionadas com Flyway
Documentação centralizada em Swagger/OpenAPI
Testes unitários com JUnit e Mockito

### 🤝 Contribuições
Contribuições são bem-vindas!

1. Faça um fork do repositório  

2. Crie uma branch:

```shell
git checkout -b minha-feature
```

3. Faça as alterações e rode os testes:

```shell
./mvnw test
```

4. Commit das mudanças (sugestão: Conventional Commits):

```shell
git commit -m "feat: adicionar endpoint de agendamento"
```

5.Envie para o seu fork:

```shell
git push origin minha-feature
```

6. Abra um Pull Request no GitHub apontando para a branch main do repositório original

7. (Se solicitado) Ajuste o PR e envie novos commits — o PR será atualizado automaticamente

8. Após o merge, você pode deletar a branch no GitHub para manter o repositório organizado

📄 Licença
Este projeto está licenciado sob a MIT License.
Consulte o arquivo LICENSE para mais detalhes.
