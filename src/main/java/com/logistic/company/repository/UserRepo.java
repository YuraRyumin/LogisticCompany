package com.logistic.company.repository;

import com.logistic.company.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Long> {
    User findFirstByLogin(String login);
    User findFirstById(Long id);
}
