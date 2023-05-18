package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportPetsLocationDTO {

    private List<LocationGenderPetQuantityDTO> locationsGenderPetQuantityDTOS;

    public ReportPetsLocationDTO(List<Location> cities){
        locationsGenderPetQuantityDTOS = new ArrayList<>();
        for(Location locations: cities){
            locationsGenderPetQuantityDTOS.add(new LocationGenderPetQuantityDTO(locations.getName()));
        }
    }
    //Metodo para actualizar
    public void updateQuantity(String city,char gender){
        for(  LocationGenderPetQuantityDTO loc : locationsGenderPetQuantityDTOS){
            if(loc.getCity().equals(city)){
                for(GenderQuantityDTO genderDto: loc.getGenders()){
                    if(genderDto.getGender()==gender){
                        genderDto.setQuantity(genderDto.getQuantity()+1);

                        loc.setTotal(loc.getTotal()+1);
                        return;
                    }
                }
            }
        }
    }
}