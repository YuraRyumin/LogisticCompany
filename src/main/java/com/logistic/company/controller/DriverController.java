package com.logistic.company.controller;

import com.logistic.company.dto.DriverDTO;
import com.logistic.company.entity.Driver;
import com.logistic.company.service.DriverService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/drivers")
    public DriverDTO getDriver(@RequestParam Long id){
        return driverService.getDraiverById(id);
    }

    @GetMapping("/driverslist")
    public Iterable<DriverDTO> listOfDrivers(){
        return driverService.getAllDraivers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createdriver")
    public void createDriver(@RequestBody Driver driver){
        driverService.saveDriver(driver);
    }
}
