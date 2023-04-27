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

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        // Verificar si ya existe un niño con la misma identificación
        Kid existingKid = listSEService.getKids().getKidByIdentification(kidDTO.getIdentification());
        if (existingKid != null) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, "Ya existe un niño con la misma identificación",
                    null), HttpStatus.CONFLICT);
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

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getkidsByLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc : locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,kidsByLocationDTOList,null),HttpStatus.OK);
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
