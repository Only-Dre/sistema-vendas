package br.com.aweb.sistema_vendas.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.aweb.sistema_vendas.model.Usuario;
import br.com.aweb.sistema_vendas.model.UsuarioRole;
import br.com.aweb.sistema_vendas.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Chamado pelo Spring Security no POST /login; o "username" é o e-mail.
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email).isPresent();
    }

    // Único ponto de gravação de usuários: a senha SEMPRE é gravada com hash.
    @Transactional
    public Usuario cadastrar(String nome, String email, String senhaPura, UsuarioRole role) {
        if (existePorEmail(email)) {
            throw new IllegalArgumentException("Já existe um usuário com o e-mail " + email);
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email.trim().toLowerCase());
        usuario.setSenha(passwordEncoder.encode(senhaPura));
        usuario.setRole(role);
        return usuarioRepository.save(usuario);
    }
}
