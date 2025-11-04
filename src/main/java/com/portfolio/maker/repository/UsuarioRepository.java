package com.portfolio.maker.repository;

import com.portfolio.maker.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su email.
     * Spring Data JPA entiende el nombre del método "findByEmail"
     * y automáticamente genera la consulta SQL (SELECT * FROM usuarios WHERE email = ?)
     *
     * @param email El email a buscar.
     * @return Un Optional que puede contener al Usuario si se encuentra.
     */
    Optional<Usuario> findByEmail(String email);
}