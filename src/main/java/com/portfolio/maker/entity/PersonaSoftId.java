package com.portfolio.maker.entity;

import jakarta.persistence.Column; // <--- Importar
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PersonaSoftId implements Serializable {

    @Column(name = "id_persona") // <--- ACTUALIZADO
    private Integer idPersona;

    @Column(name = "id_habilidad_soft") // <--- ACTUALIZADO
    private Integer idHabilidadSoft;
}