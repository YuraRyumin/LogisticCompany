package com.logistic.company.service;

import com.logistic.company.dto.TruckDTO;
import com.logistic.company.entity.City;
import com.logistic.company.entity.Truck;
import com.logistic.company.repository.CityRepo;
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
public class TruckService {
    private final TruckRepo truckRepo;
    private final CityRepo cityRepo;

    public TruckService(TruckRepo truckRepo, CityRepo cityRepo) {
        this.truckRepo = truckRepo;
        this.cityRepo = cityRepo;
    }

    public Iterable<TruckDTO> convertAllEntityToDTO(Iterable<Truck> trucks){
        return StreamSupport.stream(trucks.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public TruckDTO convertEntityToDTO(Truck truck){
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setId(truck.getId());
        truckDTO.setNumber(truck.getNumber());
        truckDTO.setStatus(truck.isStatus());
        truckDTO.setCapacity(truck.getCapacity());
        if(truck.getCity() != null) {
            truckDTO.setCity(truck.getCity().getName());
        } else {
            truckDTO.setCity("");
        }
        return truckDTO;
    }

    public TruckDTO getEmptyDTO(){
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setId(0L);
        truckDTO.setNumber("");
        truckDTO.setCapacity(0);
        truckDTO.setStatus(false);
        truckDTO.setCity(null);
        return  truckDTO;
    }

    public Iterable<TruckDTO> getAllTrucks(){
        return convertAllEntityToDTO(truckRepo.findAll());
    }

    public TruckDTO getTruckById(Long id){
        Truck truck = truckRepo.findFirstById(id);
        if(truck != null){
            return convertEntityToDTO(truck);
        }
        return getEmptyDTO();
    }

    public TruckDTO getTruckByName(String number){
        Truck truck = truckRepo.findFirstByNumber(number);
        if(truck != null){
            return convertEntityToDTO(truck);
        }
        return getEmptyDTO();
    }

    @Transactional
    public void saveTruck(String number, Integer capacity, boolean status, String cityName){
        City city = cityRepo.findFirstByName(cityName);
        truckRepo.save(new Truck(number, capacity, status, city));
    }

    @Transactional
    public void saveTruck(Truck truck){
        City city = cityRepo.findFirstByName(truck.getCity().getName());
        if(city != null){
            truck.setCity(city);
        }
        truckRepo.save(truck);
    }
}
