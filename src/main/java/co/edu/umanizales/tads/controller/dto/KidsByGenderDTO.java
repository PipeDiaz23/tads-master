package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data

public class KidsByGenderDTO {
private String city;
private List<GenderDTO> genderDTOList;
private int quantity;

}
