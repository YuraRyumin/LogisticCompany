package com.logistic.company.service;

import com.logistic.company.dto.UserDTO;
import com.logistic.company.entity.Role;
import com.logistic.company.entity.User;
import com.logistic.company.repository.RoleRepo;
import com.logistic.company.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@Slf4j
//@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService {
    private UserRepo repository;
    private RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo repository, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return this.repository.findAll();
    }

    public User getByLogin(String login) {
        return this.repository.findFirstByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findFirstByLogin(username);
    }

    public UserDTO getUserById(Long id){
        User user = repository.findFirstById(id);
        if(user != null){
            return convertEntityToDTO(user);
        }
        return getEmptyDTO();
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

    public Iterable<UserDTO> getAllUsers(){
        return convertAllEntityToDTO(repository.findAll());
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
        repository.save(new User(uuid, email, phone, role, login, password, active, activationCode));
    }

    @Transactional
    public void saveUser(User user){
        Role role = roleRepo.findFirstByName(user.getRole().getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(role != null){
            user.setRole(role);
        }
        repository.save(user);
    }

    @Transactional
    public String addUser(User user, Map<String, Object> model) {
        User userFromDB = repository.findFirstByLogin(user.getLogin());
        if(userFromDB != null){
            model.put("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepo.findFirstByName("user"));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setUuid(UUID.randomUUID().toString());
        repository.save(user);
//        if(user.getEmail() != ""){
//            String message = String.format(
//                    "Hello, %s! \n" +
//                            "Welcom to Trains. Please visit next link: http://localhost:8080/activate/%s",
//                    user.getLogin(), user.getActivationCode()
//            );
//            mailSender.send(user.getEmail(), "Activation", message);
//        }
        return "redirect:/login";
    }
}
