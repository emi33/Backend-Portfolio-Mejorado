package com.portfolio.maker.repository;

import com.portfolio.maker.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Le dice a Spring que esta es una interfaz de Repositorio
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    boolean existsByEmail(String email);

    // ¡Eso es todo!

    // Spring Data JPA generará automáticamente métodos como:
    // - save(Persona persona) -> Guarda o actualiza
    // - findById(Integer id) -> Busca por ID
    // - findAll() -> Devuelve todas las personas
    // - deleteById(Integer id) -> Borra por ID
    // - ...y muchos más.
    @Query("SELECT p FROM Persona p LEFT JOIN FETCH p.acercade LEFT JOIN FETCH p.direccion WHERE p.idPersona = :id")
    Optional<Persona> findByIdWithDetails(@Param("id") Integer id);
}