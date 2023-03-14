package com.logistic.company.controller;

import com.logistic.company.dto.UserDTO;
import com.logistic.company.entity.User;
import com.logistic.company.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public UserDTO getUserByID(@RequestParam Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/userslist")
    public Iterable<UserDTO> listOfUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createuser")
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }
}
