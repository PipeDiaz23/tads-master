package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Ranges;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RangeDTO {
    private Ranges ranges;
    int quantity;
}