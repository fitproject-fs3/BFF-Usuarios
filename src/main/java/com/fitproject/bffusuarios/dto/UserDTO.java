package com.fitproject.bffusuarios.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String userId;
    private String email;
    private String fullName;
    private String role;
    private String specialty;
    private Boolean active;
    private LocalDateTime createdAt;
}
