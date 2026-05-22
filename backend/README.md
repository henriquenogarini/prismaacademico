# 🎓 Prisma Acadêmico - Backend

> Sistema de gestão acadêmica do Cursinho Comunitário Prisma da UTFPR-CP.  
> Protótipo funcional desenvolvido como projeto de extensão universitária.

---

## 📋 Descrição

O **Prisma Acadêmico** é um sistema de apoio administrativo e pedagógico do Cursinho Comunitário Prisma da UTFPR-CP. Permite a gestão completa do cursinho: inscrições, análise de candidatos, matrícula de alunos, gestão de turmas, professores voluntários, aulas, frequência, materiais, comunicados, simulados e relatórios de impacto.

---

## 🛠️ Tecnologias

| Tecnologia          | Versão     | Finalidade                    |
|---------------------|------------|-------------------------------|
| Java                | 21         | Linguagem principal           |
| Spring Boot         | 3.3.5      | Framework principal           |
| Spring Security     | 6.x        | Autenticação e autorização    |
| JWT (jjwt)          | 0.12.6     | Tokens de acesso              |
| PostgreSQL          | 16         | Banco de dados principal      |
| Redis               | 7          | Cache e blacklist de tokens   |
| Flyway              | 10.x       | Migrations de banco de dados  |
| Swagger/OpenAPI     | 2.6.0      | Documentação da API           |
| Lombok              | 1.18.34    | Redução de boilerplate        |
| Maven               | 3.9.x      | Gerenciador de dependências   |
| Docker + Compose    | Latest     | Containerização               |

---

## ✅ Pré-requisitos

- **Docker Desktop** (versão 24+)
- **Docker Compose** (versão 2.x)

Para rodar localmente sem Docker:
- Java 21 JDK
- Maven 3.9+
- PostgreSQL 16 rodando localmente
- Redis 7 rodando localmente

---

## 🚀 Como Rodar com Docker Compose

### 1. Clone o repositório

```bash
git clone https://github.com/utfpr-prisma/prisma-academico-backend.git
cd prisma-academico-backend
```

### 2. Verifique o arquivo `.env`

O arquivo `.env` já está configurado para ambiente local com valores de desenvolvimento. Revise as variáveis se necessário:

```env
DB_USERNAME=prisma_user
DB_PASSWORD=prisma_password
JWT_SECRET=change-this-secret-to-a-secure-256-bit-key-for-production-use-only
```

### 3. Suba os containers

```bash
docker compose up --build
```

Aguarde os containers inicializarem (pode levar 1-2 minutos na primeira vez enquanto o Maven faz o build).

### 4. Acesse a aplicação

| Serviço    | URL                                        |
|------------|-------------------------------------------|
| API        | http://localhost:8080                      |
| Swagger UI | http://localhost:8080/swagger-ui.html      |
| API Docs   | http://localhost:8080/v3/api-docs          |
| PostgreSQL | localhost:5432                             |
| Redis      | localhost:6379                             |

---

## 💻 Como Rodar Localmente (sem Docker)

### 1. Configure PostgreSQL e Redis locais

Certifique-se de ter PostgreSQL (porta 5432) e Redis (porta 6379) rodando localmente.

Crie o banco:
```sql
CREATE USER prisma_user WITH PASSWORD 'prisma_password';
CREATE DATABASE prisma_academico OWNER prisma_user;
```

### 2. Configure as variáveis de ambiente

Copie `.env.example` para `.env` e ajuste os valores. Em especial:
```env
DB_URL=jdbc:postgresql://localhost:5432/prisma_academico
REDIS_HOST=localhost
```

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

Ou com `maven`:
```bash
mvn spring-boot:run
```

---

## 🔐 Usuários Demo

Após iniciar a aplicação, os seguintes usuários demo são criados automaticamente:

