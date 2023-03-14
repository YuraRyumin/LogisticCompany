package com.logistic.company.controller;

import com.logistic.company.dto.CargoDTO;
import com.logistic.company.entity.Cargo;
import com.logistic.company.service.CargoService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CargoController {
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cargos")
    public CargoDTO getCargo(@RequestParam Long cargoId){
        return cargoService.getCargoById(cargoId);
    }

    @GetMapping("/cargoslist")
    public Iterable<CargoDTO> listOfCargos(){
        return cargoService.getAllCargos();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createcargo")
    public void createCargo(@RequestBody Cargo cargo){
        cargoService.saveCargo(cargo);
    }
}
