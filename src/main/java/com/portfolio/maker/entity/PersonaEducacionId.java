package com.portfolio.maker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable // Marca la clase como "incrustable"
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode // Esencial para claves compuestas
public class PersonaEducacionId implements Serializable {
    @Column(name = "id_persona") // <--- ACTUALIZADO
    private Integer idPersona;

    @Column(name = "id_educacion") // <--- ACTUALIZADO
    private Integer idEducacion;
}