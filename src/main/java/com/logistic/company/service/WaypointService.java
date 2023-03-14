package com.logistic.company.service;

import com.logistic.company.dto.WaypointDTO;
import com.logistic.company.entity.*;
import com.logistic.company.repository.OrderRepo;
import com.logistic.company.repository.TruckRepo;
import com.logistic.company.repository.WaypointRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Transactional(readOnly = true)
@Service
public class WaypointService {
    private final WaypointRepo waypointRepo;
    private final OrderRepo orderRepo;
    private final TruckRepo truckRepo;

    public WaypointService(WaypointRepo waypointRepo, OrderRepo orderRepo, TruckRepo truckRepo) {
        this.waypointRepo = waypointRepo;
        this.orderRepo = orderRepo;
        this.truckRepo = truckRepo;
    }

    public Iterable<WaypointDTO> convertAllEntityToDTO(Iterable<Waypoint> waypoints){
        return StreamSupport.stream(waypoints.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public WaypointDTO convertEntityToDTO(Waypoint waypoint){
        WaypointDTO waypointDTO = new WaypointDTO();
        waypointDTO.setId(waypoint.getId());
        if(waypoint.getType() != null) {
            waypointDTO.setType(waypoint.getType());
        } else {
            waypointDTO.setType("");
        }
        if(waypoint.getCity() != null) {
            waypointDTO.setCity(waypoint.getCity().getName());
        } else {
            waypointDTO.setCity("");
        }
        if(waypoint.getTruck() != null) {
            waypointDTO.setTruck(waypoint.getTruck().getNumber());
        } else {
            waypointDTO.setTruck("");
        }
        if(waypoint.getCargo() != null) {
            waypointDTO.setCargo(waypoint.getCargo().getName());
        } else {
            waypointDTO.setCargo("");
        }
        if(waypoint.getOrder() != null) {
            waypointDTO.setOrder(waypoint.getOrder().getUniqueNumber());
        } else {
            waypointDTO.setOrder("");
        }
        return waypointDTO;
    }

    public WaypointDTO getEmptyDTO(){
        WaypointDTO waypointDTO = new WaypointDTO();
        waypointDTO.setId(0L);
        waypointDTO.setOrder("");
        waypointDTO.setCity("");
        waypointDTO.setCargo("");
        waypointDTO.setType("");
        waypointDTO.setTruck("");
        return  waypointDTO;
    }

    public Iterable<WaypointDTO> getAllWaypoints(){
        return convertAllEntityToDTO(waypointRepo.findAll());
    }

    public WaypointDTO getWaypointById(Long id){
        Waypoint waypoint = waypointRepo.findFirstById(id);
        if(waypoint != null){
            return convertEntityToDTO(waypoint);
        }
        return getEmptyDTO();
    }

//    public WaypointDTO getWaypointByName(String name){
//        Waypoint waypoint = waypointRepo.findFirstByName(name);
//        if(waypoint != null){
//            return convertEntityToDTO(waypoint);
//        }
//        return getEmptyDTO();
//    }

    @Transactional
    public void saveWaypoint(String orderNumber, City city, Cargo cargo, String type, String truckNumber){
        Order order = orderRepo.findFirstByUniqueNumber(orderNumber);
        Truck truck = truckRepo.findFirstByNumber(truckNumber);
        waypointRepo.save(new Waypoint(order, city, cargo, type, truck));
    }

    @Transactional
    public void saveWaypoint(Waypoint waypoint){
        waypointRepo.save(waypoint);
    }
}
