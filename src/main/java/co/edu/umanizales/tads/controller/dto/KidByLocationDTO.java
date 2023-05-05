package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

@Data
public class KidByLocationDTO {
    private String identification;
    private String name;
    private byte age;
    private char gender;
    private String codeLocation;
}
