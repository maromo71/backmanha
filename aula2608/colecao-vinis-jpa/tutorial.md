# Tutorial — Spring Boot + JPA + PostgreSQL + Supabase

## Objetivo

Ao final deste tutorial, o aluno terá uma aplicação:

```text
Spring Boot
     │
     ├── Spring Data JPA
     │
     ├── Hibernate
     │
     └── PostgreSQL Driver
             │
             ▼
      Supabase PostgreSQL
             │
             ▼
        Tabelas do banco
```

A ideia é que o aluno **não precise criar manualmente as tabelas**. As classes `@Entity` serão utilizadas pelo Hibernate para criar/atualizar a estrutura do banco.

---

# 1. Pré-requisitos

Antes de começar, o aluno deve ter:

* Java instalado;
* IntelliJ IDEA, Eclipse ou VS Code;
* Maven;
* projeto Spring Boot;
* Spring Data JPA;
* uma conta no Supabase.

Neste exemplo utilizaremos:

```text
Java 21
Spring Boot
Spring Data JPA
Hibernate
PostgreSQL
Supabase
Maven
```

---

# 2. Criar o projeto Spring Boot

O projeto pode ser criado pelo Spring Initializr.

Selecione:

### Project

```text
Maven
```

### Language

```text
Java
```

### Dependencies

Adicione:

```text
Spring Web
Spring Data JPA
PostgreSQL Driver
```

O `Spring Data JPA` fornece a infraestrutura para trabalhar com JPA e Hibernate. O starter JPA inclui Hibernate, Spring Data JPA e integração ORM do Spring. ([Home][2])

---

# 3. Configurar o `pom.xml`

Abra:

```text
pom.xml
```

Procure as dependências do banco.

Se o projeto estava utilizando H2, provavelmente haverá algo semelhante a:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Para utilizar PostgreSQL, remova o H2:

```xml
<!-- REMOVER -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

E adicione:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

O resultado deverá conter:

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- outras dependências -->

</dependencies>
```

### Atenção

Não é necessário escolher manualmente uma versão do PostgreSQL Driver se o projeto estiver utilizando o gerenciamento de dependências do Spring Boot.

Ou seja, normalmente basta:

```xml
<groupId>org.postgresql</groupId>
<artifactId>postgresql</artifactId>
```

---

# 4. Atualizar as dependências Maven

Depois de alterar o `pom.xml`, execute:

```powershell
mvn clean install
```

Ou, no IntelliJ, clique em:

```text
Maven → Reload All Maven Projects
```

Também podemos verificar:

```powershell
mvn dependency:tree
```

O aluno deverá encontrar algo semelhante a:

```text
org.postgresql:postgresql
```

---

# 5. Criar uma conta no Supabase

Acesse:

