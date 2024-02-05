SPRING BOOT - PRODUCTS API

Motivo da criação da API:
  Demonstração de conhecimentos práticos em programação utilizando JAVA e criação de API's.
  A Base de dados da API foi modelada de forma simples, visando apenas demosntração de funcionamento e conceitos fundamentais ao desenvolver uma API REST utilizando o framework SpringBoot. Sendo assim estou ciente que a base de dados poderia ser otimizada criando uma relação mais complexa de tabelas, mas esse não é o foco do projeto.

Tecnologias usadas:
- JAVA 11
- SpringBoot

Conhecimentos aplicados:
- MVC
- Conceito de DTOs e respostas condizentes da API a clientes
- Padrões de Projeto
- Arquitetura REST
- Clean Code

Funcionalidades atuais da API:
- ADENDO: As funcionalidades da API funcionam apenas por USUÁRIO, não por grupo de usuários.

- Segurança e Autenticação ( validando Roles ) via SpringSecurity
- Sistema de permições a utorizações criado apartir do uso de um Bearer Token, enviado nas requisições e validado na API
- Sistema respostas apropriadas do servidor a clientes, em caso de erros exceções personalizadas são devolvidas ao cliente, deixando a resposta mais agrádavel e expondo menos informações internas da API

- Cadastro de um usuário
- Alteração de senha de um usuário
- Consulta de usuário 

- Cadastro de um cliente, que é criado apartir das informações de um usuário, formando assim, na base de dados, uma relação 1 para 1 entre cliente e usuário

- Cadastro de um produto, que é relacionado diretamente a UM usuário.
- Alteração de um produto, que é relacionado diretamente a UM usuário.
