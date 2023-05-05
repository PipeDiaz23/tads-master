package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AgeGenderPetQuantityDTO {
    private byte age;
    private List<GenderPetQuantityDTO> genders;
    private int total;

    public AgeGenderPetQuantityDTO(int age){

        this.total = 0;
        this.genders =  new ArrayList<>();
        this.genders.add(new GenderPetQuantityDTO('M',0));
        this.genders.add(new GenderPetQuantityDTO('F',0));
    }
}
