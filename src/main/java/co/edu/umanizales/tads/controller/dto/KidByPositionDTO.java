package co.edu.umanizales.tads.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class KidByPositionDTO {
    @NotBlank(message = "El campo no puede estar vacío")
    private String identification;
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
    private String name;

    @Min(value = 1, message = "La edad debe ser mayor a 0")
    @Max(value = 14, message = "La edad debe ser menor a 15")
    private byte age;

    @NotBlank(message = "El género no puede estar vacío")
    @Pattern(regexp = "[M|F]", message = "El género debe ser M o F")
    private char gender;

    @NotBlank(message = "El campo no puede estar vacío")
    private String codeLocation;
    @NotBlank(message = "El campo no puede estar vacío")
    private int position;
}
