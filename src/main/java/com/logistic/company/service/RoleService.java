package com.logistic.company.service;

import com.logistic.company.dto.RoleDTO;
import com.logistic.company.entity.Role;
import com.logistic.company.repository.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Transactional(readOnly = true)
@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Iterable<RoleDTO> convertAllEntityToDTO(Iterable<Role> roles){
        return StreamSupport.stream(roles.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public RoleDTO convertEntityToDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    public RoleDTO getEmptyDTO(){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(0L);
        roleDTO.setName("");
        return  roleDTO;
    }

    public Iterable<RoleDTO> getAllRoles(){
        return convertAllEntityToDTO(roleRepo.findAll());
    }

    public RoleDTO getRoleById(Long id){
        Role role = roleRepo.findFirstById(id);
        if(role != null){
            return convertEntityToDTO(role);
        }
        return getEmptyDTO();
    }

    public RoleDTO getRoleByName(String name){
        Role role = roleRepo.findFirstByName(name);
        if(role != null){
            return convertEntityToDTO(role);
        }
        return getEmptyDTO();
    }

    @Transactional
    public void saveRole(String name){
        roleRepo.save(new Role(name));
    }

    @Transactional
    public void saveRole(Role role){
        roleRepo.save(role);
    }
}
