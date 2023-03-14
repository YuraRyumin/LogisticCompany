package com.logistic.company.service;

import com.logistic.company.dto.CityDTO;
import com.logistic.company.entity.City;
import com.logistic.company.repository.CityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Transactional(readOnly = true)
@Service
public class CityService {
    private final CityRepo cityRepo;

    public CityService(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    public Iterable<CityDTO> convertAllEntityToDTO(Iterable<City> cities){
        return StreamSupport.stream(cities.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public CityDTO convertEntityToDTO(City city){
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        return cityDTO;
    }

    public CityDTO getEmptyDTO(){
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(0L);
        cityDTO.setName("");
        return  cityDTO;
    }

    public CityDTO getCityById(Long id){
        City city = cityRepo.findFirstById(id);
        if(city != null){
            return convertEntityToDTO(city);
        }
        return getEmptyDTO();
    }

    public Iterable<CityDTO> getAllCitys(){
        return convertAllEntityToDTO(cityRepo.findAll());
    }

    public CityDTO getCityByName(String name){
        City city = cityRepo.findFirstByName(name);
        if(city != null){
            return convertEntityToDTO(city);
        }
        return getEmptyDTO();
    }

    @Transactional
    public void saveCity(String name){
        cityRepo.save(new City(name));
    }

    @Transactional
    public void saveCity(City city){
        cityRepo.save(city);
    }
}
