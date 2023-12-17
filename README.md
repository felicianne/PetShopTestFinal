# PetShop Virtual API

Este é um projeto de API para um PetShop virtual, construído com Java 17, utilizando banco de dados H2 para desenvolvimento e Postgre em produção. A aplicação é baseada no framework Spring Boot, com suporte para autenticação e autorização fornecido pelo Spring Security.

## Participantes
Camila Cerqueira | Carol Thiengo | Felicianne Nogueira

## Especificações

- Java 17
- Banco de dados H2 (desenvolvimento) e Postgre (produção)
- Spring Boot
- Spring Security

## Estrutura do Projeto

O projeto está organizado da seguinte maneira:

- `src/test/resources/feature`: Contém os arquivos de feature escritos em Gherkin para testes de comportamento.
- `src/test/java`: Contém os arquivos de código-fonte Java para os passos de teste e configurações.

## Cenários de Teste

Os seguintes cenários de teste foram implementados utilizando a abordagem BDD (Behavior-Driven Development) com Cucumber:

### Cenário 1: Cliente não registrado
```gherkin
Scenario: Customer is not registered
    Given customer is unknown
    When customer is registered with success
    Then customer is known


### Cenário 2: Cliente sem email
```gherkin
Scenario: Customer without email
    Given customer without email
    When customer failed to register
    Then notify email must be not null
    And customer is still unknown



### Testes end-to-end
📌 Link do repositório: [https://github.com/felicianne/petshoptestfinal-test]
