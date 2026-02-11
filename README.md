# 💰 Sistema de Transações Financeiras - Itaú

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-green?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

_Sistema completo de gerenciamento de transações financeiras com análise estatística em tempo real_

[Características](#-características) •
[Tecnologias](#-tecnologias) •
[Instalação](#-instalação) •
[Endpoints](#-endpoints-api) •
[Documentação](#-documentação)

</div>

---

## 📋 Sobre o Projeto

Sistema REST API desenvolvido para gerenciar transações financeiras com suporte a:

- ✅ **Autenticação JWT** - Sistema completo de login e autorização
- ✅ **Gerenciamento de Usuários** - Criação e autenticação de usuários
- ✅ Criação e consulta de transações (protegido por autenticação)
- ✅ Cálculo de estatísticas em tempo real
- ✅ **Rastreamento de Usuários** - Transações associadas ao usuário autenticado
- ✅ Rate limiting configurável
- ✅ Validação de valores mínimos e máximos
- ✅ Tratamento global de exceções
- ✅ Persistência em PostgreSQL

---

## ✨ Características

### 🔐 Autenticação e Segurança

- **JWT Authentication**: Sistema completo de autenticação com JSON Web Tokens
- **Spring Security**: Proteção de endpoints e gerenciamento de sessões stateless
- **Roles e Permissões**: Sistema de roles (USER, ADMIN) configurável
- **Password Encryption**: Senhas criptografadas com BCrypt
- **SecurityFilter**: Validação automática de tokens em todas as requisições
- **Rate Limiting**: Limite configurável de transações por minuto

### 🔐 Validações e Segurança

- **Validação de Content-Type**: Aceita apenas `application/json` (retorna 415 se incorreto)
- **Validação de Valores**: Valores mínimo e máximo configuráveis
- **Tratamento de Erros**: GlobalExceptionHandler com respostas padronizadas
- **Bean Validation**: Validações automáticas nos DTOs
- **Mensagens de Erro**: Respostas em português com código HTTP apropriado

### 📊 Estatísticas

- Cálculo automático de estatísticas das transações
- Métricas disponíveis: count, avg, max, min, sum
- Histórico de snapshots das estatísticas
- Relacionamento ManyToMany com transações

### 🏗️ Arquitetura

- **Clean Architecture**: Separação clara de responsabilidades
- **Design Patterns**: DTO, Mapper, Repository
- **Transações**: Gerenciamento transacional com Spring
- **OpenAPI**: Documentação Swagger automática

---

## 🛠️ Tecnologias

### Backend

- **Java 17**
- **Spring Boot 4.0.2**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Security
  - Spring Validation
  - Spring Actuator
- **Auth0 JWT** - JSON Web Token
- **Lombok** - Redução de boilerplate

### Banco de Dados

- **PostgreSQL** (via Docker)
- **Hibernate/JPA** - ORM

### Documentação

- **SpringDoc OpenAPI 2.6.0**
- **Swagger UI**

### DevOps

- **Docker Compose**
- **Maven Wrapper**

---

## 🚀 Instalação

### Pré-requisitos

- Java 17+
- Docker & Docker Compose
- Maven (ou usar o wrapper incluído)

### Passo a Passo

1️⃣ **Clone o repositório**

```bash
git clone <repository-url>
cd itau
```

2️⃣ **Inicie o banco de dados**

```bash
docker compose up -d
```

> 💡 **VS Code**: Use a task `Docker Compose Up` via `Ctrl+Shift+P` → "Tasks: Run Task"

3️⃣ **Execute a aplicação**

**Opção A: Com Maven Wrapper**

```bash
./mvnw spring-boot:run
```

**Opção B: Com Build**

```bash
./mvnw clean package
java -jar target/itau-0.0.1-SNAPSHOT.jar
```

**Opção C: VS Code Task**

Use a task `Run Application` ou `Start All (Docker + App)` para iniciar tudo de uma vez.

4️⃣ **Acesse a aplicação**

- API: <http://localhost:8080>
- Swagger UI: <http://localhost:8080/swagger-ui.html>
- Actuator: <http://localhost:8080/actuator>

---

## 🔌 Endpoints API

> **⚠️ IMPORTANTE**:
>
> - Todas as requisições devem usar `Content-Type: application/json`
> - Endpoints protegidos requerem o header `Authorization: Bearer <token>`
> - Obtenha o token através de `/auth` ou `/auth/create_user`

### � Autenticação

#### Login

```http
POST /auth
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Resposta:** `200 OK`

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Criar Novo Usuário

```http
POST /auth/create_user
Content-Type: application/json

{
  "username": "novo_usuario",
  "password": "senha123",
  "role": "U"
}
```

**Resposta:** `200 OK`

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Roles disponíveis:**

- `U` ou `ROLE_USER` - Usuário padrão
- `A` ou `ROLE_ADMIN` - Administrador

> 💡 **Nota**: O campo `role` é opcional. Se não especificado, será usado `ROLE_USER` por padrão.

---

### 📝 Transações

> ⚠️ **Todos os endpoints de transações requerem autenticação via Bearer Token**

#### Criar Transação

```http
POST /transacao
Authorization: Bearer {seu_token_jwt}
Content-Type: application/json

{
  "valor": 4500.00
}
```

**Resposta:** `201 Created`

```json
{
  "id": 1,
  "valor": 4500.0,
  "dataHora": "05/02/2026 10:30",
  "usuario": "admin"
}
```

#### Listar Todas as Transações

```http
GET /transacao
Authorization: Bearer {seu_token_jwt}
```

**Resposta:** `200 OK`

```json
[
  {
    "id": 1,
    "valor": 4500.0,
    "dataHora": "05/02/2026 10:30",
    "usuario": "admin"
  },
  {
    "id": 2,
    "valor": 3200.5,
    "dataHora": "05/02/2026 10:31",
    "usuario": "user1"
  }
]
```

#### Buscar Transação por ID

```http
GET /transacao/{id}
Authorization: Bearer {seu_token_jwt}
```

**Resposta:** `200 OK`

```json
{
  "id": 1,
  "valor": 4500.0,
  "dataHora": "05/02/2026 10:30",
  "usuario": "admin"
}
```

### 📊 Estatísticas

> ⚠️ **Requer autenticação via Bearer Token**

#### Obter Estatísticas

```http
GET /estatistica
Authorization: Bearer {seu_token_jwt}
```

**Resposta:** `200 OK`

```json
{
  "count": 10,
  "avg": 3500.5,
  "max": 8000.0,
  "min": 1000.0,
  "sum": 35005.0,
  "transacoes": [
    {
      "id": 1,
      "valor": 4500.0,
      "dataHora": "05/02/2026 10:30",
      "usuario": "admin"
    }
  ]
}
```

---

## ⚙️ Configuração

Edite `src/main/resources/application.yml`:

```yaml
# Configurações de Segurança JWT
api:
  security:
    token:
      secret: ${JWT_SECRET:my-secret-key} # Use variável de ambiente em produção

# Estatísticas
estatistica:
  intervaloEmSegundos: 60 # Janela de tempo para estatísticas

# Transações
transacoes:
  limitePorMinuto: 2 # Rate limit
  valorMaximoPorTransacao: 1000000 # Valor máximo permitido
  valorMinimoPorTransacao: 0.01 # Valor mínimo permitido
```

### Variáveis de Ambiente

Para produção, configure a secret JWT via variável de ambiente:

```bash
export JWT_SECRET="sua-chave-secreta-muito-segura-aqui"
```

---

## 🎯 Tratamento de Erros

A API retorna respostas padronizadas para erros:

### Exemplo: Validação

```json
{
  "timestamp": "2026-02-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "valor: O valor da transacao deve ser maior que zero",
  "path": "/transacao"
}
```

### Exemplo: Autenticação

```json
{
  "timestamp": "2026-02-03T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token inválido ou expirado",
  "path": "/transacao"
}
```

### Códigos de Status

| Código | Descrição                               |
| ------ | --------------------------------------- |
| `200`  | OK - Requisição bem-sucedida            |
| `201`  | Created - Recurso criado                |
| `400`  | Bad Request - Erro de validação         |
| `401`  | Unauthorized - Não autenticado          |
| `403`  | Forbidden - Sem permissão               |
| `404`  | Not Found - Recurso não encontrado      |
| `429`  | Too Many Requests - Rate limit excedido |
| `500`  | Internal Server Error - Erro interno    |

---

## 📚 Documentação

### Swagger UI

Acesse a documentação interativa em:

```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON

```
http://localhost:8080/v3/api-docs
```

---

## 🏛️ Arquitetura do Projeto

```
src/main/java/com/itau/itau/
├── config/              # Configurações (OpenAPI, Security, Filters)
│   ├── SecurityConfig.java
│   ├── SecurityFilter.java
│   └── OpenApiConfiguration.java
├── controller/          # Controllers REST
│   ├── AuthController.java
│   ├── TransacoesController.java
│   └── EstatisticaController.java
├── dto/                 # Data Transfer Objects
│   ├── request/
│   └── response/
│       └── LoginResponse.java
├── enums/               # Enumerações
│   └── RoleEnum.java
├── exception/           # Exceptions customizadas e handlers
├── mapper/              # Conversores DTO ↔ Model
├── model/               # Entidades JPA
│   ├── UserModel.java
│   ├── TransacaoModel.java
│   └── EstatisticaModel.java
├── properties/          # Configuration Properties
├── repository/          # Repositórios JPA
│   ├── UserRepository.java
│   ├── TransacaoRepository.java
│   └── EstatisticaRepository.java
└── service/             # Lógica de negócio
    ├── TokenService.java
    ├── UserService.java
    ├── TransacaoService.java
    └── EstatisticaService.java
```

---

## 🧪 Testes

```bash
# Executar testes
./mvnw test

# Executar com cobertura
./mvnw clean verify
```

---

## 🐳 Docker

### Banco de Dados

```bash
# Iniciar
docker compose up -d

# Parar
docker compose down

# Ver logs
docker logs itau-postgres
```

### Conectar ao PostgreSQL

```bash
docker exec -it itau-postgres psql -U postgres -d postgres
```

---

## � VS Code Tasks

O projeto inclui tasks pré-configuradas para VS Code em `.vscode/tasks.json`:

| Task                         | Descrição                                        |
| ---------------------------- | ------------------------------------------------ |
| **Run Application**          | Executa a aplicação via `./mvnw spring-boot:run` |
| **Docker Compose Up**        | Inicia os containers em background               |
| **Docker Compose Down**      | Para e remove os containers                      |
| **Docker Compose Logs**      | Mostra os logs dos containers                    |
| **Start All (Docker + App)** | Inicia Docker Compose e depois a aplicação       |

### Como usar

1. Abra o Command Palette: `Ctrl+Shift+P`
2. Digite: `Tasks: Run Task`
3. Selecione a task desejada

---

## 📮 Postman Collection

O projeto inclui uma coleção Postman pronta para importar:

**Arquivo:** `itau-api-collection.json`

### Funcionalidades

- ✅ Variável `{{baseUrl}}` configurada como `http://localhost:8080`
- ✅ Token JWT salvo automaticamente após Login ou Criar Usuário
- ✅ Autenticação Bearer já configurada nas rotas protegidas

### Como importar

1. Abra o Postman
2. Clique em **File → Import**
3. Selecione o arquivo `itau-api-collection.json`
4. Execute **Login** ou **Criar Usuário** primeiro para obter o token

### Arquivo de Rotas

Para referência rápida, consulte o arquivo `rotas-api.txt` que contém:

- Todas as rotas da API com descrições
- Exemplos de request/response JSON
- Comandos curl para teste

---

## �📦 Build e Deploy

### Build do Projeto

```bash
./mvnw clean package
```

### Gerar Artefato

```bash
./mvnw clean install
```

O JAR será gerado em: `target/itau-0.0.1-SNAPSHOT.jar`

---

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT.

---

## 👨‍💻 Autor

Desenvolvido com ☕ e 💙

---

## � Fluxo de Autenticação

### 1. Criar Usuário (opcional)

```bash
curl -X POST http://localhost:8080/auth/create_user \
  -H "Content-Type: application/json" \
  -d '{"username": "meu_usuario", "password": "senha123"}'
```

### 2. Fazer Login

```bash
curl -X POST http://localhost:8080/auth \
  -H "Content-Type: application/json" \
  -d '{"username": "meu_usuario", "password": "senha123"}'
```

**Resposta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Usar Token nas Requisições

```bash
# Criar transação
curl -X POST http://localhost:8080/transacao \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{"valor": 1500.00}'

# Listar transações
curl -X GET http://localhost:8080/transacao \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Ver estatísticas
curl -X GET http://localhost:8080/estatistica \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

> 💡 **Dica**: O token JWT tem validade de **1 hora**. Após expirar, faça login novamente.

---

## �🔗 Links Úteis

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Lombok](https://projectlombok.org/)
- [SpringDoc OpenAPI](https://springdoc.org/)

---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela! ⭐**

</div>
