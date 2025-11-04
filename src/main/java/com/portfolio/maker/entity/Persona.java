package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.maker.entity.enums.SexoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "persona")
@Getter
@Setter
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona") // <--- ACTUALIZADO
    private Integer idPersona;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    @Column(name = "numero_tel") // <--- ACTUALIZADO
    private String numeroTel;

    private Integer edad;

    @Column(unique = true, length = 150)
    private String email;

    @Column(name = "imagen_perfil") // <--- ACTUALIZADO
    private String imagenPerfil;

    @Column(length = 150)
    private String ocupacion;

    @Column(name = "imagen_banner") // <--- ACTUALIZADO
    private String imagenBanner;

    // --- RELACIONES ---

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acercade_id") // <--- ACTUALIZADO (coincide con tu FK)
    private AcercaDe acercade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion") // <--- ACTUALIZADO
    private Direccion direccion;

    // NOTA: Las relaciones con 'usuarios' y las tablas intermedias
    // Una persona puede tener muchas entradas de "Educacion"
    @OneToMany(
            mappedBy = "persona", // "persona" es el nombre del campo en PersonaEducacion
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    private Set<PersonaEducacion> personaEducaciones = new HashSet<>();

    @OneToMany(
            mappedBy = "persona", // "persona" es el nombre del campo en TrabajoPersona
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    private Set<TrabajoPersona> personaTrabajos = new HashSet<>();

    // Una persona puede tener muchas "Soft Skills"
    @OneToMany(
            mappedBy = "persona", // "persona" es el nombre del campo en PersonaSoft
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Set<PersonaSoft> personaSoftSkills = new HashSet<>();

    // Una persona puede tener muchas "Hard Skills"
    @OneToMany(
            mappedBy = "persona", // "persona" es el nombre del campo en PersonaHard
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Set<PersonaHard> personaHardSkills = new HashSet<>();

    // Una persona puede tener muchos "Proyectos"
    @OneToMany(
            mappedBy = "persona", // "persona" es el nombre del campo en PersonaProyecto
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Set<PersonaProyecto> personaProyectos = new HashSet<>();

    // Relación inversa: Una Persona tiene un Usuario
    // "persona" es el nombre del campo en la entidad Usuario
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Usuario usuario;
}