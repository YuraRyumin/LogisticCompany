package com.logistic.company.repository;

import com.logistic.company.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Order findFirstById(Long id);
    Order findFirstByUniqueNumber(String uniqueNumber);
}
