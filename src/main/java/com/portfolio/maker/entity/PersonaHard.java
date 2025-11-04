package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.maker.entity.enums.FrecuenciaEnum;
import jakarta.persistence.*; // Importar
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personaHard")
@Getter
@Setter
@NoArgsConstructor
public class PersonaHard {

    @EmbeddedId
    private PersonaHardId id = new PersonaHardId();

    // --- Relaciones ---

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPersona")
    @JoinColumn(name = "id_persona") // <--- ACTUALIZADO
    @JsonIgnore
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idHabilidadHard")
    @JoinColumn(name = "id_habilidad_hard")
    @JsonIgnore
    private HabilidadHard habilidadHard; // <-- El campo se llama 'habilidadHard'

    // --- Columnas Adicionales ---

    @Column(name = "nombre_hard") // <--- ACTUALIZADO
    private String nombreHard;

    @Column(name = "porcentaje")
    private Integer porcentaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private FrecuenciaEnum tipo;
}