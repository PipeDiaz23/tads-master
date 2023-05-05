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
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(200,listSEService.getKids().getHead(),null),HttpStatus.OK);
    }


    @GetMapping(path= "/changeExtremes")
    public ResponseEntity<ResponseDTO> changeExtremes(){
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(200,"se han intercambiado los extremos",null),HttpStatus.OK);
    }
    @GetMapping(path = "invertir")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(200,"se ha invertido la lista",null),HttpStatus.OK);
    }
    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> getAverAge(){
        double averageAgee = listSEService.getKids().getAverAge();
        return new ResponseEntity<>(new ResponseDTO(200,"El promedio de las edades es: "+averageAgee,
                null),HttpStatus.OK);
    }

    @GetMapping(path = "/orderBoysToStart")
    public ResponseEntity<ResponseDTO> orderBoysToStart(){
        listSEService.getKids().orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(200,"Se añadieron los niños al inicio",null),HttpStatus.OK);
    }

    @PostMapping(path = "/addbyposition")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody KidByPositionDTO kidByPositionDTO) {
        // verificar si ya existe un niño con la misma identification
        boolean isKidRegistered = listSEService.getKids().checkKidByIdentification(kidByPositionDTO.getIdentification());
        if (isKidRegistered) {
            return new ResponseEntity<>(new ResponseDTO(404, "Ya existe un niño con la misma identification",
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
                        kidByPositionDTO.getName(), kidByPositionDTO.getAge(), kidByPositionDTO.getGender(),location),
                kidByPositionDTO.getPosition());
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el niño",
                null), HttpStatus.OK);
}


        @PostMapping(path = "/addkid")
        public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
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
        }


    @GetMapping(path = "/kidsbyLocations")
    public ResponseEntity<ResponseDTO> getkidsbyLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc : locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,kidsByLocationDTOList,null),HttpStatus.OK);
 }

     @GetMapping(path = "/kidsbydepto")
     public ResponseEntity<ResponseDTO> getKidsByDeptocode(){
        List<KidsByLocationDTO> kidsByLocationDTOList1= new ArrayList<>();
        for (Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByDeptoCode(loc.getCode());
            if (count>0){
                kidsByLocationDTOList1.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,kidsByLocationDTOList1,null), HttpStatus.OK);
     }



     @GetMapping(path = "kidsbyage")
    public ResponseEntity<ResponseDTO> kidsbyage(@PathVariable byte age){
        List<KidsByGenderDTO> kidsByGenderDTOList = new ArrayList<>();
        for (Location loc:locationService.getLocations()){
            if (loc.getCode().length()==8){
                String nameCity = loc.getName();
                List<GenderDTO> genderDTOList=new ArrayList<>();

                genderDTOList.add(new GenderDTO('m',listSEService.getKids().getCountKidsByCityByAgeBygender(loc.getCode()
                        , 'm',age)));
                genderDTOList.add(new GenderDTO('f',listSEService.getKids().getCountKidsByCityByAgeBygender(loc.getCode()
                        , 'f',age)));
                int total= genderDTOList.get(0).getQuantity() + genderDTOList.get(1).getQuantity();

                kidsByGenderDTOList.add(new KidsByGenderDTO(nameCity,genderDTOList,total));
        }
    }

    return new ResponseEntity<>(new ResponseDTO(200, kidsByGenderDTOList, null), HttpStatus.OK);


    }

    @GetMapping(path= "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKidsLocationGenders(@PathVariable byte age){
        ReportKidsLocationGenderDTO report = new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids().getReportKidsBylocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(200,report,null), HttpStatus.OK);
    }
}
