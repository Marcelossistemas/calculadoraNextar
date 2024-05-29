# CalculadoraNextar

CalculadoraNextar é um projeto Spring Boot que implementa uma calculadora REST para realizar cálculos com base em expressões matemáticas fornecidas via API. O projeto armazena as expressões e os resultados em um banco de dados H2 e reutiliza resultados já calculados para melhorar a eficiência.

## Funcionalidades

- Avaliação de expressões matemáticas
- Armazenamento de expressões e resultados no banco de dados H2
- Reutilização de resultados já calculados
- Documentação da API com Swagger

## Requisitos

- Java 17 ou superior
- Maven

## Configuração

### Clonando o repositório

```bash
https://github.com/Marcelossistemas/calculadoraNextar.git
cd CalculadoraNextar


# Documentação da API
http://localhost:8081/swagger-ui.html


# Banco de Dados
http://localhost:8081/h2-console

# Credenciais de Acesso ao H2
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password

## Javadoc

O projeto contém uma pasta `doc` que inclui a documentação Javadoc gerada. Para visualizar a documentação, abra o arquivo `index.html` na pasta `doc` em seu navegador.

-- doc/index.html

