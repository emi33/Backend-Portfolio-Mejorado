package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "educacion")
@Getter
@Setter
@NoArgsConstructor
public class Educacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_educacion") // <--- ACTUALIZADO
    private Integer idEducacion;

    @Column(name = "codigo_institucion") // <--- ACTUALIZADO
    private String codigoInstitucion;

    private String institucion;
    private String nivel;

    // --- Relación Inversa ---
    // Una educación puede estar en muchas entradas de "PersonaEducacion"
    @OneToMany(mappedBy = "educacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    private Set<PersonaEducacion> personaEducaciones = new HashSet<>();
}