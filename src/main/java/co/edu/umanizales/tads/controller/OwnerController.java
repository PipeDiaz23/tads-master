package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.OwnerDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Owner;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/owner")
public class OwnerController {
    @Autowired
    private OwnerService owmerService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    ResponseEntity<ResponseDTO> getOwners() {
        return new ResponseEntity<>(new ResponseDTO(200, owmerService.getOwners(), null), HttpStatus.OK);
    }

    @PostMapping(path = "/addowner")
    public ResponseEntity<ResponseDTO> addOwner(@RequestBody OwnerDTO ownerDTO) {
        Location location = locationService.getLocationByCode(ownerDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        owmerService.getOwners().add(new Owner(
                ownerDTO.getName(),
                ownerDTO.getId(),
                ownerDTO.getContact(),
                ownerDTO.getGender(),
                location));

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el dueño",
                null), HttpStatus.OK);
    }

}







