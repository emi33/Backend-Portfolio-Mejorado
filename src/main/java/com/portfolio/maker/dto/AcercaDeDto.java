package com.portfolio.maker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcercaDeDto {

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;
}