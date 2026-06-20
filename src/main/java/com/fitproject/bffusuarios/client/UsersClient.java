package com.fitproject.bffusuarios.client;

import com.fitproject.bffusuarios.dto.AuthResponseMs;
import com.fitproject.bffusuarios.dto.LoginRequest;
import com.fitproject.bffusuarios.dto.UserDTO;
import com.fitproject.bffusuarios.dto.CreateUserRequestBff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-users", url = "${ms.users.url}")
public interface UsersClient {

    @PostMapping("/api/v1/users/authenticate")
    AuthResponseMs authenticate(@RequestBody LoginRequest request);

    @GetMapping("/api/v1/users")
    List<UserDTO> getAllUsers();

    @GetMapping("/api/v1/users/workers")
    List<UserDTO> getWorkers();

    @PostMapping("/api/v1/users")
    UserDTO createUser(@RequestBody CreateUserRequestBff request);

    @PatchMapping("/api/v1/users/{userId}/role")
    UserDTO updateRole(@PathVariable("userId") String userId,
                       @RequestParam("role") String role);

    @PatchMapping("/api/v1/users/{userId}/toggle")
    UserDTO toggleActive(@PathVariable("userId") String userId);
}
