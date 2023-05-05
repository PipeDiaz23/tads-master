package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Owner {

    private String name;
    private String id;
    private String contact;
    private String direction;
    private String gender;
}
