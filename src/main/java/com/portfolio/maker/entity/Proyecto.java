package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "proyecto")
@Getter
@Setter
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto") // <--- ACTUALIZADO
    private Integer idProyecto;

    @Column(name = "nombre_proyecto") // <--- ACTUALIZADO
    private String nombreProyecto;

    @Column(name = "equipo_cantidad") // <--- ACTUALIZADO
    private Integer equipoCantidad;

    @Column(name = "fecha_lanzamiento") // <--- ACTUALIZADO
    private LocalDate fechaLanzamiento;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // --- Relación Inversa (Proyecto -> PersonaProyecto) ---
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PersonaProyecto> personaProyectos = new HashSet<>();
}