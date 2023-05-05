package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class Pet {
    private String name;
    private String id;
    private String race;
    private Byte age;
    private String color;
    private char gender;
    private Owner owner;
    private Location location;

}
