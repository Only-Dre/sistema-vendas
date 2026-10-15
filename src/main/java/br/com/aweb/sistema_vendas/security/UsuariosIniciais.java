package br.com.aweb.sistema_vendas.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.aweb.sistema_vendas.model.UsuarioRole;
import br.com.aweb.sistema_vendas.service.UsuarioService;

/**
 * Cadastra os usuários de teste (um ADMIN e um PADRAO) na inicialização,
 * apenas se ainda não existirem. As senhas são gravadas com hash BCrypt.
 */
@Component
public class UsuariosIniciais implements CommandLineRunner {

    private final UsuarioService usuarioService;

    @Value("${app.seed.admin.nome}")
    private String adminNome;
    @Value("${app.seed.admin.email}")
    private String adminEmail;
    @Value("${app.seed.admin.senha}")
    private String adminSenha;

    @Value("${app.seed.padrao.nome}")
    private String padraoNome;
    @Value("${app.seed.padrao.email}")
    private String padraoEmail;
    @Value("${app.seed.padrao.senha}")
    private String padraoSenha;

    public UsuariosIniciais(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        criarSeNaoExistir(adminNome, adminEmail, adminSenha, UsuarioRole.ADMIN);
        criarSeNaoExistir(padraoNome, padraoEmail, padraoSenha, UsuarioRole.PADRAO);
    }

    private void criarSeNaoExistir(String nome, String email, String senha, UsuarioRole role) {
        if (!usuarioService.existePorEmail(email)) {
            usuarioService.cadastrar(nome, email, senha, role);
        }
    }
}
