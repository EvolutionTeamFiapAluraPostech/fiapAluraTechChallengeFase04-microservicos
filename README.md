# Pós-Tech-FIAP/ALURA-Fase04

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

# Descrição do projeto
Criar um sistema de gerenciamento de pedidos de venda, composto por microserviços de gestão de clientes, de produtos, de pedidos, logistica e uma aplicação para importação de dados de fontes extenas. O sistema será desenvolvido utilizando Java, Spring Boot, Spring Data JPA, Spring Open Feign e Spring Batch, entre outros.

## Requisitos:
1. Microsserviço de gerenciamento de clientes: microsserviço responsável por todas as operações relacionadas aos clientes, incluindo a criação, leitura, atualização e exclusão de registros de clientes (CRUD).
2. Microsserviço de catálogo de produtos: microsserviço responsável pelo CRUD de produtos e gerenciamento de estoque.
3. Microsserviço de gestão de pedidos: centralizará o processamento de todos os pedidos, desde a criação, até a conclusão, incluindo o recebimento, pagamento e comunicação com o microsserviço de entrega dos produtos dos pedidos.
4. Microsserviço de logística de entrega: responsável por gerenciar toda a logística de entrega de pedidos.

## Entregáveis:
1. Link do Github com o código fonte dos serviços desenvolvidos.
2. Documentação técnica.
3. Um relatório técnico descrevendo as tecnologias e ferramentas utilizadas, os desafios encontrados durante o desenvolvimento e as soluções implementadas para resolvê-las.

# Tecnologias utilizadas
1. Java 17
2. Gradle 7.6
3. Spring Boot 3.2.2
4. Spring Web MVC 
5. Spring Data JPA 
6. Spring Bean Validation 
7. Spring Doc Open API 2.3.0
8. Spring Batch
9. Spring Open Feign
10. Lombok 
11. Postgres 15.1 e Postgres 16.1
12. Flyway 
13. JUnit 5
14. Mockito
15. TestContainers
16. Docker

# Setup do Projeto

Para realizar o setup do projeto é necessário possuir o Java 17, Gradle 7.6, docker 24 e docker-compose 1.29 instalado em sua máquina.
Faca o download do projeto (https://github.com/EvolutionTeamFiapAluraPostech/fiapAluraTechChallengeFase04-microservicos) e atualize suas dependências com o gradle.
Antes de iniciar o projeto é necessário criar o banco de dados. O banco de dados está programado para ser criado em um container. 
Para criar o container, execute o docker-compose (Acesse a pasta raiz do projeto, no mesmo local onde encontra-se o arquivo compose.yaml). Para executá-lo, execute o comando docker-compose up -d (para rodar detached e não prender o terminal). O docker compose irá criar os bancos de dados, buildar a imagem de cada um dos microsserviços, iniciar a aplicação dentro do container correspondente (em sua porta específica). Desta maneira, o conjunto todo da solução estará disponível para ser consumido.

# Collection do Postman
* Marcelo-RM350802-Fiap-Alura-Tech Challenge-Fase04.postman_collection.json
* Esta collection está salva na raiz do projeto.

# Environments do Postman
* Marcelo-RM350802-Fiap-Alura-Tech Challenge-Fase04.postman_dev_environment.json
* Estas environments estão salvas na raiz do projeto.

# Documentação da API
* Local dev
  * Microsserviço de Gerenciamento de empresas - http://localhost:8082/swagger-ui/index.html
  * Microsserviço de Gerenciamento de clientes - http://localhost:8083/swagger-ui/index.html
  * Microsserviço de Gerenciamento de produtos - http://localhost:8084/swagger-ui/index.html
  * Microsserviço de Gerenciamento de pedidos - http://localhost:8085/swagger-ui/index.html
  * Microsserviço de Gerenciamento de entregas/logística - http://localhost:8086/swagger-ui/index.html
  * Microsserviço de Gerenciamento de importação de dados - http://localhost:8087/swagger-ui/index.html

# Documentação do PROJETO
* TechChallengeFase03-RM350802-MarceloAkioNishimoriV2.pdf
* PDF salvo na pasta documents deste projeto.
