
# Projeto de Gerenciamento de Usuários em Java Spring Boot

Este repositório contém um projeto para gerenciamento de usuários, com uma API RESTful desenvolvida em Java Spring Boot. Abaixo estão exemplos de objetos JSON para interagir com as rotas da API.

A classe `UserController` é responsável por lidar com as operações relacionadas às ações.

## Modelo de Usuário (User)

A entidade User representa um usuário no sistema. Ela possui os seguintes atributos:

- **id:** Identificador único do usuário (gerado automaticamente).
- **name:** Nome do usuário.
- **email:** Endereço de e-mail do usuário (único).
- **password:** Senha do usuário (criptografada).
- **cpf:** Número de CPF do usuário (opcional, único).
- **phone:** Número de telefone do usuário.
- **createdAt:** Data e hora de criação do usuário.
- **updatedAt:** Data e hora da última atualização do usuário.

## Rotas da API

- Listar Todos os Usuários
- Método: GET
- Endpoint: /api/users/

----

- Cadastrar Novo Usuário
- Método: POST
- Endpoint: /api/users/

Recebe um objeto JSON no corpo da requisição. Exemplo:

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "cpf": "12345678901",
  "phone": "1234567890"
}
```
###  Excluir Usuário

**Método:** DELETE  
**Endpoint:** /api/users/{id}/

Exclui o usuário com o ID especificado na URL.

### Atualizar Usuário

**Método:** PUT  
**Endpoint:** /api/users/{id}/

Atualiza o usuário com o ID especificado na URL, recebendo um objeto JSON no corpo da requisição. Exemplo:

```json
{
  "name": "John Doe Jr.",
  "phone": "9876543210"
}
```

  
Claro, aqui está o README completo em formato Markdown:

markdownCopy code

`# Projeto de Gerenciamento de Usuários em Java Spring Boot

Este repositório contém um projeto para gerenciamento de usuários, com uma API RESTful desenvolvida em Java Spring Boot. Abaixo estão exemplos de objetos JSON para interagir com as rotas da API.

## Modelo de Usuário (User)

A entidade User representa um usuário no sistema. Ela possui os seguintes atributos:

- **id:** Identificador único do usuário (gerado automaticamente).
- **name:** Nome do usuário.
- **email:** Endereço de e-mail do usuário (único).
- **password:** Senha do usuário (criptografada).
- **cpf:** Número de CPF do usuário (opcional, único).
- **phone:** Número de telefone do usuário.
- **createdAt:** Data e hora de criação do usuário.
- **updatedAt:** Data e hora da última atualização do usuário.


## Rotas da API

### Listar Todos os Usuários

**Método:** GET  
**Endpoint:** /api/users/

Retorna todos os usuários cadastrados.

### Cadastrar Novo Usuário

**Método:** POST  
**Endpoint:** /api/users/

Recebe um objeto JSON no corpo da requisição. Exemplo:

```json
// Exemplo de criação de usuário com todos os campos preenchidos
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "cpf": "12345678901",
  "phone": "1234567890"
}
```

### Excluir Usuário

**Método:** DELETE  
**Endpoint:** /api/users/{id}/

Exclui o usuário com o ID especificado na URL.

### Atualizar Usuário

**Método:** PUT  
**Endpoint:** /api/users/{id}/

Atualiza o usuário com o ID especificado na URL, recebendo um objeto JSON no corpo da requisição. Exemplo:

```json
{
  "name": "John Doe Jr.",
  "phone": "9876543210"
}
```

### Login de Usuário

**Método:** POST  
**Endpoint:** /api/users/login/

