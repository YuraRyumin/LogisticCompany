package com.logistic.company.controller;

import com.logistic.company.dto.TruckDTO;
import com.logistic.company.entity.Truck;
import com.logistic.company.service.TruckService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TruckController {
    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trucks")
    public TruckDTO getTruck(@RequestParam Long truckId){
        return truckService.getTruckById(truckId);
    }

    @GetMapping("/truckslist")
    public Iterable<TruckDTO> listOfTrucks(){
        return truckService.getAllTrucks();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createtruck")
    public void createTruck(@RequestBody Truck truck){
        truckService.saveTruck(truck);
    }
}
