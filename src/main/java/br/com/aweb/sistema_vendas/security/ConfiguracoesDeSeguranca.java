package br.com.aweb.sistema_vendas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {

    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {
        http
            // Autorização por URL: das regras mais específicas para as mais genéricas.
            // (a primeira regra que casar é a aplicada)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/error", "/css/**", "/js/**").permitAll()
                .requestMatchers("/produtos/**").hasRole("ADMIN")
                .requestMatchers("/clientes/**").hasRole("ADMIN")
                .requestMatchers("/pedidos/**").hasAnyRole("ADMIN", "PADRAO")
                .anyRequest().authenticated()
            )
            // Login personalizado: GET /login -> LoginController; POST /login -> Spring Security
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/pedidos")
                .permitAll()
            )
            // Logout: GET /logout mostra logout.html; o POST /logout é tratado pelo Spring
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            )
            // Usuário logado sem permissão para a URL (ex.: PADRAO em /produtos)
            .exceptionHandling(ex -> ex.accessDeniedPage("/acesso-negado"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
