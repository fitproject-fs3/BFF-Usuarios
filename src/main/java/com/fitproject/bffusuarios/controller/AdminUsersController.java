package com.fitproject.bffusuarios.controller;

import com.fitproject.bffusuarios.client.UsersClient;
import com.fitproject.bffusuarios.dto.CreateUserRequestBff;
import com.fitproject.bffusuarios.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminUsersController {

    private final UsersClient usersClient;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("GET /api/v1/admin/users");
        return ResponseEntity.ok(usersClient.getAllUsers());
    }

    @GetMapping("/users/workers")
    public ResponseEntity<List<UserDTO>> getWorkers() {
        log.info("GET /api/v1/admin/users/workers");
        return ResponseEntity.ok(usersClient.getWorkers());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequestBff request) {
        log.info("POST /api/v1/admin/users email={} role={}", request.getEmail(), request.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(usersClient.createUser(request));
    }

    @PatchMapping("/users/{userId}/role")
    public ResponseEntity<UserDTO> updateRole(
            @PathVariable String userId,
            @RequestParam String role) {
        log.info("PATCH /api/v1/admin/users/{}/role role={}", userId, role);
        return ResponseEntity.ok(usersClient.updateRole(userId, role));
    }

    @PatchMapping("/users/{userId}/toggle")
    public ResponseEntity<UserDTO> toggleActive(@PathVariable String userId) {
        log.info("PATCH /api/v1/admin/users/{}/toggle", userId);
        return ResponseEntity.ok(usersClient.toggleActive(userId));
    }
}
