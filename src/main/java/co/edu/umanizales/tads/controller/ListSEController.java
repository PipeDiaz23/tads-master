package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class  ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(200, listSEService.getKids().getHead(), null), HttpStatus.OK);
    }


    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> getAverageAge() {
        try {
            double averageAge = listSEService.getKids().getAverageAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "El promedio de edad de los niños es " + averageAge,
                    null), HttpStatus.OK);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "No hay niños en la lista",
                    null), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "/deletekids/{identification}")
    public ResponseEntity<ResponseDTO> deleteKidByIdentification(@PathVariable String identification) {
        try {
            listSEService.getKids().deleteByIdentification(identification);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño con identificación " + identification + " ha sido eliminado", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping(path = "/deletekidsage/{age}")
    public ResponseEntity<ResponseDTO> deleteKidsByAge(@PathVariable int age) {
        try {
            listSEService.getKids().deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(200, "Se han eliminado los niños con edad " + age, null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Error al eliminar niños: " + e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/changeExtremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        try {
            listSEService.getKids().changeExtremes();
            return new ResponseEntity<>(new ResponseDTO(200, "se han intercambiado los extremos", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay niños en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() {
        try {
            listSEService.invert();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "SE ha invertido la lista",
                    null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay niños en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/orderboystostar")
    public ResponseEntity<ResponseDTO> orderBoyToStart() {
        try {
            listSEService.getKids().orderBoysToStart();
            return new ResponseEntity<>(new ResponseDTO(200, "Se han agregado los niños al inicio", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay niños en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/intercalategenders")
    public ResponseEntity<ResponseDTO> intercalateGenders() {
        try {
            listSEService.getKids().intercalateByGender();
            return new ResponseEntity<>(new ResponseDTO(200, "Se han intercalado los géneros de los niños", null), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay niños en la lista", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error interno", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(path = "/addbyposition")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody KidByPositionDTO kidByPositionDTO) {
        // Verificar si ya existe un niño con la misma identificación
        boolean isKidRegistered = listSEService.getKids().checkKidByIdentification(kidByPositionDTO.getIdentification());
        if (isKidRegistered) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "Ya existe un niño con la misma identificación",
                    null), HttpStatus.BAD_REQUEST);
        }
        // Si no existe, agregar el niño
        Location location = locationService.getLocationByCode(kidByPositionDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        listSEService.getKids().addByPosition(new Kid(kidByPositionDTO.getIdentification(),
                        kidByPositionDTO.getName(), kidByPositionDTO.getAge(), kidByPositionDTO.getGender(), location),
                kidByPositionDTO.getPosition());
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el niño",
                null), HttpStatus.OK);
    }

    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody KidByPositionDTO kidByPositionDTO) {
        try {
            // Verificar si ya existe un niño con la misma identificación
            boolean isKidRegistered = listSEService.getKids().checkKidByIdentification(kidByPositionDTO.getIdentification());
            if (isKidRegistered) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "Ya existe un niño con la misma identificación",
                        null), HttpStatus.BAD_REQUEST);
            }
            // Si no existe, agregar el niño
            Location location = locationService.getLocationByCode(kidByPositionDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.OK);
            }
            listSEService.getKids().addToStart(new Kid(
                    kidByPositionDTO.getIdentification(),
                    kidByPositionDTO.getName(),
                    kidByPositionDTO.getAge(),
                    kidByPositionDTO.getGender(),
                    location));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado el niño",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se ha producido un error al agregar el niño: " + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/gainposition/{id}/{position}")
    public ResponseEntity<ResponseDTO> gainPosition(@PathVariable String id, @PathVariable int position) {
        try {
            listSEService.getKids().gainPosition(id, position, listSEService.getKids());
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La posición ingresada no existe en la lista",
                    null), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "No se encontró un niño con la identificación ingresada",
                    null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al intentar mover el niño de posición",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "El niño con identificación " + id + " se ha movido a la posición " + position,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/backposition/{id}/{position}")
    public ResponseEntity<ResponseDTO> backPosition(@PathVariable String id, @PathVariable int position) {
        try {
            listSEService.getKids().backPosition(id, position, listSEService.getKids());
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La posición ingresada no existe en la lista",
                    null), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "No se encontró un niño con la identificación ingresada",
                    null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al intentar mover el niño de posición",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha movido el niño a una posición anterior",
                null), HttpStatus.OK);
    }



    @PostMapping(path = "/addkid")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        try {
            // Verificar si ya existe un niño con la misma identificación
            boolean isKidRegistered = listSEService.getKids().checkKidByIdentification(kidDTO.getIdentification());
            if (isKidRegistered) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "Ya existe un niño con la misma identificación",
                        null), HttpStatus.BAD_REQUEST);
            }
            // Si no existe, agregar el niño
            Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.OK);
            }
            listSEService.getKids().add(new Kid(
                    kidDTO.getIdentification(),
                    kidDTO.getName(),
                    kidDTO.getAge(),
                    kidDTO.getGender(),
                    location));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado el niño",
                    null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Se ha producido un error al intentar agregar el niño",
                    null), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getkidsByLocation() {
        try {
            List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
            for (Location loc : locationService.getLocations()) {
                int count = listSEService.getKids().getCountKidsByLocation(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(200, kidsByLocationDTOList, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al obtener la lista de niños por ubicación",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(path = "/kidsbydepto")
    public ResponseEntity<ResponseDTO> getKidsByDeptoCode() {
        try {
            List<KidsByLocationDTO> kidsByLocationDTOList1 = new ArrayList<>();
            for (Location loc : locationService.getLocations()) {
                int count = listSEService.getKids().getCountKidsByDeptoCode(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOList1.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(200, kidsByLocationDTOList1, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al obtener la lista de niños por departamento", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/reportsKids/{age}")
    public ResponseEntity<ResponseDTO> getReportsKidsLocationGenders(@PathVariable byte age) {
        try {
            ReportKidsLocationDTO report = new ReportKidsLocationDTO(locationService.getLocationsByCodeSize(8));
            listSEService.getKids().getReportKidsByLocationByGendersByAge(age, report);
            return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "La edad ingresada no es válida",
                    null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al generar el reporte",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "reportsagekids/{age}")
    public ResponseEntity<ResponseDTO> getReportsKidsByAgeGender(@PathVariable byte age) {
        try {
            ReportAgeQuantityKidsDTO report = new ReportAgeQuantityKidsDTO(listSEService.getKids().getKids());
            listSEService.getKids().getReportKidsByAgeByGender(age, report);
            return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "No hay niños registrados con la edad ingresada",
                    null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al generar el reporte",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}