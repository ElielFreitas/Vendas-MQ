# Sistema de Vendas - Next.js + Spring Boot + RabbitMQ + PostgreSQL

Sistema de gerenciamento de vendas com processamento assíncrono usando mensageria.

## 🏗️ Arquitetura

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐      ┌──────────────┐
│   Frontend  │──────│  Backend API │──────│   RabbitMQ  │──────│   Consumer   │
│  Next.js    │      │ Spring Boot  │      │   (Queue)   │      │  (Processor) │
│  :3000      │      │   :8080      │      │             │      │              │
└─────────────┘      └──────────────┘      └─────────────┘      └─────────���────┘
                            │                                            │
                            │                                            │
                            └──────────────────┬─────────────────────────┘
                                               │
                                        ┌──────────────┐
                                        │  PostgreSQL  │
                                        │    :5432     │
                                        └──────────────┘
```

## 🚀 Tecnologias

- **Frontend**: Next.js 16, TypeScript, Tailwind CSS
- **Backend**: Spring Boot 3, Java 17
- **Message Broker**: RabbitMQ
- **Banco de Dados**: PostgreSQL
- **Build Tool**: Maven

## 📋 Pré-requisitos

- Java 17+
- Maven 3.6+
- Node.js 18+
- Docker e Docker Compose

## 🛠️ Instalação

### 1. Clonar o projeto

```bash
git clone https://github.com/ElielFreitas/Vendas-MQ.git
cd Vendas-MQ
```

### 2. Subir os containers (PostgreSQL + RabbitMQ)

```bash
docker-compose up -d
```

### 3. Backend (Spring Boot)

```bash
cd vendas
mvn spring-boot:run
```

O backend estará disponível em: http://localhost:8080

### 4. Frontend (Next.js)

```bash
cd frontend
npm install
npm run dev
```

O frontend estará disponível em: http://localhost:3000

## 📡 API Endpoints

### Criar Venda
```bash
POST http://localhost:8080/api/vendas
Content-Type: application/json

{
  "produto": "Nome do Produto",
  "valor": 100.00
}
```

### Listar Vendas
```bash
GET http://localhost:8080/api/vendas
```

## 🔧 Fluxo de Funcionamento

1. **Criação**: Usuário cadastra venda pelo frontend
2. **Persistência**: Venda é salva no PostgreSQL com status `PENDENTE`
3. **Mensageria**: Producer envia mensagem para o RabbitMQ
4. **Processamento**: Consumer recebe mensagem e atualiza status para `PROCESSADO`
5. **Atualização**: Frontend faz polling e exibe status atualizado

## 🐳 Docker Compose

```yaml
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: vendasdb
      POSTGRES_USER: cytrus
      POSTGRES_PASSWORD: cytrus123
    ports:
      - "5432:5432"

  rabbitmq:
    image: rabbitmq:3-management
    environment:
      RABBITMQ_DEFAULT_USER: cytrus
      RABBITMQ_DEFAULT_PASS: cytrus123
    ports:
      - "5672:5672"
      - "15672:15672"
```

## 📂 Estrutura do Projeto

```
Vendas-MQ/
├── frontend/                    # Next.js Frontend
│   ├── src/
│   │   ├── app/               # Páginas
│   │   ├── components/        # Componentes React
│   │   ├── services/         # API client
│   │   └── types/            # TypeScript types
│   └── package.json
│
└── vendas/                     # Spring Boot Backend
    └── src/main/java/com/cytrus/vendas/
        ├── controller/        # REST Controllers
        ├── service/          # Business Logic
        ├── repository/       # Data Access
        ├── model/            # Entities
        ├── dto/             # Data Transfer Objects
        ├── producer/        # RabbitMQ Producer
        └── consumer/        # RabbitMQ Consumer
```

## 📊 Status das Vendas

| Status      | Descrição                              |
|-------------|----------------------------------------|
| `PENDENTE`  | Venda criada, aguardando processamento |
| `PROCESSADO`| Venda processada pelo consumer         |

## 🔍 Monitoramento

- **RabbitMQ Management**: http://localhost:15672 
