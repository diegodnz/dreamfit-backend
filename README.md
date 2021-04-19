# Dreamfit API
### Projeto: https://github.com/diegodnz/projeto-eng-software

-----------------------------
### Plano inicial das rotas da API: https://pastebin.com/7Pupd0em
### Rotas atuais da API (Swagger): https://dreamfit-api.herokuapp.com/swagger-ui.html
-----------------------------

#### Requisitos e utilitários

| Ferramenta| Versão | Descrição                            |
|-----------|--------|--------------------------------------|
| Spring Tools Suit   | >= 4.9.0     |IDE de desenvolvimento padrão         |
| Java      | >= 1.8.0  |SDK                                   |
| Maven     | >=  3.6.3 |Gerenciador de dependências           |
| SpringBoot | >= 2.4.1  |Framework para configuração e publicação da aplicação|
| PostgreSQL | -      |Sistema de gerenciamento de banco de dados|
| Postman   | >=8.0.7 |Ambiente de Desenvolvimento|
| Git       | -      |Controle de versões|
| Windows 10    | -      |Sistema operacional|

-----------------------------
#### Configuração de ambiente para desenvolvimento

- Verifique se o Java 8 está devidamente instalado e com as variáveis de ambiente configuradas.
- Renomear o ```application.properties.example``` para ```application.properties```
- Preencher os campos do application.properties:
- spring.datasource.username (Usuário root do banco de dados), 
- spring.datasource.password (Senha do usuário root), 
- spring.mail.username (Email necessário para envio da mensagem de recuperação de senha. Este email deve permitir acesso de fontes desconhecidas), 
- spring.mail.password (Senha deste email)
- Crie o banco de dados "dreamFit" manualmente no postgres.
- Use o ```./mvnw clean install -DskipTests``` para a gestão de dependências
- Agora pode executar o projeto com ```./mvnw spring-boot:run```
- O projeto também pode ser executado a partir do kit de desenvolvimento do Spring: https://spring.io/tools
- Endpoints podem ser testados com softwares como postman e insomnia

-----------------------------
### Administrador e Usuários

Inicialmente o banco está populado, onde o super usuário é
login:```33367174777``` senha:
```12345678```

-----------------------------
### Segurança
Alguns endpoints precisam de token jwt (Obtido no endpoint /users/login) com o respectivo nível de acesso para serem acessados. Os níveis de acesso atuais da aplicação são: ADMIN, Professor e Aluno da academia
