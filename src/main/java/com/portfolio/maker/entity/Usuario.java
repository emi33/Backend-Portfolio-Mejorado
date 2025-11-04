package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.maker.entity.enums.RolEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data // Incluye @Getter, @Setter, @ToString, @EqualsAndHashCode
@Builder // Patrón de diseño Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // <--- ACTUALIZADO
    private Integer idUsuario;

    @Column(name = "refresh_token")
    @JsonIgnore
    private String refreshToken;

    @Column(nullable = false, length = 100, name = "nombre_usuario") // <--- ACTUALIZADO
    private String nombreUsuario;

    @Column(name = "contrasena_hash", nullable = false, length = 255) // <--- ACTUALIZADO
    @JsonIgnore
    private String contrasenaHash;

    @Column(name = "fecha_creacion", nullable = false, updatable = false) // <--- ACTUALIZADO
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Builder.Default // Asegura que el valor por defecto se aplique con Lombok Builder
    private RolEnum rol = RolEnum.USUARIO;

    @Column(name = "ultimo_acceso") // <--- ACTUALIZADO
    @UpdateTimestamp
    private LocalDateTime ultimoAcceso;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona") // <--- ACTUALIZADO
    @JsonIgnore
    private Persona persona;

    // ======================================================
    // MÉTODOS SOBREESCRITOS DE UserDetails
    // ======================================================

    /**
     * Retorna los roles/autoridades del usuario.
     * Spring Security usará esto para la autorización (ej. @PreAuthorize("hasRole('ADMIN')"))
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // "ROLE_" es un prefijo estándar que Spring Security busca.
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    /**
     * Retorna la contraseña (hash) usada para autenticar.
     */
    @Override
    public String getPassword() {
        return contrasenaHash;
    }

    /**
     * Retorna el campo que se usará como "username" para el login.
     * En nuestro caso, usaremos el email por ser único.
     */
    @Override
    public String getUsername() {
        return email;
    }

    // Los siguientes métodos se pueden usar para deshabilitar cuentas,
    // bloquearlas, etc. Como no tenemos esas columnas en la BD,
    // los dejamos en 'true' (cuenta siempre activa).

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta nunca se bloquea
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales nunca expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // La cuenta está siempre habilitada
    }
}