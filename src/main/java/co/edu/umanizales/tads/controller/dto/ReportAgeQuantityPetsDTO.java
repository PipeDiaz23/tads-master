package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class ReportAgeQuantityPetsDTO {

    private List<AgeGenderPetQuantityDTO> ageGenderPetQuantityDTOS;

    public ReportAgeQuantityPetsDTO(List<Pet> pets){
        ageGenderPetQuantityDTOS = new ArrayList<>();
        for(Pet pet:pets){
            ageGenderPetQuantityDTOS.add(new AgeGenderPetQuantityDTO(pet.getAge()));
        }
    }
    public void updateQuantity(int age, char gender){
        for(AgeGenderPetQuantityDTO ageGender : ageGenderPetQuantityDTOS){
            if(ageGender.getAge()==age){
                for(GenderPetQuantityDTO genderDto : ageGender.getGenders()){
                    genderDto.setQuantity(genderDto.getQuantity()+1);
                    ageGender.setTotal(ageGender.getTotal()+1);
                    return;
                }
            }
        }
    }


}
