package com.logistic.company.repository;

import com.logistic.company.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepo extends JpaRepository<Truck, Long> {
    Truck findFirstById(Long id);
    Truck findFirstByNumber(String number);
}
