package com.portfolio.maker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // <-- Importar
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/persona") // Esta ruta NO es pública
public class PersonaController {

    /**
     * Endpoint de prueba para obtener los datos del usuario autenticado.
     */
    @GetMapping("/datos")
    public ResponseEntity<String> getPersonaDatos(Authentication authentication) {
        // authentication.getName() contendrá el email del usuario gracias al JWT
        String userEmail = authentication.getName();
        return ResponseEntity.ok("Hola " + userEmail + ", estos son tus datos protegidos.");
    }
}