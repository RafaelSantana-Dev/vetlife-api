package com.vetlife.api.shared.config;

import com.vetlife.api.modules.auth.TokenService;
import com.vetlife.api.modules.auth.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("--- [SECURITY FILTER] Iniciando verificação para: " + request.getRequestURI());
        
        var token = this.recoverToken(request);
        
        if (token != null) {
            System.out.println("--- [SECURITY FILTER] Token encontrado! Tentando validar...");
            try {
                var login = tokenService.validateToken(token);
                System.out.println("--- [SECURITY FILTER] Token válido! Login no token: " + login);
                
                if (login != null && !login.isEmpty()) {
                    UserDetails user = userRepository.findByLogin(login);
                    
                    if (user != null) {
                        System.out.println("--- [SECURITY FILTER] Usuário encontrado no banco: " + user.getUsername());
                        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("--- [SECURITY FILTER] Autenticação realizada com SUCESSO!");
                    } else {
                        System.out.println("--- [SECURITY FILTER] ERRO: Usuário não encontrado no banco de dados.");
                    }
                }
            } catch (Exception e) {
                System.out.println("--- [SECURITY FILTER] ERRO na validação do token: " + e.getMessage());
            }
        } else {
            System.out.println("--- [SECURITY FILTER] Nenhum token encontrado no Header Authorization.");
        }
        
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "").trim();
    }
}