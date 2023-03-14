package com.logistic.company.repository;

import com.logistic.company.entity.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaypointRepo extends JpaRepository<Waypoint, Long> {
    Waypoint findFirstById(Long id);
}
