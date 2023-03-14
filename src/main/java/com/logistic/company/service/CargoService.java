package com.logistic.company.service;

import com.logistic.company.dto.CargoDTO;
import com.logistic.company.entity.Cargo;
import com.logistic.company.entity.City;
import com.logistic.company.repository.CargoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Transactional(readOnly = true)
@Service
public class CargoService {
    private final CargoRepo cargoRepo;

    public CargoService(CargoRepo cargoRepo) {
        this.cargoRepo = cargoRepo;
    }

    public Iterable<CargoDTO> convertAllEntityToDTO(Iterable<Cargo> cargos){
        return StreamSupport.stream(cargos.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public CargoDTO convertEntityToDTO(Cargo cargo){
        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setId(cargo.getId());
        cargoDTO.setName(cargo.getName());
        cargoDTO.setWeight(cargo.getWeight());
        cargoDTO.setStatus(cargo.getStatus());
        return cargoDTO;
    }

    public CargoDTO getEmptyDTO(){
        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setId(0L);
        cargoDTO.setName("");
        cargoDTO.setWeight(0);
        cargoDTO.setStatus("");
        return cargoDTO;
    }

    @Transactional
    public void saveCargo(Long cargoId, String name, Integer weight, String status){
        if (cargoId.equals(0)) {
            Cargo cargo = cargoRepo.findFirstById(cargoId);
            cargo.setName(name);
            cargo.setWeight(weight);
            cargo.setStatus(status);
            cargoRepo.save(cargo);
        }
    }

    public CargoDTO getCargoById(Long id){
        Cargo cargo = cargoRepo.findFirstById(id);
        if(cargo != null){
            return convertEntityToDTO(cargo);
        }
        return getEmptyDTO();
    }

    public Iterable<CargoDTO> getAllCargos(){
        return convertAllEntityToDTO(cargoRepo.findAll());
    }

    public CargoDTO getCargoByName(String name){
        Cargo cargo = cargoRepo.findFirstByName(name);
        if(cargo != null){
            return convertEntityToDTO(cargo);
        }
        return getEmptyDTO();
    }

    @Transactional
    public void saveCargo(String name, Integer weight, String status){
        cargoRepo.save(new Cargo(name, weight, status));
    }

    @Transactional
    public void saveCargo(Cargo cargo){
        cargoRepo.save(cargo);
    }
}
