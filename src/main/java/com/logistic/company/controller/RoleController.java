package com.logistic.company.controller;

import com.logistic.company.dto.RoleDTO;
import com.logistic.company.entity.Role;
import com.logistic.company.service.RoleService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RoleController {
    private final RoleService roleService;


    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public RoleDTO getRole(@RequestParam Long Id){
        return roleService.getRoleById(Id);
    }

    @GetMapping("/roleslist")
    public Iterable<RoleDTO> listOfRoles(){
        return roleService.getAllRoles();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createrole")
    public void createRole(@RequestBody Role role){
        roleService.saveRole(role);
    }
}
