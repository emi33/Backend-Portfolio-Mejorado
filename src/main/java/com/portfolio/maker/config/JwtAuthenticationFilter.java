package com.portfolio.maker.config;

import com.portfolio.maker.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // El bean de ApplicationConfig

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Obtener el encabezado "Authorization"
        final String authHeader = request.getHeader("Authorization");

        // 2. Si no existe o no empieza con "Bearer ", pasamos al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token (quitando el "Bearer ")
        final String jwt = authHeader.substring(7);
        final String userEmail;

        try {
            // 4. Extraer el email del token
            userEmail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Si el token es inválido (expirado, malformado), no autenticamos
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Si tenemos email y el usuario AÚN NO está autenticado en el contexto
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Cargar los detalles del usuario desde la BD
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 7. Validar el token contra los detalles del usuario
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 8. Si es válido, crear la autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No se necesitan credenciales (password)
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 9. Establecer la autenticación en el Contexto de Seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 10. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
