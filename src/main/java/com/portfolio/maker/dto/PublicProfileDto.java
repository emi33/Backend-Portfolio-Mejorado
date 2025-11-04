package com.portfolio.maker.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Este DTO "aplana" los datos de Persona, AcercaDe y Direccion
 * para ser mostrados públicamente de forma segura.
 */
@Data
@Builder // Usamos @Builder para facilitar la construcción en el servicio
public class PublicProfileDto {

    // --- Campos de Persona ---
    private String nombre;
    private String apellido;
    private String imagenPerfil;
    private String ocupacion;
    private String imagenBanner;

    // --- Campos de AcercaDe ---
    private String acercaDeTitulo;
    private String acercaDeDescripcion;

    // --- Campos de Direccion (¡Solo los públicos!) ---
    private String pais;
    private String provincia;
    private String localidad;

    // NOTA: Omitimos campos privados como email, numeroTel,
    // y la dirección exacta (calle y número) por seguridad.
}