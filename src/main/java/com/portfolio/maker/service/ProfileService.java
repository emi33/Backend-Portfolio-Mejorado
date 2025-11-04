package com.portfolio.maker.service;

import com.portfolio.maker.dto.AcercaDeDto;
import com.portfolio.maker.dto.DireccionDto;
import com.portfolio.maker.entity.AcercaDe;
import com.portfolio.maker.entity.Direccion;
import com.portfolio.maker.entity.Persona;
import com.portfolio.maker.entity.Usuario;
import com.portfolio.maker.repository.AcercaDeRepository;
import com.portfolio.maker.repository.DireccionRepository;
import com.portfolio.maker.repository.PersonaRepository;
import com.portfolio.maker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final DireccionRepository direccionRepository;
    private final AcercaDeRepository acercaDeRepository;

    // --- NÚCLEO DE SEGURIDAD ---
    /**
     * Obtiene la entidad Persona del usuario actualmente autenticado
     * a partir del token JWT.
     */
    // --- NÚCLEO DE SEGURIDAD (ACTUALIZADO) ---
    private Persona getPersonaFromAuth(Authentication authentication) {
        String userEmail = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado"));

        Integer personaId = usuario.getPersona().getIdPersona();

        // ¡CAMBIO! Usamos el nuevo método con "fetch join"
        return personaRepository.findByIdWithDetails(personaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Datos de persona no encontrados"));
    }

    // --- CRUD DE DIRECCION ---

    @Transactional(readOnly = true)
    public DireccionDto getDireccion(Authentication authentication) {
        // Esto ya no dará error ByteBuddy porque la entidad fue cargada
        Persona persona = getPersonaFromAuth(authentication);
        if (persona.getDireccion() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha asignado una dirección.");
        }
        Direccion direccion = persona.getDireccion();

        // 2. Mapear la entidad a un DTO
        //    Esto "desconecta" el objeto de Hibernate y resuelve el error
        DireccionDto dto = new DireccionDto();
        dto.setPais(direccion.getPais());
        dto.setProvincia(direccion.getProvincia());
        dto.setLocalidad(direccion.getLocalidad());
        dto.setDireccion(direccion.getDireccion());
        dto.setNumero(direccion.getNumero());
        dto.setPiso(direccion.getPiso());
        dto.setDepto(direccion.getDepto());

        return dto; // 3. Devolver el DTO
    }

    @Transactional
    public DireccionDto updateDireccion(Authentication authentication, DireccionDto dto) {
        Persona persona = getPersonaFromAuth(authentication); // ¡Obtiene la persona CON la dirección!

        Direccion direccion = persona.getDireccion(); // ¡Ya no será null!

        // El bloque "if (direccion == null)" ya no se ejecutará
        if (direccion == null) {
            // Este bloque solo se ejecutaría si el registro falló,
            // pero lo dejamos por seguridad.
            direccion = new Direccion();
            persona.setDireccion(direccion); // Importante vincular
        }

        // Actualizamos los campos en la entidad existente
        direccion.setPais(dto.getPais());
        direccion.setProvincia(dto.getProvincia());
        direccion.setLocalidad(dto.getLocalidad());
        direccion.setDireccion(dto.getDireccion());
        direccion.setNumero(dto.getNumero());
        direccion.setPiso(dto.getPiso());
        direccion.setDepto(dto.getDepto());

        // Guardamos la entidad existente. Hibernate hará un UPDATE.
        direccionRepository.save(direccion);

        // No es necesario guardar 'persona' si solo actualizamos 'direccion',
        // pero no hace daño.

        return dto;
    }

    // --- LÓGICA DE ACERCADE (CORREGIDA) ---

    @Transactional(readOnly = true)
    public AcercaDeDto getAcercaDe(Authentication authentication) {
        Persona persona = getPersonaFromAuth(authentication);
        if (persona.getAcercade() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El perfil 'Acerca de' está vacío.");
        }

        // 1. Obtener la entidad real
        AcercaDe acercaDe = persona.getAcercade();

        // 2. Mapear a DTO
        AcercaDeDto dto = new AcercaDeDto();
        dto.setTitulo(acercaDe.getTitulo());
        dto.setDescripcion(acercaDe.getDescripcion());

        return dto; // 3. Devolver el DTO
    }

    @Transactional
    public AcercaDeDto createOrUpdateAcercaDe(Authentication authentication, AcercaDeDto dto) {
        Persona persona = getPersonaFromAuth(authentication);

        AcercaDe acercaDe = persona.getAcercade();
        if (acercaDe == null) {
            acercaDe = new AcercaDe();
        }

        acercaDe.setTitulo(dto.getTitulo());
        acercaDe.setDescripcion(dto.getDescripcion());

        AcercaDe savedAcercaDe = acercaDeRepository.save(acercaDe);

        if (persona.getAcercade() == null) {
            persona.setAcercade(savedAcercaDe);
            personaRepository.save(persona);
        }

        return dto; // Devolver el DTO
    }

    @Transactional
    public void deleteAcercaDe(Authentication authentication) {
        Persona persona = getPersonaFromAuth(authentication); // <- Solo datos propios
        AcercaDe acercaDe = persona.getAcercade();

        if (acercaDe != null) {
            // 1. Rompemos el vínculo en la tabla 'persona'
            persona.setAcercade(null);
            personaRepository.save(persona);

            // 2. Borramos la entidad 'acercade'
            // (La FK en 'persona' se puso 'ON DELETE SET NULL' en tu SQL,
            //  así que este orden es seguro)
            acercaDeRepository.delete(acercaDe);
        }
    }
}