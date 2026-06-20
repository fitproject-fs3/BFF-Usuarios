package com.fitproject.bffusuarios.dto;

import lombok.Data;

@Data
public class CreateUserRequestBff {
    private String email;
    private String password;
    private String fullName;
    private String role;
    private String specialty;
}
