package br.com.aweb.sistema_vendas.model;

/**
 * Perfis de acesso do sistema.
 * ADMIN  -> acesso total (produtos, clientes e pedidos).
 * PADRAO -> restrito às operações de pedidos.
 */
public enum UsuarioRole {
    ADMIN,
    PADRAO
}
