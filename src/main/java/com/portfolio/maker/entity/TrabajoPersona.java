package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*; // Importar
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "trabajoPersona")
@Getter
@Setter
@NoArgsConstructor
public class TrabajoPersona {

    @EmbeddedId
    private TrabajoPersonaId id = new TrabajoPersonaId();

    // --- Relaciones ---

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPersona")
    @JoinColumn(name = "id_persona") // <--- ACTUALIZADO
    @JsonIgnore
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTrabajo")
    @JoinColumn(name = "id_trabajo") // <--- ACTUALIZADO
    @JsonIgnore
    private Trabajo trabajo;

    // --- Columnas Adicionales ---

    @Column(name = "nombre_empresa") // <--- ACTUALIZADO
    private String nombreEmpresa;

    @Column(columnDefinition = "TEXT", name = "descripcion_empresa") // <--- ACTUALIZADO
    private String descripcionEmpresa;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "fecha_inicio") // <--- ACTUALIZADO
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin") // <--- ACTUALIZADO
    private LocalDate fechaFin;

    @Column(columnDefinition = "TEXT", name = "descripcion_trabajo") // <--- ACTUALIZADO
    private String descripcionTrabajo;
}