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

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "habilidadhard")
@Getter
@Setter
@NoArgsConstructor
public class HabilidadHard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilidad_hard") // <--- ACTUALIZADO
    private Integer idHabilidadHard;

    @Column(name = "nombre_hard") // <--- ACTUALIZADO
    private String nombreHard;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String tecnologia;

    // --- Relación Inversa (HabilidadHard -> PersonaHard) ---
    @OneToMany(mappedBy = "habilidadHard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)    @JsonIgnore
    private Set<PersonaHard> personaHardSkills = new HashSet<>();
}