| E-mail                           | Senha   | Papel        |
|----------------------------------|---------|--------------|
| `admin@prisma.com`               | `123456`| ADMIN        |
| `coordination@prisma.com`        | `123456`| COORDINATION |
| `teacher@prisma.com`             | `123456`| TEACHER      |
| `student@prisma.com`             | `123456`| STUDENT      |
| `candidate@prisma.com`           | `123456`| CANDIDATE    |

---

## 🔑 Autenticação

A API usa JWT. Para autenticar:

**1. Login:**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "coordination@prisma.com",
  "password": "123456"
}
```

**2. Use o token retornado:**
```http
Authorization: Bearer <seu_token_jwt>
```

**3. Logout:**
```http
POST /api/auth/logout
Authorization: Bearer <seu_token_jwt>
```

---

## 📡 Endpoints Principais

### Autenticação
```
POST   /api/auth/login
GET    /api/auth/me
POST   /api/auth/logout
```

### Usuários
```
GET    /api/users
POST   /api/users
PUT    /api/users/{id}
PATCH  /api/users/{id}/status
DELETE /api/users/{id}
```

### Processos Seletivos
```
GET    /api/selection-processes
GET    /api/selection-processes/open
POST   /api/selection-processes
PUT    /api/selection-processes/{id}
PATCH  /api/selection-processes/{id}/status
DELETE /api/selection-processes/{id}
```

### Candidatos
```
GET    /api/candidates
POST   /api/candidates/public-application   ← Público, sem autenticação
GET    /api/candidates/status/{document}     ← Consulta pública
POST   /api/candidates/{id}/approve
POST   /api/candidates/{id}/reject
POST   /api/candidates/{id}/convert-to-student
```

### Alunos
```
GET    /api/students
GET    /api/students/{id}/overview
PATCH  /api/students/{id}/status
```

### Turmas
```
GET    /api/classes
POST   /api/classes
POST   /api/classes/{classId}/students/{studentId}
GET    /api/classes/{id}/students
GET    /api/classes/{id}/overview
```

### Frequência
```
GET    /api/attendance/lesson/{lessonId}
GET    /api/attendance/student/{studentId}
POST   /api/attendance
POST   /api/attendance/batch
```

### Relatórios
```
GET    /api/reports/overview
GET    /api/reports/applications-by-status
GET    /api/reports/attendance-by-class
GET    /api/reports/students-by-school
GET    /api/reports/extension-summary
```

---

## 📚 Swagger / OpenAPI

A documentação completa da API está disponível em:

**http://localhost:8080/swagger-ui.html**

Na interface do Swagger, clique em **Authorize** e informe:
```
Bearer seu_token_jwt_aqui
```

---

## 🗄️ Banco de Dados

### Migrations Flyway

As migrações são executadas automaticamente ao iniciar a aplicação:

| Migration | Descrição                                    |
|-----------|----------------------------------------------|
| V1        | Tabela de usuários                           |
| V2        | Tabela de processos seletivos                |
| V3        | Tabela de candidatos                         |
| V4        | Tabela de documentos de candidatos           |
| V5        | Tabelas de alunos e turmas                   |
| V6        | Tabelas de disciplinas, professores e aulas  |
| V7        | Tabela de frequência                         |
| V8        | Tabela de materiais                          |
| V9        | Tabelas de simulados e resultados            |
| V10       | Tabela de comunicados                        |
| V11       | Nota sobre seed de dados                     |

### Seed de Dados

Os dados demo são criados pelo `DataSeeder.java` na primeira inicialização (quando o banco está vazio). Incluem:

- 9 usuários (admin, coordenação, 5 professores, 1 aluno, 1 candidato)
- 1 processo seletivo aberto (2026/1)
- 7 candidatos com status variados
- 4 alunos matriculados
- 2 turmas (Turma A - Manhã, Turma B - Tarde)
- 5 disciplinas
- 5 professores voluntários
- 4 aulas com frequência registrada
- 4 materiais didáticos
- 4 comunicados

---

## 🔒 Controle de Permissões

| Role          | Permissões                                              |
|---------------|---------------------------------------------------------|
| ADMIN         | Acesso total ao sistema                                 |
| COORDINATION  | Gestão completa: candidatos, alunos, turmas, relatórios |
| TEACHER       | Aulas, frequência, materiais, comunicados              |
| STUDENT       | Consulta: turmas, materiais, comunicados, simulados    |
| CANDIDATE     | Consulta de status da própria inscrição                |

---

## 🧪 Testes

Execute os testes unitários:
```bash
mvn test
```

Os testes incluem:
- `AuthServiceTest` - Autenticação e logout
- `CandidateServiceTest` - Criação, validação e conversão para aluno
- `ReportServiceTest` - Cálculo de indicadores

---

## 🌍 CORS

A API permite requisições dos seguintes origins por padrão:
- `http://localhost:5173` (Frontend Vite/React em dev)
- `http://127.0.0.1:5173`

