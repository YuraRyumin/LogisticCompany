package com.logistic.company.service;

import com.logistic.company.dto.UserDTO;
import com.logistic.company.entity.Role;
import com.logistic.company.entity.User;
import com.logistic.company.repository.RoleRepo;
import com.logistic.company.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public Iterable<UserDTO> convertAllEntityToDTO(Iterable<User> users){
        return StreamSupport.stream(users.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public UserDTO convertEntityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setUuid(user.getUuid());
        userDTO.setActive(user.getActive());
        if(user.getRole() != null) {
            userDTO.setRole(user.getRole().getName());
        } else {
            userDTO.setRole("");
        }
        userDTO.setActivationCode(user.getActivationCode());
        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findFirstByLogin(username);
    }

    public UserDTO getEmptyDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0L);
        userDTO.setUuid("");
        userDTO.setEmail("");
        userDTO.setPhone("");
        userDTO.setRole(null);
        userDTO.setLogin("");
        userDTO.setPassword("");
        userDTO.setActive(false);
        userDTO.setActivationCode("");
        return  userDTO;
    }

    public Iterable<UserDTO> getAllUsers(){
        return convertAllEntityToDTO(userRepo.findAll());
    }

    public UserDTO getUserById(Long id){
        User user = userRepo.findFirstById(id);
        if(user != null){
            return convertEntityToDTO(user);
        }
        return getEmptyDTO();
    }

    public UserDTO getUserByLogin(String login){
        User user = userRepo.findFirstByLogin(login);
        if(user != null){
            return convertEntityToDTO(user);
        }
        return getEmptyDTO();
    }

    @Transactional
    public void saveUser(String uuid,
                         String email,
                         String phone,
                         String roleName,
                         String login,
                         String password,
                         boolean active,
                         String activationCode){
        Role role = roleRepo.findFirstByName(roleName);
        userRepo.save(new User(uuid, email, phone, role, login, password, active, activationCode));
    }

    @Transactional
    public void saveUser(User user){
        Role role = roleRepo.findFirstByName(user.getRole().getName());
        if(role != null){
            user.setRole(role);
        }
        userRepo.save(user);
    }
}
