package com.logistic.company.repository;

import com.logistic.company.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepo extends JpaRepository<Driver, Long> {
    Driver findFirstById(Long id);
    Driver findFirstByNameAndSurname(String name, String surname);
}
