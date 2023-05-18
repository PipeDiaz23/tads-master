package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListCircular;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListCircularService {
    private ListCircular pets;
    public ListCircularService(){pets = new ListCircular();}
}
