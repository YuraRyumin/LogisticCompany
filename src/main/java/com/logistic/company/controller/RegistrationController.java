package com.logistic.company.controller;

import com.logistic.company.entity.User;
import com.logistic.company.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;


    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){
        return "a";//userService.saveUser(user);
    }
}
