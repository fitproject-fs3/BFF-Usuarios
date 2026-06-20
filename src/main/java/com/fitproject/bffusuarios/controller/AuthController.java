package com.fitproject.bffusuarios.controller;

import com.fitproject.bffusuarios.client.UsersClient;
import com.fitproject.bffusuarios.dto.AuthResponseMs;
import com.fitproject.bffusuarios.dto.LoginRequest;
import com.fitproject.bffusuarios.dto.LoginResponse;
import com.fitproject.bffusuarios.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsersClient usersClient;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponseMs user = usersClient.authenticate(request);
        String token = jwtService.generateToken(
                user.getUserId(), user.getEmail(), user.getFullName(), user.getRole());
        return ResponseEntity.ok(LoginResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build());
    }
}
