package com.logistic.company.repository;

import com.logistic.company.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Long> {
    City findFirstById(Long id);
    City findFirstByName(String name);
}
