# Sistema de Vendas

Spring Boot 4 + Spring Security 7 + Thymeleaf + PostgreSQL.

## Usuários de teste (criados automaticamente na 1ª inicialização)

| Perfil | E-mail                    | Senha       |
|--------|---------------------------|-------------|
| ADMIN  | admin@sistemavendas.com   | `Admin@123` |
| PADRAO | padrao@sistemavendas.com  | `Padrao@123`|

As senhas são gravadas com hash BCrypt. Para trocar e-mail/senha sem alterar código, defina as
variáveis `SEED_ADMIN_EMAIL`, `SEED_ADMIN_SENHA`, `SEED_PADRAO_EMAIL` e `SEED_PADRAO_SENHA`
**antes da primeira execução** (usuários já existentes não são sobrescritos).

## Permissões

| Área                | ADMIN | PADRAO |
|---------------------|:-----:|:------:|
| `/pedidos/**`       |  ✔   |   ✔   |
| `/clientes/**`      |  ✔   |   ✘   |
| `/produtos/**`      |  ✔   |   ✘   |

Dois níveis: visual (`sec:authorize` nas páginas) e por URL (`requestMatchers` em
`security/ConfiguracoesDeSeguranca.java`). PADRAO que acessa `/clientes` ou `/produtos` pela URL vê
a página "Acesso negado" (HTTP 403).

## Rodar localmente

```bash
docker compose up -d          # PostgreSQL em localhost:5433
./mvnw spring-boot:run        # http://localhost:8080
```

## Deploy no Render

Siga `DEPLOY-RENDER.pdf`. Resumo das variáveis do Web Service:

| Key                 | Value                                                        |
|---------------------|--------------------------------------------------------------|
| `DB_URL`            | `jdbc:postgresql://<HOSTNAME_INTERNO>:5432/<NOME_DO_BANCO>`  |
| `DB_USER`           | usuário do banco                                             |
| `DB_PASSWORD`       | senha do banco                                               |
| `JAVA_TOOL_OPTIONS` | `-Xmx400m`                                                   |

Banco e Web Service na **mesma região** (use o hostname *interno* do banco, o que termina em `-a`).
Recomendado: defina também `SEED_ADMIN_SENHA` e `SEED_PADRAO_SENHA` no Render para que o ambiente
público não use as senhas de teste acima.
