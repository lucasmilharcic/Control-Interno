package com.shastino.controlProductos.config;

import com.shastino.controlProductos.repository.UserRepository;
import com.shastino.controlProductos.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si no viene Authorization o no tiene "Bearer " sigo sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        // Extraigo username de forma segura (el método devuelve null si el token no es parseable)
        username = jwtService.extractUsernameSafe(jwt);

        // Si tengo username y no hay auth en contexto -> intento autenticar
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userOpt = userRepository.findByUsername(username);

            // Validar que el token sea válido y que coincida con el usuario en BD
            if (userOpt.isPresent() && jwtService.isTokenValid(jwt, username)) {
                // No tenés roles, así que dejamos lista vacía de authorities
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userOpt.get(), // principal -> ENTIDAD user
                        null,
                        Collections.emptyList()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            // si no es válido: no autentica y sigue el chain (no lanzamos excepción)
        }

        filterChain.doFilter(request, response);
    }
}
