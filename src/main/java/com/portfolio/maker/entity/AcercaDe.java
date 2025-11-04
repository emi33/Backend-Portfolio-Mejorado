package com.portfolio.maker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "acercade")
@Getter
@Setter
@NoArgsConstructor
public class AcercaDe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acercade") // <--- ACTUALIZADO
    private Integer idAcercade;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String titulo;
}