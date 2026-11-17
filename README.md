# Loja Web

Projeto da disciplina de Programação para Web I. É uma loja simples feita com Servlets, JSP, JSTL e PostgreSQL.

Os produtos ficam no banco de dados. O carrinho usa a sessão HTTP para guardar seu identificador, enquanto os itens e as quantidades são salvos no PostgreSQL.

## Rodando no Codespaces

O projeto já tem uma configuração em `.devcontainer/` com Java, Maven e PostgreSQL.

Depois de abrir o repositório em um Codespace, rode no terminal:

```bash
mvn jetty:run
```

Abra a porta `8080` na aba **Ports**. Na primeira inicialização, o arquivo `database/init.sql` cria as tabelas e insere os produtos de exemplo.

## Rodando localmente

É preciso ter Java 21, Maven e PostgreSQL instalados.

Crie o banco `loja` e execute o script inicial:

```bash
psql -U loja -d loja -f database/init.sql
```

A aplicação usa estas configurações por padrão:

```text
DB_URL=jdbc:postgresql://localhost:5432/loja
DB_USER=loja
DB_PASSWORD=loja
```

Se necessário, elas podem ser alteradas por variáveis de ambiente. Para iniciar a aplicação:

```bash
mvn jetty:run
```

Para gerar o arquivo WAR:

```bash
mvn package
```

O arquivo gerado fica em `target/loja-web-sessao.war` e pode ser usado em um servidor compatível com Servlet 4, como o Tomcat 9.

## Endpoints

| Método | Caminho | Ação |
| --- | --- | --- |
| GET | `/produtos` | Lista os produtos |
| GET | `/carrinho` | Mostra o carrinho da sessão |
| POST | `/carrinho/itens` | Adiciona um produto usando o parâmetro `id` |
| DELETE | `/carrinho/itens?id={id}` | Remove um produto |
| DELETE | `/carrinho` | Remove todos os itens |

As JSPs usam EL/JSTL e não possuem scriptlets.
