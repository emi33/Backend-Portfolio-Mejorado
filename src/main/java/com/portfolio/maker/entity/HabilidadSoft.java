package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "habilidadSoft") // <--- CORREGIDO (era "habilidadsoft")
@Getter
@Setter
@NoArgsConstructor
public class HabilidadSoft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilidad_soft")
    private Integer idHabilidadSoft;

    @Column(name = "nombre_hard_soft")
    private String nombreHabSoft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private CategoriaSoft categoria;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // --- Relación Inversa (Habilidad -> PersonaSoft) ---
    @OneToMany(mappedBy = "habilidadSoft", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // <--- CORREGIDO (era "habilidadsoft")
    @JsonIgnore
    private Set<PersonaSoft> personaSoftSkills = new HashSet<>();
}