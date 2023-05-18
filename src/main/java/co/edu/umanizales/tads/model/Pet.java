package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String name;
    private String id;
    private String race;
    private Byte age;
    private String color;
    private char gender;
    private Owner owner;
    private Location location;
    private Boolean status;


}
