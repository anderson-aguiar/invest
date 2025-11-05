# 💰 API de Gestão de Investimentos Pessoais

> 🚧 Projeto em andamento

---

## 🧩 Visão Geral

A **API de Gestão de Investimentos Pessoais** tem como objetivo permitir que usuários gerenciem suas carteiras de investimentos, acompanhem seus ativos e recebam notificações automáticas via RabbitMQ e e-mail.

O projeto foi construído com **Java 17**, **Spring Boot 3**, **PostgreSQL**, **RabbitMQ** e segue boas práticas de segurança, testes e observabilidade.

---

## ⚙️ Funcionalidades Principais

### 🔐 Autenticação e Autorização
- Cadastro e login de usuários.
- Geração de tokens **JWT** com expiração de 30 minutos e refresh via `/auth/refresh`.
- Perfis de acesso: `USER` e `ADMIN`.

**Endpoints:**
- `POST /auth/register` → envia mensagem RabbitMQ para fila de e-mails.  
- `POST /auth/login` → retorna JWT.  
- `POST /auth/refresh` → gera novo token.  

---

### 💼 Carteiras e Investimentos
- Criação e listagem de carteiras por usuário.
- Adição, listagem e remoção de investimentos.
- Atualização de saldo da carteira.

**Endpoints:**
- `POST /wallets` → cria carteira.  
- `GET /wallets` → lista carteiras do usuário.  
- `GET /wallets/{id}/investments` → lista investimentos da carteira.  
- `POST /wallets/{id}/investments` → adiciona investimento.  
- `DELETE /investments/{id}` → remove investimento e envia notificação RabbitMQ.  
- `PATCH /wallets/{id}/balance` → atualiza saldo.  

---

### 📊 Relatórios
- `GET /users/{id}/summary` → retorna:
  - Total investido  
  - Número de ativos  
  - Média de risco  

---

## 📨 Integração com RabbitMQ
- **Exchange:** `email.exchange`  
- **Filas:**  
  - `email.welcome` → Envio de e-mail de boas-vindas.  
  - `email.investment.removed` → Notificação de remoção de investimento.  

O **consumer** consome as mensagens e envia e-mails automáticos com o **Spring Boot Mail** (Mailtrap ou logs no console).

---

## 🧠 Health Check e Observabilidade
- **Spring Boot Actuator:**  
  - `/actuator/health`  
  - `/actuator/info`  
  - `/actuator/metrics`  
- **Custom Health Check:** `/health/custom` → verifica conexão com **DB** e **RabbitMQ**.

---

## 🧪 Testes Automatizados
- **JUnit 5** + **Mockito**  
- Casos de teste:
  - Operações de carteira e investimento  
  - Relatórios e integração com RabbitMQ  
  - Health check da aplicação  

---

## 🛠️ Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot 3.x**
- **PostgreSQL**
- **RabbitMQ**
- **Spring Security + JWT**
- **Bean Validation**
- **JUnit 5 + Mockito**
- **Docker Compose**

---

## 🧱 Estrutura de Domínio

| Entidade | Relacionamentos | Atributos principais |
|-----------|-----------------|----------------------|
| **User** | 1 --- N Wallet | id, name, email, password, role, createdAt |
| **Wallet** | 1 --- N Investment | id, name, balance, user, createdAt |
| **Investment** | N --- 1 Asset | id, quantity, purchasePrice, purchaseDate, wallet, asset |
| **Asset** | — | id, ticker, type, riskLevel, createdAt |

---

## 🧩 Desafio Extra (opcional)
- Endpoint: `/emails/logs` → histórico de mensagens via RabbitMQ (somente ADMIN).

---

## 🧾 Critérios Avaliados
- Arquitetura limpa e modular  
- Segurança e boas práticas  
- Modelagem correta das entidades  
- Integração RabbitMQ e envio de e-mails  
- Testes automatizados  
- Observabilidade com Actuator  

---

## 📅 Status do Projeto
🚧 **Em desenvolvimento ativo.**  

---

## 👨‍💻 Autor
**Anderson Martins**  
🔗 [LinkedIn](https://www.linkedin.com/in/anderson-tech/) | 💻 [GitHub](https://github.com/anderson-aguiar)
