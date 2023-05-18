package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetByPositionDTO;
import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Owner;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListCircularService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/petscircular")
public class ListCircularController {
    @Autowired
    private ListCircularService listCircularService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private OwnerService ownerService;
    @GetMapping
    public ResponseEntity<ResponseDTO> getPets(){
        return new ResponseEntity<>(new ResponseDTO(
                200, listCircularService.getPets().getPetNext(), null), HttpStatus.OK);
    }

    @PostMapping(path = "/addpet")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        //verificar si la mascota existe
        boolean isPetRegistered = listCircularService.getPets().checkPetById(petDTO.getId());
        if (isPetRegistered) {
            return new ResponseEntity<>(new ResponseDTO(400, "Ya hay una mascota con el mismo", null),
                    HttpStatus.BAD_REQUEST);
        }
        Owner owner = ownerService.getOwners().getOwnerById(petDTO.getCodeOwner());
        if (owner == null) {
            // Devolver un valor de error o null
            return new ResponseEntity<>(new ResponseDTO(200, "el dueño no existe", null), HttpStatus.BAD_REQUEST);
        }

        // Verificar si la localidad ya existe
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            // Devolver un valor de error o null
            return new ResponseEntity<>(new ResponseDTO(200, "la locacion no existe", null), HttpStatus.BAD_REQUEST);
        }
        //crear la mascota
        listCircularService.getPets().add(new Pet(
                petDTO.getName(),
                petDTO.getId(),
                petDTO.getRace(),
                petDTO.getAge(),
                petDTO.getColor(),
                petDTO.getGender(),
                owner,
                location,
                false));
        return new ResponseEntity<>(new ResponseDTO(200, "se agrego la mascota", null), HttpStatus.OK);
    }
    @PostMapping(path = "/addpetostar")
    public ResponseEntity<ResponseDTO> addStar(@RequestBody PetDTO petDTO) {
        //verificar si la mascota existe
        boolean isPetRegistered = listCircularService.getPets().checkPetById(petDTO.getId());
        if (isPetRegistered) {
            return new ResponseEntity<>(new ResponseDTO(400, "Ya hay una mascota con el mismo", null),
                    HttpStatus.BAD_REQUEST);
        }
        Owner owner = ownerService.getOwners().getOwnerById(petDTO.getCodeOwner());
        if (owner == null) {
            // Devolver un valor de error o null
            return new ResponseEntity<>(new ResponseDTO(200, "el dueño no existe", null), HttpStatus.BAD_REQUEST);
        }

        // Verificar si la localidad ya existe
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            // Devolver un valor de error o null
            return new ResponseEntity<>(new ResponseDTO(200, "la locacion no existe", null), HttpStatus.BAD_REQUEST);
        }
        //crear la mascota
        listCircularService.getPets().addStar(new Pet(
                petDTO.getName(),
                petDTO.getId(),
                petDTO.getRace(),
                petDTO.getAge(),
                petDTO.getColor(),
                petDTO.getGender(),
                owner,
                location,
                false));
        return new ResponseEntity<>(new ResponseDTO(200, "se agrego la mascota", null), HttpStatus.OK);
    }

    @PostMapping(path = "/addbyposition")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody PetByPositionDTO petByPositionDTO ) {
        //verificar si la mascota existe
        boolean isPetRegistered = listCircularService.getPets().checkPetById(petByPositionDTO.getId());
        if (isPetRegistered) {
            return new ResponseEntity<>(new ResponseDTO(400, "Ya hay una mascota con el mismo", null),
                    HttpStatus.BAD_REQUEST);
        }
        Owner owner = ownerService.getOwners().getOwnerById(petByPositionDTO.getCodeOwner());
        if (owner == null) {
            // Devolver un valor de error o null
            return new ResponseEntity<>(new ResponseDTO(200, "el dueño no existe", null), HttpStatus.BAD_REQUEST);
        }

        // Verificar si la localidad ya existe
        Location location = locationService.getLocationByCode(petByPositionDTO.getCodelocation());
        if (location == null) {
            throw new IllegalArgumentException("La localidad no existe");
        }
        //crear la mascota
        listCircularService.getPets().addByPosition(new Pet(
                petByPositionDTO.getName(),
                petByPositionDTO.getId(),
                petByPositionDTO.getRace(),
                petByPositionDTO.getAge(),
                petByPositionDTO.getColor(),
                petByPositionDTO.getGender(),
                owner,
                location,
                false), petByPositionDTO.getPosition());
        return new ResponseEntity<>(new ResponseDTO(200, "se agrego la mascota", null), HttpStatus.OK);
    }
    @GetMapping("/takeShower/{location}")
    public ResponseEntity<ResponseDTO> takeShower(@PathVariable char location) {
        Pet pet = listCircularService.getPets().takeShower(location);
        if (pet == null) {
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota ya estaba bañada", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota " + pet.getId() + " se bañó", null), HttpStatus.OK);
        }
    }
}

