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
