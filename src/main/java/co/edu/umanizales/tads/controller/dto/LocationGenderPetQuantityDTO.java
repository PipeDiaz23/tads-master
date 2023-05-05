package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class LocationGenderPetQuantityDTO {
    private String city;
    private List<GenderPetQuantityDTO> genders;
    private int total;

    public LocationGenderPetQuantityDTO(String city){
        this.city = city;
        this.total = 0;
        this.genders = new ArrayList<>();
        this.genders.add(new GenderPetQuantityDTO('M',0));
        this.genders.add(new GenderPetQuantityDTO('F',0));
    }
}
