package com.logistic.company.controller;

import com.logistic.company.dto.WaypointDTO;
import com.logistic.company.entity.Waypoint;
import com.logistic.company.service.WaypointService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class WaypointController {
    private final WaypointService waypointService;

    public WaypointController(WaypointService waypointService) {
        this.waypointService = waypointService;
    }

    @GetMapping("/waypoints")
    public WaypointDTO getWaypointById(@RequestParam Long id){
        return waypointService.getWaypointById(id);
    }

    @GetMapping("/waypointslist")
    @PreAuthorize("hasAuthority('admin')")
    public Iterable<WaypointDTO> listOfWaypoints(){
        return waypointService.getAllWaypoints();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createwaypoint")
    public void createWaypoint(@RequestBody Waypoint waypoint){
        waypointService.saveWaypoint(waypoint);
    }
}
