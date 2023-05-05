package co.edu.umanizales.tads.controller.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetDTO {


    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
    private String name;
    @NotBlank(message = "El campo no puede estar vacío")
    @Size(max = 30, message = "La identificacion no puede tener más de 30 caracteres")
    private String id;
    @NotBlank(message = "El campo no puede estar vacío")
    @Size(max = 30, message = "La raza no puede tener más de 30 caracteres")
    private String race;
    @Min(value = 1, message = "La edad debe ser mayor a 0")
    @Max(value = 14, message = "La edad debe ser menor a 15")
    private Byte age;
    @NotBlank(message = "El campo no puede estar vacío")
    @Size(max = 30, message = "El color no puede tener más de 30 caracteres")
    private String color;
    @NotBlank(message = "El género no puede estar vacío")
    @Pattern(regexp = "[M|F]", message = "El género debe ser M o F")
    private char gender;
    @NotBlank(message = "El campo no puede estar vacío")
    private String codeOwner;
    @NotBlank(message = "El campo no puede estar vacío")
    private String codelocation;


}
