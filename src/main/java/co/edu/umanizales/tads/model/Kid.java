package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
public class Kid {
    private String identification;
    private String name;
    private byte age;
    private char gender;
    private Location location;
}