Realiza o login do usuário, recebendo um objeto JSON no corpo da requisição. Exemplo:

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```
# Stock Management API

Este repositório contém um projeto para gerenciamento de ações (stocks) com uma API RESTful desenvolvida em Java Spring Boot. Abaixo estão exemplos de objetos JSON para interagir com as rotas da API.

A classe `StockController` é responsável por lidar com as operações relacionadas às ações.

### Modelo: Stock

A classe `Stock` representa uma ação no sistema.

#### Atributos

-   **id:** Identificador único da ação.
-   **ticker:** Símbolo único da ação.
-   **name:** Nome da empresa.
-   **type:** Setor da empresa.
-   **price:** Preço da ação.
-   **createdAt:** Data e hora de criação.
-   **updatedAt:** Data e hora da última atualização.
-   **imageURL:** URL da imagem representativa da empresa.

### Rotas da API

#### Cadastrar Ação

- **Método:** POST  
- **Endpoint:** /api/stocks/
- **Descrição:** Cria uma nova ação no sistema. Se a ação já existir, atualiza o preço.

**Exemplo de Requisição:**
```json
{
  "ticker": "AAPL",
  "price": 150.5
}
```

**Exemplo de Resposta**
```json
{
  "id": 1,
  "ticker": "AAPL",
  "name": "Apple Inc.",
  "type": "Tech",
  "price": 150.5,
  "createdAt": "2023-01-01T12:00:00",
  "updatedAt": "2023-01-01T12:30:00",
  "imageURL": "https://example.com/apple.png"
}
```

#### Consultar Ação

-   **Método:** GET
-   **Endpoint:** /api/stocks/{ticker}
-   **Descrição:** Retorna informações sobre uma ação com base no seu ticker.

**Exemplo de requisição**
-   **Endpoint:**  /api/stocks/BBAS

**Exemplo de Resposta**
```json
{
	"id": 109,
	"ticker": "BBAS3",
	"name": "BBAS3 - BANCO BRASIL ON: cotação e indicadores",
	"type": "STOCK",
	"price": 29.19,
	"createdAt": "2023-10-10T11:46:42",
	"updatedAt": "2023-10-10T11:46:42",
	"imageURL": "https://statusinvest.com.br//img/company/cover/331.jpg?v=39"
}
```
# Chart Management API

Este repositório contém um projeto para gerenciamento de gráficos relacionados a transações de ações, com uma API RESTful desenvolvida em Java Spring Boot. Abaixo estão exemplos de objetos JSON para interagir com as rotas da API.

A classe `ChartController` é responsável por lidar com operações relacionadas aos gráficos de transações de ações.

### Modelo: UserChart

A classe `UserChart` representa as ações que cada usuário tem usa como base as transações.

#### Atributos

-   **id:** Identificador único do registro.
-   **ticker:** Símbolo da ação.
-   **quantity:** Quantidade de ações.
-   **medianPrice:** Preço médio das ações.
-   **actualPrice:** Preço atual da ação.
-   **totalInvested:** Total investido.
-   **totalActual:** Total atual.
-   **profitPrejudice:** Lucro ou prejuízo.
-   **profitPrejudicePercent:** Percentual de lucro ou prejuízo.
-   **created_At:** Data de criação.
-   **updated_At:** Data de última atualização.

### Rotas da API

#### Comprar Ação

-   **Método:** POST
-   **Endpoint:** /api/charts/buy
-   **Descrição:** Realiza uma transação de compra de ações.

**Exemplo de Requisição:**
```json
{
  "token": "your_user_token",
  "ticker": "AAPL",
  "quantity": 10,
  "price": 150.5
}
```

**Exemplo de Resposta:**
```json
{
	"id": 36,
	"ticker": "TAEE11",
	"type": "buy",
	"quantity": 100.0,
	"price": 10.0,
	"totalInvested": 1000.0,
	"stockId": 212,
	"userId": 4,
	"created_At": "2023-10-15",
	"updated_At": "2023-10-15"
}
```

#### Preço Médio

-   **Método:** POST
-   **Endpoint:** /api/charts/average-price
-   **Descrição:** Calcula o preço médio de ações de um usuário.

**Exemplo de Requisição:**


```json
{
  "token": "your_user_token",
  "ticker": "BBAS3"
}
```

**Exemplo de Resposta:**

```json
	29.34
```

#### Informações da Carteira

-   **Método:** POST
-   **Endpoint:** /api/charts/chart-info
-   **Descrição:** Retorna informações da carteira baseado nas transações de ações de um usuário.

**Exemplo de Requisição:**

```json
{  "token":  "your_user_token"  }
```

**Exemplo de Resposta**

```json
[
  {
    "id": 1,
    "ticker": "AAPL",
    "quantity": 10,
    "medianPrice": 150.5,
    "actualPrice": 150.5,
    "totalInvested": 1505.0,
    "totalActual": 1505.0,
    "profitPrejudice": 0.0,
    "profitPrejudicePercent": 0.0,
    "created_At": "2023-01-01",
    "updated_At": "2023-01-01"
  },
  // ... outras transações
]
```
