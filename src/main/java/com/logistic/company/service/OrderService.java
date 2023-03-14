package com.logistic.company.service;

import com.logistic.company.dto.OrderDTO;
import com.logistic.company.entity.Order;
import com.logistic.company.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Iterable<OrderDTO> convertAllEntityToDTO(Iterable<Order> orders){
        return StreamSupport.stream(orders.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public OrderDTO convertEntityToDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUniqueNumber(order.getUniqueNumber());
        orderDTO.setCompleted(order.isCompleted());
        return orderDTO;
    }

    public OrderDTO getEmptyDTO(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(0L);
        orderDTO.setUniqueNumber("");
        orderDTO.setCompleted(false);
        return  orderDTO;
    }

    public Iterable<OrderDTO> getAllOrders(){
        return convertAllEntityToDTO(orderRepo.findAll());
    }

    public OrderDTO getOrderById(Long id){
        Order order = orderRepo.findFirstById(id);
        if(order != null){
            return convertEntityToDTO(order);
        }
        return getEmptyDTO();
    }

    public OrderDTO getOrderByName(String uniqueNumber){
        Order order = orderRepo.findFirstByUniqueNumber(uniqueNumber);
        if(order != null){
            return convertEntityToDTO(order);
        }
        return getEmptyDTO();
    }

    @Transactional
    public void saveOrder(String uniqueNumber, boolean completed){
        orderRepo.save(new Order(uniqueNumber, completed));
    }

    @Transactional
    public void saveOrder(Order order){
        orderRepo.save(order);
    }
}
