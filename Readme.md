# 🍕 GoPizza API

API REST para o sistema de gestão de pizzaria GoPizza, desenvolvida com Spring Boot.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Configuração](#configuração)
- [Como Executar](#como-executar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Migrations](#migrations)
- [Documentação da API](#documentação-da-api)
- [Comandos Úteis](#comandos-úteis)
- [Troubleshooting](#troubleshooting)

## 🎯 Sobre o Projeto

A GoPizza API é uma aplicação REST desenvolvida para gerenciar operações de uma pizzaria, incluindo cadastro e gerenciamento de usuários. A API utiliza Spring Boot 4.0.1 com Java 21 e PostgreSQL como banco de dados.

## 🛠 Tecnologias

- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.1** - Framework Java
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - Construção de APIs REST
- **SpringDoc OpenAPI 2.7.0** - Documentação da API (Swagger)
- **Flyway** - Controle de versão do banco de dados
- **PostgreSQL 16** - Banco de dados relacional
- **Docker & Docker Compose** - Containerização
- **Maven** - Gerenciamento de dependências

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21** ou superior
- **Maven 3.9** ou superior
- **Docker** e **Docker Compose** (versão 2.0+)
- **Git** (opcional, para clonar o repositório)

### Verificando as instalações

```bash
java -version
mvn -version
docker --version
docker compose version
```

## ⚙️ Configuração

### 1. Clone o repositório (se aplicável)

```bash
git clone <url-do-repositorio>
cd gopizza
```

**⚠️ Importante:** Para produção, use senhas seguras e não commite o arquivo `.env` no repositório.

## 🚀 Como Executar

### Opção 1: Usando Docker Compose (Recomendado)

Esta é a forma mais simples de executar o projeto, pois já configura o banco de dados e a aplicação:

```bash
# Construir e iniciar os containers
docker compose up -d --build

# Verificar o status dos containers
docker compose ps

# Visualizar os logs da aplicação
docker compose logs -f app

# Parar os containers
docker compose down

# Parar e remover volumes (limpar banco de dados)
docker compose down -v
```

A aplicação estará disponível em: **[http://localhost:8080](http://localhost:8080)**

### Opção 2: Execução Local (Sem Docker)

Se preferir executar localmente sem Docker:

1. **Instale e configure o PostgreSQL** localmente
2. **Configure as variáveis de ambiente** no arquivo `.env`
3. **Execute a aplicação:**

```bash
# Compilar o projeto
mvn clean package

# Executar a aplicação
mvn spring-boot:run
```

Ou execute o JAR diretamente:

```bash
java -jar target/gopizza-0.0.1-SNAPSHOT.jar
```

## 📁 Estrutura do Projeto

```text
gopizza/
├── src/
│   ├── main/
│   │   ├── java/com/gopizza/
│   │   │   ├── config/          # Configurações da aplicação
│   │   │   ├── controller/       # Controllers REST
│   │   │   │   ├── HomeController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── CreateUserDTO.java
│   │   │   │   └── UserResponseDTO.java
│   │   │   ├── exception/        # Tratamento de exceções
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── model/            # Entidades JPA
│   │   │   │   └── User.java
│   │   │   ├── repository/       # Repositórios Spring Data JPA
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/          # Lógica de negócio
│   │   │   │   └── UserService.java
│   │   │   └── GopizzaApplication.java
│   │   └── resources/
│   │       ├── application.yaml           # Configuração local
│   │       ├── application-docker.yaml    # Configuração Docker
│   │       └── db/migration/              # Scripts Flyway
│   │           └── V1__create_users_table.sql
│   └── test/                      # Testes
├── docker-compose.yml             # Configuração Docker Compose
├── Dockerfile                     # Imagem Docker da aplicação
├── pom.xml                        # Dependências Maven
├── .example.env                   # Exemplo de variáveis de ambiente
└── README.md                      # Este arquivo
```

## 🔌 Endpoints da API

### Informações da API

- **GET** `/api` - Retorna informações sobre a API e lista de endpoints disponíveis

### Usuários

Todos os endpoints de usuários estão sob o prefixo `/api/users`:

| Método    | Endpoint                    | Descrição                  |
| :-------- | :-------------------------- | :------------------------- |
| **POST**  | `/api/users`                | Criar novo usuário         |
| **GET**   | `/api/users`                | Listar todos os usuários   |
| **GET**   | `/api/users/{id}`           | Buscar usuário por ID      |
| **GET**   | `/api/users/email/{email}`  | Buscar usuário por email   |
| **PUT**   | `/api/users/{id}`           | Atualizar usuário          |
| **DELETE**| `/api/users/{id}`           | Deletar usuário            |

### Exemplo de Requisição

**Criar usuário:**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "phone": "11999999999",
    "password": "senha123"
  }'
```

**Listar usuários:**

```bash
curl http://localhost:8080/api/users
```

## 🔐 Variáveis de Ambiente

### Variáveis da Aplicação (Docker)

As seguintes variáveis são configuradas automaticamente no Docker Compose:

- `SPRING_PROFILES_ACTIVE=docker`
- `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/gopizza_db`
- `SPRING_DATASOURCE_USERNAME` (herdado de `POSTGRES_USER`)
- `SPRING_DATASOURCE_PASSWORD` (herdado de `POSTGRES_PASSWORD`)

## 🗄 Migrations

O projeto utiliza **Flyway** para controle de versão do banco de dados. As migrations estão localizadas em `src/main/resources/db/migration/`.

### Migrations Existentes

- **V1__create_users_table.sql** - Cria a tabela `users` com os campos necessários

### Criando uma Nova Migration

1. Crie um arquivo SQL seguindo o padrão: `V{versão}__{descricao}.sql`
2. Coloque o arquivo em `src/main/resources/db/migration/`
3. A migration será executada automaticamente na próxima inicialização da aplicação

**Exemplo:**

```sql
-- V2__add_user_role_column.sql
ALTER TABLE users ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'USER';
```

## 📚 Documentação da API

A API possui documentação interativa usando Swagger UI:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs (JSON)**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
- **API Info**: [http://localhost:8080/api](http://localhost:8080/api)

A documentação é gerada automaticamente a partir das anotações nos controllers e está disponível apenas quando a aplicação está rodando.

## 🛠 Comandos Úteis

### Docker Compose

```bash
# Iniciar containers
docker compose up -d

# Parar containers
docker compose down

# Reconstruir e reiniciar
docker compose up -d --build

# Ver logs
docker compose logs -f app
docker compose logs -f postgres

# Verificar status
docker compose ps

# Reiniciar um serviço específico
docker compose restart app

# Parar e remover volumes (limpar dados)
docker compose down -v
```

### Maven

```bash
# Compilar o projeto
mvn clean package

# Executar testes
mvn test

# Executar a aplicação
mvn spring-boot:run

# Limpar build
mvn clean
```

### Banco de Dados

```bash
# Conectar ao PostgreSQL no container
docker compose exec postgres psql -U postgres -d gopizza_db

# Listar tabelas
docker compose exec postgres psql -U postgres -d gopizza_db -c "\dt"

# Ver dados da tabela users
docker compose exec postgres psql -U postgres -d gopizza_db -c "SELECT * FROM users;"
```

## 🔧 Troubleshooting

### Problema: Porta 5432 já está em uso

**Solução:** Altere a porta no `docker-compose.yml`:

```yaml
ports:
  - "5433:5432"  # Use 5433 externamente
```

E atualize o `.env`:

```env
POSTGRES_PORT=5433
```

### Problema: Erro de autenticação do PostgreSQL

**Solução:** Remova o volume antigo e recrie:

```bash
docker compose down -v
docker compose up -d
```

### Problema: Container reiniciando constantemente

**Solução:** Verifique os logs para identificar o erro:

```bash
docker compose logs app
```

### Problema: Erro "Ambiguous mapping"

**Solução:** Certifique-se de que não há conflito de rotas. O endpoint raiz (`/`) é usado pelo Swagger UI.

### Problema: Migration não está sendo executada

**Solução:** Verifique se:

1. O arquivo está em `src/main/resources/db/migration/`
2. O nome segue o padrão `V{versão}__{descricao}.sql`
3. O Flyway está habilitado no `application.yaml`

## 📝 Notas Adicionais

- A aplicação utiliza o perfil `docker` quando executada via Docker Compose
- O Hibernate está configurado com `ddl-auto: validate` no Docker para garantir que apenas migrations sejam aplicadas
- O SpringDoc está configurado para documentar apenas endpoints sob `/api/**`
- A aplicação roda na porta **8080** por padrão

## 📄 Licença

Este projeto é privado e de uso interno.

---

## 👨‍💻 Desenvolvido com ❤️ para GoPizza
