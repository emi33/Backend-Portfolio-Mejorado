package com.portfolio.maker.dto.auth;

import com.portfolio.maker.entity.enums.SexoEnum;
import jakarta.validation.constraints.*; // Importar
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // Regex para "Primera Mayúscula, resto minúsculas, permite nombres compuestos"
    private static final String CAPITALIZED_PATTERN = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñü]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñü]+)*$";

    // Regex para "solo números"
    private static final String NUMERIC_PATTERN = "^\\d+$";

    // Regex para "solo números, opcional" (permite nulo o vacío)
    private static final String OPTIONAL_NUMERIC_PATTERN = "^\\d*$";

    // --- Campos de Persona ---
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "El nombre debe tener la primera letra en mayúscula y el resto en minúscula (ej. 'Juan' o 'San Martín')")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "El apellido debe tener la primera letra en mayúscula y el resto en minúscula (ej. 'Perez')")
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un formato de email válido")
    private String email;

    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "^(Masculino|Femenino|Otro)$",
            message = "El campo 'sexo' es inválido. Debe ser 'Masculino', 'Femenino' u 'Otro'.")
    private String sexo; // <--- Ahora es un String // El Enum ya valida que sea 'Masculino', 'Femenino' u 'Otro'

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = NUMERIC_PATTERN, message = "El teléfono solo debe contener números, sin espacios ni caracteres")
    private String numeroTel;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 16, message = "La edad debe ser como mínimo 16 años")  // <-- ACTUALIZADO
    @Max(value = 130, message = "La edad debe ser como máximo 130 años") // <-- AÑADIDO
    private Integer edad;

    private String ocupacion; // Opcional

    // --- Campos de Usuario ---
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    // --- Campos de Direccion ---
    @NotBlank(message = "El país es obligatorio")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "El país debe tener la primera letra en mayúscula y el resto en minúscula")
    private String pais;

    @NotBlank(message = "La provincia es obligatoria")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "La provincia debe tener la primera letra en mayúscula y el resto en minúscula")
    private String provincia;

    @NotBlank(message = "La localidad es obligatoria")
    @Pattern(regexp = CAPITALIZED_PATTERN, message = "La localidad debe tener la primera letra en mayúscula y el resto en minúscula")
    private String localidad;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El número de dirección es obligatorio")
    @Pattern(regexp = NUMERIC_PATTERN, message = "El número de dirección solo debe contener números")
    private String numero;

    @Pattern(regexp = OPTIONAL_NUMERIC_PATTERN, message = "El piso debe ser numérico")
    private String piso; // Opcional

    private String depto; // Opcional
}