package com.logistic.company.repository;

import com.logistic.company.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepo extends JpaRepository<Cargo, Long> {
    Cargo findFirstById(Long i);
    Cargo findFirstByName(String name);
}
