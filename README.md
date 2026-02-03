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

- ✅ Criação e consulta de transações
- ✅ Cálculo de estatísticas em tempo real
- ✅ Rate limiting configurável
- ✅ Validação de valores mínimos e máximos
- ✅ Tratamento global de exceções
- ✅ Persistência em PostgreSQL

---

## ✨ Características

### 🔐 Validações e Segurança

- **Rate Limiting**: Limite configurável de transações por minuto
- **Validação de Valores**: Valores mínimo e máximo configuráveis
- **Tratamento de Erros**: GlobalExceptionHandler com respostas padronizadas
- **Bean Validation**: Validações automáticas nos DTOs

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
  - Spring Validation
  - Spring Actuator
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

4️⃣ **Acesse a aplicação**

- API: <http://localhost:8080>
- Swagger UI: <http://localhost:8080/swagger-ui.html>
- Actuator: <http://localhost:8080/actuator>

---

## 🔌 Endpoints API

### 📝 Transações

#### Criar Transação

```http
POST /transacao
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
  "dataHora": "2026-02-03T10:30:00"
}
```

#### Listar Todas as Transações

```http
GET /transacao
```

**Resposta:** `200 OK`

```json
[
  {
    "id": 1,
    "valor": 4500.0,
    "dataHora": "2026-02-03T10:30:00"
  },
  {
    "id": 2,
    "valor": 3200.5,
    "dataHora": "2026-02-03T10:31:00"
  }
]
```

#### Buscar Transação por ID

```http
GET /transacao/{id}
```

**Resposta:** `200 OK`

```json
{
  "id": 1,
  "valor": 4500.0,
  "dataHora": "2026-02-03T10:30:00"
}
```

### 📊 Estatísticas

#### Obter Estatísticas

```http
GET /estatistica
```

**Resposta:** `200 OK`

```json
{
  "count": 10,
  "avg": 3500.50,
  "max": 8000.00,
  "min": 1000.00,
  "sum": 35005.00,
  "transacoes": [...]
}
```

---

## ⚙️ Configuração

Edite `src/main/resources/application.yml`:

```yaml
estatistica:
  intervaloEmSegundos: 60 # Janela de tempo para estatísticas

transacoes:
  limitePorMinuto: 2 # Rate limit
  valorMaximoPorTransacao: 1000000 # Valor máximo permitido
  valorMinimoPorTransacao: 0.01 # Valor mínimo permitido
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

### Códigos de Status

| Código | Descrição                               |
| ------ | --------------------------------------- |
| `200`  | OK - Requisição bem-sucedida            |
| `201`  | Created - Recurso criado                |
| `400`  | Bad Request - Erro de validação         |
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
├── config/              # Configurações (OpenAPI)
├── controller/          # Controllers REST
├── dto/                 # Data Transfer Objects
│   ├── request/
│   └── response/
├── exception/           # Exceptions customizadas e handlers
├── mapper/              # Conversores DTO ↔ Model
├── model/               # Entidades JPA
├── properties/          # Configuration Properties
├── repository/          # Repositórios JPA
└── service/             # Lógica de negócio
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

## 📦 Build e Deploy

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

## 🔗 Links Úteis

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Lombok](https://projectlombok.org/)
- [SpringDoc OpenAPI](https://springdoc.org/)

---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela! ⭐**

</div>
