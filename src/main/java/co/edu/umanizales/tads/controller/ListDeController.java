package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Owner;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListDeService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.OwnerService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listde")
public class  ListDeController {
    @Autowired
    private ListDeService listDeService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangeService rangeService;
    @Autowired
    private OwnerService ownerService;
    @GetMapping
    public ResponseEntity<ResponseDTO> getPets(){
        return new ResponseEntity<>(new ResponseDTO(
                200, listDeService.getPets().getPets(), null), HttpStatus.OK);
    }
    //getPetsPrev
    @GetMapping(path = "/getpetsprev")
    public ResponseEntity<ResponseDTO>getPetsPrev(){
        return new ResponseEntity<>(new ResponseDTO(
                200, listDeService.getPets().getPetsPrev(), null), HttpStatus.OK);
    }
    @PostMapping(path = "/addpet")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        try {
            // Verificar si la mascota ya existe
            boolean isPetRegistered = listDeService.getPets().checkPetById(petDTO.getId());
            if (isPetRegistered) {
                throw new IllegalArgumentException("Ya existe una mascota con el mismo ID");
            }

            // Verificar si el dueño ya existe
            Owner owner = ownerService.getOwners().getOwnerById(petDTO.getCodeOwner());
            if (owner == null) {
                throw new IllegalArgumentException("El dueño no existe");
            }

            // Verificar si la localidad ya existe
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                throw new IllegalArgumentException("La localidad no existe");
            }

