package com.logistic.company.controller;

import com.logistic.company.dto.OrderDTO;
import com.logistic.company.entity.Order;
import com.logistic.company.service.OrderService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public OrderDTO getOrderById(@RequestParam Long id){
        return orderService.getOrderById(id);
    }

    @GetMapping("/orderslist")
    public Iterable<OrderDTO> listOfOrders(){
        return orderService.getAllOrders();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createorder")
    public void createOrder(@RequestBody Order order){
        orderService.saveOrder(order);
    }
}
