package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListDE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListDeService {

    private ListDE pets;

    public ListDeService() {
        pets=new ListDE();
    }
}