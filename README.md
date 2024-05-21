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
4. Spring Web MVC (compatível com o Spring Boot) 
5. Spring Data JPA (compatível com o Spring Boot)  
6. Spring Bean Validation (compatível com o Spring Boot) 
7. Spring Doc Open API 2.3.0
8. Spring Batch 5
9. Spring Open Feign 4.1.1
10. Lombok 
11. Postgres 15.1 e Postgres 16.3
12. Flyway 
13. JUnit 5
14. Mockito
15. TestContainers
16. Docker
17. WireMock 3.3.1

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
O projeto está dividido em 6 containers de microsserviços backend Java Spring Boot e outros 6 containers de banco de dados Postgresql. Cada um dos microsserviços possui seu respectivo banco de dados.

O backend foi implementado seguindo as recomendações da Clean Architecture, com Clean Code, SOLID e testes automatizados de unidade e integração, seguindo os princípios do FIRST e Clean Tests. Observação: a Clean Architecuture não foi completamente implementada, visto que os microsserviços são fortemente acomplados com o Spring, entretanto, a aplicação está bem segmentada em pacotes e responsabilidades.

# Microsserviço de gerenciamento de empresas
O objetivo deste microsserviço é gerenciar as empresas fornecedoras de um produto ou serviço para atender seus clientes. A empresa cadastrada será o ponto inicial da rota de entrega do bem para o cliente.
  * Microsserviço de Gerenciamento de empresas 
    * API: 
        * http://localhost:8082/companies 
            * Verbo POST - para realizar o cadastro.
        * http://localhost:8082/companies/{id} 
            * Verbo GET - para realizar a pesquisa de uma empresa pelo seu ID.
            * Verbo PUT - para realizar a atualização de dados de uma empresa pelo seu ID. Necessário informar request body.
            * Verbo DELETE - para realizar a exclusão (soft delete) de uma empresa pelo seu ID.
        * http://localhost:8082/companies/name-email
            * Verbo GET - para realizar a pesquisa paginada de uma empresa pelo seu nome ou email.
    * Documentação da API: http://localhost:8082/swagger-ui/index.html
    * Banco de dados: http://localhost:5432/company-db

    ![alt text](image.png)

    Testes de integração e unidade com 93% de linhas de código cobertas.

    ![alt text](image-6.png)

# Microsserviço de gerenciamento de clientes
O objetivo deste microsserviço é gerenciar os clientes consumidores de produto/serviço das empreasas fornecedoras. O cliente cadastrado será o ponto final da rota de entrega do bem para o cliente.
  * Microsserviço de Gerenciamento de clientes 
    * API: 
        * http://localhost:8083/customers
            * Verbo POST - para realizar o cadastro.
        * http://localhost:8083/customers/{id}
            * Verbo GET - para realizar a pesquisa de um cliente pelo seu ID.
            * Verbo PUT - para realizar a atualização de dados de um cliente pelo seu ID. Necessário informar request body.
            * Verbo DELETE - para realizar a exclusão (soft delete) de um cliente pelo seu ID.   
        * http://localhost:8083/customers/name-email
            * Verbo GET - para realizar a pesquisa paginada de um cliente pelo seu nome ou email.             
    * Documentação: http://localhost:8083/swagger-ui/index.html
    * Banco de dados: http://localhost:5433/customer-db

    ![alt text](image-1.png)

    Testes de integração e unidade com 93% de linhas de código cobertas.

    ![alt text](image-7.png)

# Microsserviço de gerenciamento de produtos
O objetivo deste microsserviço é gerenciar os produtos/serviços cadastrados pelo fornecedor, que por sua vez, serão incluídos em pedidos de venda.
  * Microsserviço de Gerenciamento de produtos 
    * API: 
        * http://localhost:8084/products
            * Verbo POST - para realizar o cadastro.
        * http://localhost:8084/products/{id}
            * Verbo GET - para realizar a pesquisa de um produto pelo seu ID.
            * Verbo PUT - para realizar a atualização de dados de um produto pelo seu ID. Necessário informar request body.
            * Verbo DELETE - para realizar a exclusão (soft delete) de um produto pelo seu ID.   
        * http://localhost:8084/products/sku-description
            * Verbo GET - para realizar a pesquisa paginada de um produto pelo seu sku ou descrição.
    * Documentação: http://localhost:8084/swagger-ui/index.html
    * Banco de dados: http://localhost:5434/product-db

    ![alt text](image-2.png)

    Testes de integração e unidade com 92% de linhas de código cobertas.

    ![alt text](image-8.png)

