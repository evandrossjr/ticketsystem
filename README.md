# 🎟️ Sistema de Gerenciamento de Tickets

Este repositório contém a aplicação **backend** de um sistema para gerenciar tickets de suporte.  
O projeto foi desenvolvido em **Java com Spring Boot**, seguindo os princípios da **Arquitetura Limpa** e aplicando **práticas modernas de desenvolvimento**.

---

## 🚀 Funcionalidades Atuais

- **API RESTful**: Gerenciamento completo de tickets e usuários (CRUD).
- **Gerenciamento de Tickets**: Criação, leitura, atualização e exclusão de tickets.
- **Integração com JPA**: Uso do Spring Data JPA para persistência de dados.
- **Banco de Dados**: Banco de dados em memória **H2** para desenvolvimento.
- **Documentação**: Geração automática da API com **Swagger/OpenAPI**.
- **Qualidade de Código**: Análise com **SonarQube** e testes de integração automatizados.
- **CI/CD**: Pipeline de Integração Contínua com **GitHub Actions** para testes e análise a cada push.

---

## 🛠️ Próximos Passos

As próximas melhorias planejadas para o sistema incluem:

- **Atribuição de Tickets**: Permitir que um ticket seja atribuído a um usuário responsável.
- **Atualização de Status**: Implementar lógica de mudança de status dos tickets.
- **Autenticação e Autorização**: Adicionar sistema de login e controle de acesso com **Spring Security**.

---

## ⚙️ Como Rodar a Aplicação

### ✅ Pré-requisitos
- **JDK 17+**
- **Maven 3.9+**

### ▶️ Executando com Maven

Clone o repositório:
```bash
git clone https://github.com/seu-usuario/ticketsystem.git
cd ticketsystem
```
Rode a aplicação:
```bash
mvn spring-boot:run
```

A API estará disponível em:
👉 http://localhost:8080

### 📚 Documentação da API

Após iniciar o projeto, acesse a documentação gerada automaticamente pelo Swagger em:  
👉 http://localhost:8080/swagger-ui.html  

---

## 👨‍💻 Autor

Nome: Evandro Sacramento Jr.  
GitHub: [@evandrossjr  ](https://github.com/evandrossjr)



