# Dreamfit API
## Projeto: https://github.com/diegodnz/projeto-eng-software

-----------------------------
## Plano inicial das rotas da API: https://pastebin.com/7Pupd0em
## Rotas atuais da API (Swagger): --
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
#### Configuração de ambiente

- Verifique se o Java 8 está devidamente instalado e com as variáveis de ambiente configuradas.
- Configurar o application properties para o perfil desejado em seu projeto, conforme descrita no ```application.example.properties```
- Crie o banco de dados "dreamFit" manualmente no postgres.
- Use o ```./mvnw clean install -DskipTests``` para a gestão de dependências
- Agora é executar o projeto com ```./mvnw spring-boot:run```

-----------------------------
### Administrador e Usuários

Inicialmente o banco está populado, onde o super usuário é
login:```33367174777``` senha:
```12345678```

-----------------------------
### Segurança
Alguns endpoints precisam de token jwt (Obtido no login) com o respectivo nível de acesso para serem acessados. Os níveis de acesso atuais da aplicação são: ADMIN, Professor e Aluno da academia
