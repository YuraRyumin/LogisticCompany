package com.logistic.company.repository;

import com.logistic.company.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findFirstById(Long id);
    Role findFirstByName(String name);
}
