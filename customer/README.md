# Pós-Tech-FIAP/ALURA-Fase04

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

# Descrição do projeto
Repositório do projeto da pós tech da FIAP/ALURA. Desenvolvimento de um microserviço de gerenciamento de clientes.

## Requisitos:
1. Este serviço será responsável por todas as operações relacionadas aos clientes, incluindo a criação, leitura, atualização e exclusão de registros de clientes (CRUD).

## Entregáveis:
1. Link do Github com o código fonte dos serviços desenvolvidos.
2. Documentação técnica (pode ser em JavaDoc, Swagger, etc).
3. Um relatório técnico descrevendo as tecnologias e ferramentas utilizadas, os desafios encontrados durante o desenvolvimento e as soluções implementadas para resolvê-las.

# Tecnologias utilizadas
1. Java 17
2. Spring Boot 3.2.4
3. Spring Web MVC
4. Spring Data JPA
5. Spring Bean Validation
6. Spring Open Feign
7. Spring Doc Open API
8. Lombok
9. Postgres 16.2
10. Flyway
11. JUnit
12. Mockito
13. TestContainers
14. Docker
17. Ethereal - fake SMTP

# Setup do Projeto

Para realizar o setup do projeto é necessário possuir o Java 17, docker 24 e docker-compose 2.3.3 instalado em sua máquina.
Faca o download do projeto (https://github.com/EvolutionTeamFiapAluraPostech/fiapAluraTechChallenge) e atualize suas dependências com o gradle.
Antes de iniciar o projeto é necessário criar o banco de dados. O banco de dados está programado para ser criado em um container. 
Para criar o container, execute o docker-compose.
Acesse a pasta raiz do projeto, no mesmo local onde encontra-se o arquivo docker-compose.yml. Para executá-lo, execute o comando docker-compose up -d (para rodar detached e não prender o terminal).
Para iniciar o projeto, basta executar o Spring Boot Run no IntelliJ.
Após a inicialização do projeto, será necessário se autenticar, pois o Spring Security está habilitado. Para tanto, utilize o Postman (ou outra aplicação de sua preferência), crie um endpoint para realizar a autenticação, com a seguinte url **localhost:8080/authenticate**. No body, inclua um json contendo o atributo “email” com o valor “thomas.anderson@itcompany.com” e outro atributo “password” com o valor “@Bcd1234”. Realize a requisição para este endpoint para se obter o token JWT que deverá ser utilizado para consumir os demais endpoints do projeto.
Segue abaixo instruções do endpoint para se autenticar na aplicação.

POST /authenticate HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 76

{
"email": "thomas.anderson@itcompany.com",
"password": "@Bcd1234"
}

# Documentação da API
http://localhost:8080/swagger-ui/index.html

# Documentação do PROJETO
https://www.notion.so/Evolution-Team-Digital-Parking-96c5767bc5ac4506b4497998f261fef9?pvs=4

# Collection do Postman
* Marcelo-RM350802-Fiap-Alura-Arq-Dev-Java-Tech-Challenge-Fase-02.postman_collection.json
* Esta collection está salva na raiz do projeto.
