package com.logistic.company.service;

import com.logistic.company.dto.DriverDTO;
import com.logistic.company.dto.TruckDTO;
import com.logistic.company.entity.City;
import com.logistic.company.entity.Driver;
import com.logistic.company.entity.Truck;
import com.logistic.company.repository.CityRepo;
import com.logistic.company.repository.DriverRepo;
import com.logistic.company.repository.TruckRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Transactional(readOnly = true)
@Service
public class DriverService {
    private final DriverRepo driverRepo;
    private final TruckRepo truckRepo;
    private final CityRepo cityRepo;

    public DriverService(DriverRepo driverRepo, TruckRepo truckRepo, CityRepo cityRepo) {
        this.driverRepo = driverRepo;
        this.truckRepo = truckRepo;
        this.cityRepo = cityRepo;
    }

    public Iterable<DriverDTO> convertAllEntityToDTO(Iterable<Driver> drivers){
        return StreamSupport.stream(drivers.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public DriverDTO convertEntityToDTO(Driver driver){
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(driver.getId());
        driverDTO.setName(driver.getName());
        driverDTO.setSurname(driver.getSurname());
        if(driver.getStatus() != null) {
            driverDTO.setStatus(driver.getStatus().toString());
        } else {
            driverDTO.setStatus("");
        }
        driverDTO.setPersonalNumber(driver.getPersonalNumber());
        driverDTO.setWorkingHours(driver.getWorkingHours());
        if(driver.getCurrentCity() != null) {
            driverDTO.setCurrentCity(driver.getCurrentCity().getName());
        } else {
            driverDTO.setCurrentCity("");
        }
        if(driver.getCurrentTruck() != null) {
            TruckDTO truckDTO = new TruckDTO();
            truckDTO.setNumber(driver.getCurrentTruck().getNumber());
            driverDTO.setCurrentTruck(truckDTO);
        } else {
            driverDTO.setCurrentTruck(new TruckDTO());
        }
        return driverDTO;
    }

    public DriverDTO getEmptyDTO(){
        DriverDTO cityDTO = new DriverDTO();
        cityDTO.setId(0L);
        cityDTO.setName("");
        return  cityDTO;
    }

    public DriverDTO getDraiverById(Long id){
        Driver driver = driverRepo.findFirstById(id);
        if(driver != null){
            return convertEntityToDTO(driver);
        }
        return getEmptyDTO();
    }

    public Iterable<DriverDTO> getAllDraivers(){
        return convertAllEntityToDTO(driverRepo.findAll());
    }

    public DriverDTO getDriverByName(String name, String surname){
        Driver driver = driverRepo.findFirstByNameAndSurname(name, surname);
        if(driver != null){
            return convertEntityToDTO(driver);
        }
        return getEmptyDTO();
    }

    @Transactional
    public void saveDriver(String name, String surname, String personalNumber, Integer workingHours, String status, City currentCity, String currentTruckNumber){
        Truck truck = truckRepo.findFirstByNumber(currentTruckNumber);
        driverRepo.save(new Driver(name, surname, personalNumber, workingHours, status, currentCity, truck));
    }

    @Transactional
    public void saveDriver(Driver driver){
        if(driver.getCurrentTruck() != null &&
                driver.getCurrentTruck().getId() == null){
            Truck truck = truckRepo.findFirstByNumber(driver.getCurrentTruck().getNumber());
            if(truck != null){
                driver.setCurrentTruck(truck);
            }
        }
        if(driver.getCurrentCity() != null &&
                driver.getCurrentCity().getId() == null){
            City city = cityRepo.findFirstByName(driver.getCurrentCity().getName());
            if(city != null){
                driver.setCurrentCity(city);
            }
        }
        driverRepo.save(driver);
    }
}
