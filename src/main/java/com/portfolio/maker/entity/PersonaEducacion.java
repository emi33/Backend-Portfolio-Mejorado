package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Table(name = "personaeducacion")
@Getter
@Setter
@NoArgsConstructor
public class PersonaEducacion {

    @EmbeddedId // 1. Usa la clave compuesta que definimos
    private PersonaEducacionId id = new PersonaEducacionId();

    // --- Relaciones (Dueño de la relación) ---

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPersona") // Mapea al campo idPersona de la EmbeddedId
    @JoinColumn(name = "id_persona") // <--- ACTUALIZADO (Coincide con la clave foránea)
    @JsonIgnore
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEducacion")
    @JoinColumn(name = "id_educacion") // <--- ACTUALIZADO
    @JsonIgnore
    private Educacion educacion;

    @Column(name = "nombre_institucion") // <--- ACTUALIZADO
    private String nombreInstitucion;

    @Column(name = "fecha_inicio") // <--- ACTUALIZADO
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin") // <--- ACTUALIZADO
    private LocalDate fechaFin;

    @Column(name = "anio_graduacion", columnDefinition = "YEAR") // <--- ACTUALIZADO
    private Year anioGraduacion;

    private String titulo;
}