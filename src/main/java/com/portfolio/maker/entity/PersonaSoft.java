package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.maker.entity.enums.FrecuenciaEnum;
import com.portfolio.maker.entity.enums.NivelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personaSoft")
@Getter
@Setter
@NoArgsConstructor
public class PersonaSoft {

    @EmbeddedId
    private PersonaSoftId id = new PersonaSoftId();

    // --- Relaciones ---

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPersona")
    @JoinColumn(name = "id_persona")
    @JsonIgnore
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idHabilidadSoft")
    @JoinColumn(name = "id_habilidad_soft")
    @JsonIgnore
    private HabilidadSoft habilidadSoft; // <-- Este es el nombre correcto (habilidadSoft)

    // --- Columnas Adicionales ---

    @Column(name = "nombre_soft")
    private String nombreSoft;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel")
    private NivelEnum nivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private FrecuenciaEnum tipo;
}