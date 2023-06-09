package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Owner {
    private String name;
    private String id;
    private String contact;
    private char gender;
    private Location location;
}