[Supabase](https://supabase.com/?utm_source=chatgpt.com)

Crie uma conta ou faça login.

Depois:

```text
New project
```

Preencha:

```text
Project name: colecao-vinis
Database Password: ********
Region: escolha a região adequada
```

### MUITO IMPORTANTE

A senha criada nessa etapa é a **senha do banco PostgreSQL**.

Ela será utilizada posteriormente pelo Spring Boot.

**Não confunda:**

```text
senha do usuário do Supabase
```

com:

```text
senha do banco PostgreSQL
```

---

# 6. Aguardar a criação do banco

Depois que o projeto for criado, o Supabase disponibilizará um PostgreSQL.

O aluno **não precisa criar outro banco PostgreSQL**.

O projeto Supabase já possui um banco PostgreSQL.

---

# 7. Localizar os dados de conexão

Dentro do projeto Supabase, procure:

```text
Connect
```

Depois procure:

```text
Session pooler
```

O Supabase atualmente recomenda o **Session Pooler na porta 5432** para aplicações persistentes e ORMs como Hibernate/JPA. ([Supabase][1])

A conexão terá uma estrutura semelhante a:

```text
postgresql://postgres.PROJECT_REF:SENHA@aws-0-REGION.pooler.supabase.com:5432/postgres
```

### Exemplo didático

```text
Host:
aws-0-sa-east-1.pooler.supabase.com

Port:
5432

Database:
postgres

User:
postgres.PROJECT_REF

Password:
SUA_SENHA
```

**Não copie os valores deste exemplo para o seu projeto.**

Cada projeto Supabase possui seus próprios dados.

---

# 8. Por que usar a porta 5432?

Esse ponto é importante para os alunos.

O Supabase possui diferentes formas de conexão.

### Session Pooler

```text
5432
```

É apropriado para aplicações persistentes e suporta recursos de sessão utilizados por ORMs.

### Transaction Pooler

```text
6543
```

É destinado principalmente a cenários de conexões curtas, como ambientes serverless.

O Supabase alerta especificamente para **não usar o Transaction Pooler 6543 como fonte principal de dados para aplicações ORM que dependem de prepared statements**. ([Supabase][1])

Portanto, para nosso projeto didático:

```text
Spring Boot + JPA + Hibernate
              ↓
      Session Pooler
              ↓
            5432
```

---

# 9. Configurar o `application.properties`

Abra:

```text
src/main/resources/application.properties
```

Coloque:

```properties
spring.application.name=colecao-vinis-jpa

spring.datasource.url=jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres?sslmode=require

spring.datasource.username=postgres.PROJECT_REF

spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true
```

### Atenção ao JDBC

Um erro bastante comum é colocar:

```text
postgresql://...
```

quando o Spring Boot espera uma URL JDBC.

❌ Errado:

```properties
spring.datasource.url=postgresql://...
```

✅ Correto:

```properties
spring.datasource.url=jdbc:postgresql://...
```

O prefixo:

```text
jdbc:
```

é obrigatório para essa configuração.

---

# 10. Por que utilizamos `sslmode=require`?

Nossa URL contém:

```text
?sslmode=require
```

Isso determina que a conexão PostgreSQL deverá utilizar SSL.

Portanto:

```properties
spring.datasource.url=jdbc:postgresql://HOST:5432/postgres?sslmode=require
```

O Supabase recomenda utilizar SSL para proteger a comunicação com o banco. ([Supabase][3])

---

# 11. Configurar o Hibernate

A propriedade:

```properties
spring.jpa.hibernate.ddl-auto=update
```

é muito importante.

Ela informa ao Hibernate para:

```text
analisar as entidades
        ↓
comparar com o banco
        ↓
criar/atualizar estrutura
```

Por exemplo:

```java
@Entity
public class Vinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String artista;

    private Integer ano;
}
```

O Hibernate poderá criar uma tabela correspondente no PostgreSQL.

O Spring Boot suporta os modos:

```text
none
validate
update
create
create-drop
```

e `update` atualiza o schema quando necessário. ([Home][4])

---

# 12. Por que não utilizar `create-drop`?

Para nosso projeto didático, queremos:

```properties
spring.jpa.hibernate.ddl-auto=update
```

e não:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

Porque:

```text
create-drop
```

pode criar a estrutura quando a aplicação inicia e eliminá-la quando a aplicação termina.

Para um banco remoto no Supabase, isso seria especialmente perigoso.

---

# 13. Criar a primeira Entity

Vamos testar a conexão.

Crie:

```text
Vinho.java
```

Exemplo:

```java
package com.exemplo.colecaovinis.model;

import jakarta.persistence.*;

@Entity
public class Vinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String artista;

    private Integer ano;

    public Vinho() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
```

O ponto fundamental é:

```java
@Entity
```

Essa anotação informa ao JPA que a classe representa uma entidade persistente.

O Spring Boot realiza automaticamente o entity scanning das classes anotadas com `@Entity`. ([Home][2])

---

# 14. Criar o Repository

Agora:

```java
package com.exemplo.colecaovinis.repository;

import com.exemplo.colecaovinis.model.Vinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VinhoRepository
        extends JpaRepository<Vinho, Long> {
}
```

Não precisamos implementar manualmente:

```text
INSERT
SELECT
UPDATE
DELETE
```

O Spring Data JPA fornece essas operações através do `JpaRepository`.

---

# 15. Executar a aplicação

Execute:

```text
ColecaoVinisJpaApplication
```

No console, procure mensagens relacionadas ao:

```text
HikariPool
Hibernate
PostgreSQL
```

Se a conexão estiver correta, o Hibernate poderá executar comandos SQL.

Com:

```properties
spring.jpa.show-sql=true
```

podemos visualizar os SQLs no console.

---

# 16. Verificar o Supabase

Volte para o Supabase.

Entre em:

```text
Table Editor
```

A tabela correspondente à entidade deverá aparecer.

Por exemplo:

```text
vinho
```

ou o nome determinado pela estratégia de nomenclatura utilizada pelo projeto.

Teremos então:

```text
Java
  ↓
@Entity
  ↓
JPA
  ↓
Hibernate
  ↓
JDBC
  ↓
PostgreSQL Driver
  ↓
Supabase
  ↓
Tabela
```

---

# 17. Testar inserção de dados

Podemos testar através de um `CommandLineRunner`:

```java
@Bean
CommandLineRunner executar(VinhoRepository repository) {
    return args -> {

        Vinho vinho = new Vinho();

        vinho.setNome("The Dark Side of the Moon");
        vinho.setArtista("Pink Floyd");
        vinho.setAno(1973);

        repository.save(vinho);
    };
}
```

Quando a aplicação iniciar, o Hibernate deverá executar um `INSERT`.

No console poderemos observar algo semelhante a:

```sql
insert into vinho
    (ano, artista, nome)
values
    (?, ?, ?)
```

Depois podemos verificar o registro diretamente no:

```text
Supabase
   ↓
Table Editor
   ↓
vinho
```

---

# 18. Teste de conexão antes do Spring Boot

Uma boa prática para os alunos é separar os problemas.

Primeiro podemos verificar se o computador alcança o Supabase.

No Windows PowerShell:

```powershell
Test-NetConnection HOST -Port 5432
```

Por exemplo:

```powershell
Test-NetConnection aws-0-sa-east-1.pooler.supabase.com -Port 5432
```

Se aparecer:

```text
TcpTestSucceeded : True
```

a porta está acessível.

---

# 19. Erros comuns

## Erro 1 — URL sem `jdbc`

❌

```properties
spring.datasource.url=postgresql://...
```

✅

```properties
spring.datasource.url=jdbc:postgresql://...
```

---

## Erro 2 — Usar a porta 6543

Para nosso projeto JPA:

❌

```text
6543
```

Prefira:

✅

```text
5432
```

O próprio quickstart atual do Supabase para Spring Boot orienta usar Session Pooler e não o Transaction Pooler como fonte principal da aplicação. ([Supabase][1])

---

## Erro 3 — Usuário incorreto

No Session Pooler, o usuário normalmente possui o formato:

```text
postgres.PROJECT_REF
```

e não simplesmente:

```text
postgres
```

O Supabase documenta essa diferença entre conexão direta e Session Pooler. ([Supabase][3])

---

## Erro 4 — Esquecer o SSL

Use:

```properties
?sslmode=require
```

no final da URL.

---

## Erro 5 — Esquecer o PostgreSQL Driver

O `pom.xml` precisa conter:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## Erro 6 — Hibernate não encontra o banco

Um erro como:

```text
Unable to determine Dialect without JDBC metadata
```

pode parecer um problema de dialect.

Mas frequentemente a causa está **antes** no log: o Hibernate não conseguiu abrir a conexão JDBC. A própria documentação do Supabase recomenda procurar a causa anterior no log, como problemas de autenticação. ([Supabase][1])

Portanto, o aluno deve procurar **a primeira exceção do log**, e não necessariamente a última.

---

# 20. Não colocar a senha no GitHub

Durante uma aula podemos usar:

```properties
spring.datasource.password=SUA_SENHA
```

mas, em um projeto real, não devemos versionar a senha.

O Supabase recomenda utilizar variável de ambiente para a URL/credenciais em vez de colocar a conexão completa no `application.properties`, que normalmente é versionado junto com o projeto. ([Supabase][1])

Por exemplo:

```properties
spring.datasource.url=${SUPABASE_DB_URL}
```

e configurar:

```text
SUPABASE_DB_URL
```

no ambiente de execução.

---

# 21. Configuração final para a aula

Para o projeto didático, o aluno deverá chegar a algo semelhante a:

### `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### `application.properties`

```properties
spring.application.name=colecao-vinis-jpa

spring.datasource.url=jdbc:postgresql://HOST_SUPABASE:5432/postgres?sslmode=require
spring.datasource.username=postgres.PROJECT_REF
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Entity

```java
@Entity
public class Vinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String artista;
    private Integer ano;
}
```

---

# 22. Checklist do aluno

Antes de dizer **"meu projeto está conectado ao PostgreSQL"**, confira:

| Item | Verificação                                |
| ---- | ------------------------------------------ |
| 1    | Projeto Spring Boot criado                 |
| 2    | Spring Data JPA instalado                  |
| 3    | Dependência H2 removida, se existia        |
| 4    | PostgreSQL Driver adicionado               |
| 5    | `mvn clean install` executado              |
| 6    | Projeto Supabase criado                    |
| 7    | Senha do PostgreSQL conhecida              |
| 8    | **Session Pooler** selecionado             |
| 9    | Porta **5432** utilizada                   |
| 10   | Usuário `postgres.PROJECT_REF` configurado |
| 11   | URL começa com `jdbc:postgresql://`        |
| 12   | `sslmode=require` configurado              |
| 13   | `ddl-auto=update` configurado              |
| 14   | Pelo menos uma classe `@Entity` criada     |
| 15   | Aplicação iniciada sem erro                |
| 16   | Tabela verificada no Supabase              |
| 17   | Registro testado no banco                  |

