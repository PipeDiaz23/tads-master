package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Owner;
import co.edu.umanizales.tads.model.OwnerList;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class OwnerService {
    private OwnerList owners;


    public OwnerService(){
        owners=new OwnerList();}

}
