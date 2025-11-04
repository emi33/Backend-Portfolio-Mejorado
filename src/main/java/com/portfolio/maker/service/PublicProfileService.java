package com.portfolio.maker.service;

import com.portfolio.maker.dto.PublicProfileDto;
import com.portfolio.maker.entity.Persona;
import com.portfolio.maker.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PublicProfileService {

    private final PersonaRepository personaRepository;

    @Transactional(readOnly = true)
    public PublicProfileDto getPublicProfile(Integer personaId) {

        // 1. Encontrar la Persona por su ID
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado"));

        // 2. Usar @Transactional para cargar las relaciones LAZY
        // y construir el DTO de forma segura.
        PublicProfileDto.PublicProfileDtoBuilder builder = PublicProfileDto.builder()
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .imagenPerfil(persona.getImagenPerfil())
                .ocupacion(persona.getOcupacion())
                .imagenBanner(persona.getImagenBanner());

        // 3. Cargar 'AcercaDe' (si existe)
        if (persona.getAcercade() != null) {
            builder.acercaDeTitulo(persona.getAcercade().getTitulo())
                    .acercaDeDescripcion(persona.getAcercade().getDescripcion());
        }

        // 4. Cargar 'Direccion' (si existe)
        if (persona.getDireccion() != null) {
            builder.pais(persona.getDireccion().getPais())
                    .provincia(persona.getDireccion().getProvincia())
                    .localidad(persona.getDireccion().getLocalidad());
        }

        return builder.build();
    }
}