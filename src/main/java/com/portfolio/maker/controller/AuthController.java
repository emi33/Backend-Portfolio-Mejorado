package com.portfolio.maker.controller;

import com.portfolio.maker.dto.auth.AuthResponse;
import com.portfolio.maker.dto.auth.LoginRequest;
import com.portfolio.maker.dto.auth.RegisterRequest;
import com.portfolio.maker.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Ruta base para autenticación
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint de Registro.
     * Devuelve 201 Created si es exitoso.
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register( // <-- CAMBIO: Devuelve ResponseEntity<Void>
                                          @Valid @RequestBody RegisterRequest request
    ) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Devuelve 201
    }

    /**
     * Endpoint de Login.
     * Devuelve 200 OK con el token si es exitoso.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request // <-- NUEVO DTO
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody AuthResponse request) {
        // Asumimos que el frontend envía el refresh token en el campo 'refreshToken' del DTO
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        // Pasamos el request completo para poder extraer el token
        authService.logout(request);
        return ResponseEntity.ok().build();
    }
}