---

## Resultado esperado

Ao final da atividade, o aluno terá aprendido uma arquitetura bastante importante:

```text
              APLICAÇÃO JAVA
                    │
                    ▼
             ┌─────────────┐
             │ Spring Boot │
             └──────┬──────┘
                    │
                    ▼
             ┌─────────────┐
             │ Spring Data │
             │     JPA     │
             └──────┬──────┘
                    │
                    ▼
             ┌─────────────┐
             │  Hibernate  │
             └──────┬──────┘
                    │
                    ▼
             ┌─────────────┐
             │ JDBC Driver │
             │ PostgreSQL  │
             └──────┬──────┘
                    │
              SSL / 5432
                    │
                    ▼
             ┌─────────────┐
             │  Supabase   │
             │ PostgreSQL  │
             └──────┬──────┘
                    │
                    ▼
              ┌───────────┐
              │  Tabelas  │
              └───────────┘
```

### Referências oficiais

* [Supabase — Use Supabase with Spring Boot](https://supabase.com/docs/guides/getting-started/quickstarts/spring-boot?utm_source=chatgpt.com)
* [Supabase — Connect to your database](https://supabase.com/docs/guides/database/connecting-to-postgres?utm_source=chatgpt.com)
* [Spring Boot — SQL Databases / JPA](https://docs.spring.io/spring-boot/reference/data/sql.html?utm_source=chatgpt.com)
* [Spring Boot — Database Initialization](https://docs.spring.io/spring-boot/how-to/data-initialization.html?utm_source=chatgpt.com)

**Sugestão didática:** esse material já pode ser transformado em uma aula prática de laboratório, dividindo a atividade em três etapas: **① migrar H2 → PostgreSQL, ② criar e configurar o projeto Supabase, ③ criar uma `@Entity` e comprovar que o Hibernate criou a tabela no Supabase.**

[1]: https://supabase.com/docs/guides/getting-started/quickstarts/spring-boot?utm_source=chatgpt.com "Use Supabase with Spring Boot | Supabase Docs"
[2]: https://docs.spring.io/spring-boot/reference/data/sql.html?utm_source=chatgpt.com "SQL Databases :: Spring Boot"
[3]: https://supabase.com/docs/guides/database/connecting-to-postgres?utm_source=chatgpt.com "Connect to your database | Supabase Docs"
[4]: https://docs.spring.io/spring-boot/how-to/data-initialization.html?utm_source=chatgpt.com "Database Initialization :: Spring Boot"