Configure a variável `CORS_ALLOWED_ORIGINS` no `.env` para adicionar outros origins.

---

## ⚙️ Variáveis de Ambiente

| Variável                    | Descrição                          | Padrão                  |
|-----------------------------|------------------------------------|-------------------------|
| `APP_PORT`                  | Porta da aplicação                 | `8080`                  |
| `DB_URL`                    | URL de conexão com PostgreSQL      | -                       |
| `DB_USERNAME`               | Usuário do banco                   | -                       |
| `DB_PASSWORD`               | Senha do banco                     | -                       |
| `REDIS_HOST`                | Host do Redis                      | `redis`                 |
| `REDIS_PORT`                | Porta do Redis                     | `6379`                  |
| `JWT_SECRET`                | Chave secreta para JWT             | -                       |
| `JWT_EXPIRATION_MINUTES`    | Expiração do token em minutos      | `120`                   |
| `CORS_ALLOWED_ORIGINS`      | Origins CORS permitidos            | `http://localhost:5173` |

---

## 🔐 Segurança e Produção

> ⚠️ **ATENÇÃO:** Os valores no arquivo `.env` são apenas para **ambiente local de desenvolvimento**.

Para produção:
1. **Nunca** versione o `.env` com credenciais reais
2. Use um `JWT_SECRET` de pelo menos 256 bits aleatórios
3. Use senhas fortes para PostgreSQL e Redis
4. Configure `CORS_ALLOWED_ORIGINS` com apenas os origins necessários
5. Desative o Swagger em produção se necessário
6. Use HTTPS

---

## 📁 Estrutura do Projeto

```
src/main/java/br/edu/utfpr/prismaacademico/
├── PrismaAcademicoApplication.java
├── DataSeeder.java
├── config/          - Configurações (CORS, Redis, Swagger, JWT)
├── security/        - Spring Security, JWT filter, handlers
├── auth/            - Login, logout, me
├── users/           - Gerenciamento de usuários
├── selectionprocess/ - Processos seletivos
├── candidates/      - Inscrições e candidatos
├── students/        - Alunos e matrículas
├── classgroups/     - Turmas
├── subjects/        - Disciplinas
├── teachers/        - Professores voluntários
├── lessons/         - Aulas
├── attendance/      - Frequência
├── materials/       - Materiais didáticos
├── exams/           - Simulados e resultados
├── announcements/   - Comunicados
├── reports/         - Relatórios e indicadores
└── common/          - Exceções globais e Redis service
```

---

## 📄 Licença

Este projeto é um **protótipo acadêmico open-source** desenvolvido para o projeto de extensão Cursinho Comunitário Prisma da UTFPR-CP.

Licença MIT - consulte o arquivo `LICENSE` para detalhes.

---

## 👥 Autores

Desenvolvido como parte do Projeto de Extensão Universitária - **Cursinho Comunitário Prisma UTFPR-CP**

---

*Prisma Acadêmico - 2026 | UTFPR-CP*

