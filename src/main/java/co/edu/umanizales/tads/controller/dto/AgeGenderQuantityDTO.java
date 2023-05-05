package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AgeGenderQuantityDTO {

    private byte age;

    private List<GenderQuantityDTO> genders;
    private int total;

    public AgeGenderQuantityDTO(int age){
        this.total = 0;
        this.genders =  new ArrayList<>();
        this.genders.add(new GenderQuantityDTO('M',0));
        this.genders.add(new GenderQuantityDTO('F',0));
    }
}
