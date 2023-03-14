package com.logistic.company.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String uuid;
    private String email;
    private String phone;
    private String login;
    private String password;
    private String activationCode;
    private String role;
    private boolean active;
}
