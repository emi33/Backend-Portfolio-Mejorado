package com.portfolio.maker.controller;

import com.portfolio.maker.dto.PublicProfileDto;
import com.portfolio.maker.service.PublicProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public") // La ruta que hicimos pública
@RequiredArgsConstructor
public class PublicProfileController {

    private final PublicProfileService publicProfileService;

    /**
     * Endpoint público para que CUALQUIERA vea un perfil por ID.
     * No requiere token.
     */
    @GetMapping("/profile/{personaId}")
    public ResponseEntity<PublicProfileDto> getPublicProfile(
            @PathVariable Integer personaId
    ) {
        return ResponseEntity.ok(publicProfileService.getPublicProfile(personaId));
    }
}