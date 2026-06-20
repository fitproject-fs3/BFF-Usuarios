package com.fitproject.bffusuarios.dto;

import lombok.Data;

@Data
public class AuthResponseMs {
    private String userId;
    private String email;
    private String fullName;
    private String role;
    private Boolean active;
}
