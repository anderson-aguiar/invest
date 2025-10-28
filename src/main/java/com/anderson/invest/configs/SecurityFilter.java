package com.anderson.invest.configs;

import com.anderson.invest.repositories.UserRepository;
import com.anderson.invest.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        if (token != null) {
            // Valida o token e extrai o subject (email do usuário).
            String login = tokenService.validateToken(token);

            // Se o login não for vazio, o token é válido.
            if (!login.isEmpty()) {
                // Busca o UserDetails (User) no banco de dados.
                UserDetails user = userRepository.findByEmail(login);

                // Cria o objeto de autenticação.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                // Define o usuário autenticado no contexto do Spring Security.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continua a cadeia de filtros.
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
