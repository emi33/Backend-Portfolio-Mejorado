package com.portfolio.maker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "direccion")
@Getter
@Setter
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion") // <--- ACTUALIZADO
    private Integer idDireccion;

    private String pais;
    private String provincia;
    private String localidad;
    private String direccion;
    private String numero;
    private String piso;
    private String depto;
}