            // Si esta correcto
            listDeService.getPets().add(new Pet(
                    petDTO.getName(),
                    petDTO.getId(),
                    petDTO.getRace(),
                    petDTO.getAge(),
                    petDTO.getColor(),
                    petDTO.getGender(),
                    owner,
                    location,
                    false));

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado la mascota", null), HttpStatus.OK);

        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se ha producido un error inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping(path = "/addtostar")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody PetDTO petDTO) {
        try {
            // Verificar si la mascota ya existe
            if (listDeService.getPets().checkPetById(petDTO.getId())) {
                throw new IllegalArgumentException("Ya existe una mascota con el mismo ID");
            }
            // Verificar si el dueño ya existe
            Owner owner = ownerService.getOwners().getOwnerById(petDTO.getCodeOwner());
            if (owner == null) {
                throw new IllegalArgumentException("El dueño no existe");
            }

            // Verificar si la localidad ya existe
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                throw new IllegalArgumentException("La localidad no existe");
            }

            // Si esta correcto

            listDeService.getPets().addToStart(new Pet(
                    petDTO.getName(),
                    petDTO.getId(),
                    petDTO.getRace(),
                    petDTO.getAge(),
                    petDTO.getColor(),
                    petDTO.getGender(),
                    owner,
                    location,
                    false));

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado la mascota",
                    null), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/addbyposition")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody PetByPositionDTO petByPositionDTO) {
        try {
            // Verificar si la mascota ya existe
            if (listDeService.getPets().checkPetById(petByPositionDTO.getId())) {
                throw new IllegalArgumentException("Ya existe una mascota con el mismo ID");
            }

            // Verificar si el dueño ya existe
            Owner owner = ownerService.getOwners().getOwnerById(petByPositionDTO.getCodeOwner());
            if (owner == null) {
                throw new IllegalArgumentException("El dueño no existe");
            }

            // Verificar si la localidad ya existe
            Location location = locationService.getLocationByCode(petByPositionDTO.getCodelocation());
            if (location == null) {
                throw new IllegalArgumentException("La localidad no existe");
            }

            listDeService.getPets().addByPosition(new Pet(
                    petByPositionDTO.getName(),
                    petByPositionDTO.getId(),
                    petByPositionDTO.getRace(),
                    petByPositionDTO.getAge(),
                    petByPositionDTO.getColor(),
                    petByPositionDTO.getGender(),
                    owner,
                    location,
                    false), petByPositionDTO.getPosition());

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado la mascota",
                    null), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }




    @DeleteMapping(path = "/deletepets/{id}")
    public ResponseEntity<ResponseDTO> deletePetById(@PathVariable String id){
        try {
            listDeService.getPets().deletePetById(id);
            return new ResponseEntity<>(new ResponseDTO(200,"La mascota con ID"+ id+"ha sido eliminado",
                    null),HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No se encontró la mascota con ID "+id, null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deletepetsbyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age){
        try {
            listDeService.getPets().deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(200,"Las mascotas con edad:"+ age +"han sido eliminadas",
                    null),HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No se encontraron mascotas con edad "+age, null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getavarage")
    public ResponseEntity<ResponseDTO> getAverageAge(){
        try {
            double averageAge= listDeService.getPets().getAverageAge();
            return new ResponseEntity<>(new ResponseDTO(200,"el promedio de edades es:"+ averageAge,
                    null),HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay mascotas en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/changeExtremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        try {
            listDeService.getPets().changeExtremes();
            return new ResponseEntity<>(new ResponseDTO(200, "Se han intercambiado los extremos", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(path = "/backposition/{id}/{position}")
    public ResponseEntity<ResponseDTO> backPosition(@PathVariable String id, @PathVariable int position) {
        try {
            listDeService.getPets().backPosition(id, position, listDeService.getPets());
            return new ResponseEntity<>(new ResponseDTO(200, "Se ha movido la macota a una posicion anterior", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No se encontró la mascota con ID " + id, null), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(400, "La posición debe ser mayor que cero", null), HttpStatus.BAD_REQUEST);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ResponseDTO(400, "La posición especificada está fuera de los límites de la lista", null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/gainPosition/{id}/{position}")
    public ResponseEntity<ResponseDTO> gainPosition(@PathVariable String id, @PathVariable int position) {
        try {
            listDeService.getPets().gainPosition(id, position, listDeService.getPets());
            return new ResponseEntity<>(new ResponseDTO(200, "Se ha movido la macota a una posicion anterior", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No se encontró la mascota con ID " + id, null), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(400, "La posición debe ser mayor que cero", null), HttpStatus.BAD_REQUEST);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ResponseDTO(400, "La posición especificada está fuera de los límites de la lista", null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/orderBoysToStart")
    public ResponseEntity<ResponseDTO> orderBoysToStart() {
        try {
            listDeService.getPets().orderBoysToStart();
            return new ResponseEntity<>(new ResponseDTO(200, "Se han agregado las mascotas de sexo masculino al inicio", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No se encontraron mascotas", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/intercalateByGender")
    public ResponseEntity<ResponseDTO> intercalateByGender() {
        try {
            listDeService.getPets().intercalateByGender();
            return new ResponseEntity<>(new ResponseDTO(200, "Se han intercalado los géneros", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay mascotas en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/petsbylocations")
    public ResponseEntity<ResponseDTO> getPetsByLocation() {
        try {
            List<PetByLocationDTO> petByLocationDTOSList = new ArrayList<>();
            for (Location loc : locationService.getLocations()) {
                int count = listDeService.getPets().getCountPetsByLocation(loc.getCode());
                if (count > 0) {
                    petByLocationDTOSList.add(new PetByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(200, petByLocationDTOSList, null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay mascotas en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/petsbydeptos")
    public ResponseEntity<ResponseDTO> getPetsBydeptos(){
        try {
            List<PetByLocationDTO> petByLocationDTOSList = new ArrayList<>();
            for(Location loc : locationService.getLocations()){
                int count = listDeService.getPets().getCountPetByDeptoCode(loc.getCode());
                if(count > 0){
                    petByLocationDTOSList.add(new PetByLocationDTO(loc,count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(200, petByLocationDTOSList, null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay mascotas en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/reportspets/{age}")
    public ResponseEntity<ResponseDTO> getReportsPetsLocationGenders(@PathVariable byte age) {
        try {
            ReportPetsLocationDTO report = new ReportPetsLocationDTO(locationService.getLocationsByCodeSize(8));
            listDeService.getPets().getReportPetsByLocationByGendersByAge(age, report);
            return ResponseEntity.ok(new ResponseDTO(200, report, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(400, "La edad ingresada no es válida", null));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Ha ocurrido un error interno", null));
        }
    }
    @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> informRangeByAge() {
        try {
            List<RangeDTO> petsRangeList = new ArrayList<>();
            for (Ranges i : rangeService.getRanges()) {
                int quantity = listDeService.getPets().informRangeByAge(i.getFrom(), i.getTo());
                petsRangeList.add(new RangeDTO(i, quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, "El rango de las mascktas es: " + petsRangeList, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(path = "/delatebyidtwo/{id}")
    public ResponseEntity<ResponseDTO> deletePetByIdTwo(@PathVariable String id){
        listDeService.getPets().deletePetByIdTwo(id);
        return new ResponseEntity<>(new ResponseDTO(200,"la mascota fue eliminada",null),HttpStatus.OK);
    }

}



