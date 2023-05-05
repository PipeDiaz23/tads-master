package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportKidsLocationDTO {
    private List<LocationGenderQuantityDTO> locationGenderQuantityDTOS;

    public ReportKidsLocationDTO(List<Location> cities){
        locationGenderQuantityDTOS = new ArrayList<>();
        for(Location locations : cities){
            locationGenderQuantityDTOS.add(new LocationGenderQuantityDTO(locations.getName()));
        }
    }
    //metodo para actualizar
    public void updateQuantity(String city,char gender){
        for(LocationGenderQuantityDTO loc: locationGenderQuantityDTOS){
            if(loc.getCity().equals(city)){
                for(GenderQuantityDTO genderDto : loc.getGenders()){
                    if(genderDto.getGender()==gender){
                        genderDto.setQuantity(genderDto.getQuantity()+1);

                        loc.setTotal(loc.getTotal()+1);
                        //al colocar el return rompo los ciclos
                        return;
                    }
                }
            }
        }
    }
}
