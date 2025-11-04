package com.portfolio.maker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DireccionDto {

    private static final String CAPITALIZED_PATTERN = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñü]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñü]+)*$";
    private static final String NUMERIC_PATTERN = "^\\d+$";
    private static final String OPTIONAL_NUMERIC_PATTERN = "^\\d*$";

    @NotBlank(message = "El país es obligatorio")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "El país debe tener la primera letra en mayúscula")
    private String pais;

    @NotBlank(message = "La provincia es obligatoria")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "La provincia debe tener la primera letra en mayúscula")
    private String provincia;

    @NotBlank(message = "La localidad es obligatoria")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "La localidad debe tener la primera letra en mayúscula")
    private String localidad;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El número de dirección es obligatorio")
    @Pattern(regexp = NUMERIC_PATTERN, message = "El número de dirección solo debe contener números")
    private String numero;

    @Pattern(regexp = OPTIONAL_NUMERIC_PATTERN, message = "El piso debe ser numérico")
    private String piso;

    private String depto;
}