package com.logistic.company.controller;

import com.logistic.company.dto.CityDTO;
import com.logistic.company.entity.City;
import com.logistic.company.service.CityService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/citys")
    public CityDTO getCity(@RequestParam Long cityId){
        return cityService.getCityById(cityId);
    }

    @GetMapping("/cityslist")
    public Iterable<CityDTO> listOfCitys(){
        return cityService.getAllCitys();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createcity")
    public void createCity(@RequestBody City city){
        cityService.saveCity(city);
    }
}