# Microsserviço de gerenciamento de pedidos
O objetivo deste microsserviço é gerenciar os pedidos cadastrados pelos clientes, que irão consumir um produto/serviço.
  * Microsserviço de Gerenciamento de pedidos 
    * API: 
        * http://localhost:8085/orders
            * Verbo POST - para realizar o cadastro.
        * http://localhost:8085/orders/{id}
            * Verbo GET - para realizar a pesquisa de um pedido pelo seu ID.
            * Verbo PUT - para realizar a atualização de dados de um pedido pelo seu ID. Necessário informar request body.
            * Verbo DELETE - para realizar a exclusão (soft delete) de um pedido pelo seu ID.   
        * http://localhost:8085/orders/{id}/payment-confirmation
            * Verbo PATCH - para informar o pagamento do pedido. Este endpoint realiza uma comunicação com o endpoint do microsserviço de logística/entrega para cadastrar uma ordem de entrega. Neste endpoint será validado se o pedido já foi pago e evitar duplicidade de pagamento.
        * http://localhost:8085/orders/{id}/awaiting-delivery
            * Verbo PATCH - para informar que o pedido está em entrega. Este endpoint será consumido pelo microsserviço de logística/entrega quando o pedido estiver em rota de entrega. Neste endpoint será validado se o pedido foi pago e se já está no status atual, evitando persistência desnecessária no banco de dados.
        * http://localhost:8085/orders/{id}/delivery-confirmation
            * Verbo PUT - para informar que o pedido foi entregue. Este endpoint será consumido pelo microsserviço de logística/entrega quando a entrega do pedido for concluída. Neste endpoint será validado se o pedido foi pago e se já está no status atual, evitando persistência desnecessária no banco de dados.
        * http://localhost:8085/orders/company-customer
            * Verbo GET - para realizar a pesquisa paginada de um pedido pela empresa ou cliente.        
    * Documentação: http://localhost:8085/swagger-ui/index.html
    * Banco de dados: http://localhost:5435/order-db

![alt text](image-3.png)

Testes de integração e unidade com 85% de linhas de código cobertas.

![alt text](image-9.png)

# Microsserviço de gerenciamento de logística/entrega
O objetivo deste microsserviço é gerenciar a logística e entrega dos pedidos cadastrados e pagos pelos clientes.
  * Microsserviço de Gerenciamento de entregas/logística 
    * API: 
        * http://localhost:8086/logistics
            * Verbo POST - para realizar o cadastro da ordem de entrega, que será gerado no pagamento do pedido. Este endpoint será consumido pelo microserviço de pedidos, no momento do pagamento do pedido.
        * http://localhost:8086/logistics/{id}
            * Verbo GET - para realizar a pesquisa de uma ordem de entrega pelo seu ID.            
        * http://localhost:8086/logistics/order-id/{id}
            * Verbo GET - para realizar a pesquisa de uma ordem de entrega pelo ID do pedido de venda.            
        * http://localhost:8086/logistics/{id}/order-is-ready-to-deliver
            * Verbo PATCH - para realizar confirmação que o pedido está pronto para ser entregue, após a análise da rota de entrega. Este endpoint consumirá o microsserviço de pedido quando o pedido estiver em rota de entrega. Neste endpoint será validado se o pedido já foi entregue e se já está no status atual, evitando persistência desnecessária no banco de dados.
        * http://localhost:8086/logistics/{id}/delivery-confirmation
            * Verbo PATCH - para realizar confirmação da entrega do pedido. Este endpoint consumirá o microsserviço de pedido quando o pedido for entregue. Neste endpoint será validado se o pedido foi entregue e se já está no status atual, evitando persistência desnecessária no banco de dados.
    * Documentação: http://localhost:8086/swagger-ui/index.html
    * Banco de dados: http://localhost:5436/logistics-db

    ![alt text](image-4.png)

    Testes de integração e unidade com 91% de linhas de código cobertas.

    ![alt text](image-10.png)

# Microsserviço de gerenciamento de importação de dados
O objetivo deste microsserviço é importar dados de produtos e CEPs de fontes externas de dados, como arquivo CSV.
  * Microsserviço de Gerenciamento de importação de dados 
    * API: 
        * http://localhost:8087/products/batch
            * Verbo POST - para realizar a importação de produtos para a base de dados do microsserviço de gerenciamento de produtos.
        * http://localhost:8087/logistics/batch
            * Verbo POST - para realizar a importação de CEPs para a base de dados do microsserviço de gerenciamento logística/entrega.
    * Documentação: http://localhost:8087/swagger-ui/index.html
    * Banco de dados: http://localhost:5437/batch-db
        * Este microsserviço possui seu próprio banco de dados para realizar o gerenciamento de importações realizadas (DER com o fundo escuro no print abaixo). 

    ![alt text](image-5.png)

# Qualidade de software
Para garantir a qualidade de software, implementamos testes de unidade e de integração na grande maioria do código. Para identificar o que foi testado, utilizamos a cobertura de testes de código do próprio IntelliJ IDEA. A decisão de utilizar o próprio IntelliJ foi motivada pela manutenção de menor número de dependências a serem adicionadas no projeto, com o objetivo de reduzir possibilidades de libs externas abrirem uma fragilidade na segurança da aplicação (lembrando do caso do Log4J) e que no cenário em que o projeto foi desenvolvido não foi necessária a adição do Jacoco.
Os testes de unidade foram implementados nas classes de domínio e application testando a menor unidade de código. Os testes de integração foram implementados nas classes de presentation, realizando a requisição REST aos endpoints em diversos cenários, testando o código por completo, da entrada dos dados, processamento e saída. O objetivo desta segregação foi considerar a eficiência dos testes versus o tempo de
entrega do projeto. Aplicando este método, foi apurado pela cobertuda de testes do IntelliJ IDEA, em mais de 90% de linhas de código testadas na maioria dos microserviços. Para realizar o teste de cobertura, clique com o botão direito do mouse sobre o nome do projeto, navegue até a opção More Run/Debug, em seguida selecione a opção Run tests in <nome do projeto> with Coverage.

