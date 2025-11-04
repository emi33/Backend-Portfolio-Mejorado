package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trabajo")
@Getter
@Setter
@NoArgsConstructor
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajo") // <--- ACTUALIZADO
    private Integer idTrabajo;

    @Column(columnDefinition = "TEXT", name = "descripcion_empresa") // <--- ACTUALIZADO
    private String descripcionEmpresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion") // <--- ACTUALIZADO
    private Direccion direccion;

    private String empresa;

    private String cuit;
    private String numero;

    // --- Relación Inversa (Trabajo -> trabajoPersona) ---
    @OneToMany(mappedBy = "trabajo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    private Set<TrabajoPersona> personaTrabajos = new HashSet<>();
}