# Sistema de Saúde Pública
> Projeto de Trabalho de Conclusão de Curso da Pós-Graduação em Engenharia de Software

O projeto se baseia na criação de um sistema para a gestão de saúde pública de uma cidade.

## Tecnologias

O projeto foi desenvolvido utilizando as seguintes tecnologias:

- Java 11
- Spring Boot (https://spring.io/projects/spring-boot)
- Thymeleaf (https://www.thymeleaf.org/)
- Mysql https://www.mysql.com/)
- Gradle (https://gradle.org/)

## Requisitos do Projeto

Os requisitos para se executar o projeto são os seguintes:

- Possuir Java 11 instalado.
- Possuir Mysql instalado.
- Caso necessário, acessar o arquivo application.properties dentro do projeto, e alterar as propriedades spring.datasource.username e spring.datasource.password para o valores do seus Mysql instalado.
- Após executar o projeto pela primeira vez, deve ser executado o script abaixo:
```sql
INSERT INTO saudepublica.user (id, cpf, email, name, password, type) VALUES (1, '59647181086', 'admin@admin.com', 'Admin', '$2a$10$3.rfzgEHq4z011VGx.kud.g2INDwbgICb67GPxdm8lC.jivK7DUPa', 'ADMIN');
```

## Acessando o sistema

Acesse o sistema pela URL http://localhost:8080/.

Vocês será redirecionado para a página de Login. O E-mail e Senha padrão sãp:

E-mail: admin@admin.com

Senha admin123

Após logar você será redirecionado para a página Home.

## Conhecendo o sistema

### Login


![Login](images/Login.PNG)

### Home


![Home](images/Home.PNG)

### Usuário

Listagem de usuários
![User1](images/User1.PNG)

Cadastro de usuário
![User2](images/User2.PNG)

### Estabelecimento

Listagem de estabelecimentos
![Establishment2](images/Establishment1.PNG)

Cadastro de estabelecimento
![Establishment2](images/Establishment2.PNG)

### Medicamento

Listagem de medicamentos
![Medication1](images/Medication1.PNG)

Cadastro de medicamento
![Medication2](images/Medication2.PNG)

### Entrada de Estoque

Listagem de entradas de Estoque
![StockInput1](images/StockInput1.PNG)

Cadastro de entrada de Estoque
![StockInput2](images/StockInput2.PNG)

### Saída de Estoque

Listagem de saídas de Estoque
![StockOutput1](images/StockOutput1.PNG)

Cadastro de saída de Estoque
![StockOutput2](images/StockOutput2.PNG)
