package com.portfolio.maker.controller;

import com.portfolio.maker.dto.AcercaDeDto;
import com.portfolio.maker.dto.DireccionDto;
import com.portfolio.maker.entity.AcercaDe;
import com.portfolio.maker.entity.Direccion;
import com.portfolio.maker.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // <-- Inyectado por Spring Security
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile") // Ruta protegida por JWT
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // --- Endpoints de DIRECCION (Propios) ---

    /**
     * GET /api/profile/direccion
     * Obtiene la dirección del usuario logeado.
     */
    @GetMapping("/direccion")
    public ResponseEntity<DireccionDto> getMyDireccion(Authentication authentication) {
        return ResponseEntity.ok(profileService.getDireccion(authentication));
    }

    @PutMapping("/direccion")
    public ResponseEntity<DireccionDto> updateMyDireccion(
            Authentication authentication,
            @Valid @RequestBody DireccionDto dto) {
        return ResponseEntity.ok(profileService.updateDireccion(authentication, dto));
    }

    // --- Endpoints de ACERCA DE (Devuelven DTOs) ---

    @GetMapping("/acercade")
    public ResponseEntity<AcercaDeDto> getMyAcercaDe(Authentication authentication) {
        return ResponseEntity.ok(profileService.getAcercaDe(authentication));
    }

    @PutMapping("/acercade")
    public ResponseEntity<AcercaDeDto> createOrUpdateMyAcercaDe(
            Authentication authentication,
            @Valid @RequestBody AcercaDeDto dto) {
        return ResponseEntity.ok(profileService.createOrUpdateAcercaDe(authentication, dto));
    }

    // ... (deleteAcercaDe sigue igual)
    @DeleteMapping("/acercade")
    public ResponseEntity<Void> deleteMyAcercaDe(Authentication authentication) {
        profileService.deleteAcercaDe(authentication);
        return ResponseEntity.noContent().build();
    }
}