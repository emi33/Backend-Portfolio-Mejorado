package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*; // Importar
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personaProyecto")
@Getter
@Setter
@NoArgsConstructor
public class PersonaProyecto {

    @EmbeddedId
    private PersonaProyectoId id = new PersonaProyectoId();

    // --- Relaciones ---

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPersona") // Mapea al campo idPersona de la EmbeddedId
    @JoinColumn(name = "id_persona") // <--- ACTUALIZADO
    @JsonIgnore
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProyecto") // Mapea al campo idProyecto de la EmbeddedId
    @JoinColumn(name = "id_proyecto") // <--- ACTUALIZADO
    @JsonIgnore
    private Proyecto proyecto;

    // --- Columnas Adicionales ---

    @Column(name = "titulo")
    private String titulo;

    @Column(columnDefinition = "TEXT", name = "descripcion")
    private String descripcion;

    @Column(name = "tiempo_desarrollo") // <--- ACTUALIZADO
    private String tiempoDesarrollo;

    @Column(name = "rol")
    private String rol;

    @Column(name = "cantidad_equipo_trabajo") // <--- ACTUALIZADO
    private Integer cantidadEquipoTrabajo;

    @Column(name = "metodologia_usada") // <--- ACTUALIZADO
    private String metodologiaUsada;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "imagenproyecto", length = 255)
    private String imagenproyecto;
}