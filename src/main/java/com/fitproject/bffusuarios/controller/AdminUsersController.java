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

/**
 * Controller BFF-Usuarios para la administración de usuarios del sistema FitProject.
 *
 * <p>Actúa como Backend For Frontend para el panel de administración, reenviando
 * las operaciones de gestión de usuarios a MS-Users vía Feign con Circuit Breaker.
 * Todos los endpoints requieren rol {@code ADMIN}.</p>
 *
 * <p>Base URL: {@code /api/v1/admin}</p>
 *
 * @see UsersClient
 * @see com.fitproject.bffusuarios.client.fallback.UsersClientFallbackFactory
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminUsersController {

    private final UsersClient usersClient;

    /**
     * Obtiene la lista completa de usuarios registrados en el sistema.
     *
     * @return lista de todos los usuarios; vacía si MS-Users no está disponible (fallback)
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("GET /api/v1/admin/users");
        return ResponseEntity.ok(usersClient.getAllUsers());
    }

    /**
     * Obtiene solo los usuarios con rol {@code TRABAJADOR}.
     *
     * <p>Utilizado por el panel de supervisor para seleccionar a quién asignar
     * una tarea de construcción.</p>
     *
     * @return lista de trabajadores; vacía si MS-Users no está disponible (fallback)
     */
    @GetMapping("/users/workers")
    public ResponseEntity<List<UserDTO>> getWorkers() {
        log.info("GET /api/v1/admin/users/workers");
        return ResponseEntity.ok(usersClient.getWorkers());
    }

    /**
     * Crea un nuevo usuario en el sistema con el rol y datos especificados.
     *
     * @param request datos del usuario a crear (nombre, email, password, rol)
     * @return usuario creado con status 201 Created
     */
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequestBff request) {
        log.info("POST /api/v1/admin/users email={} role={}", request.getEmail(), request.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(usersClient.createUser(request));
    }

    /**
     * Cambia el rol de un usuario existente.
     *
     * @param userId identificador UUID del usuario
     * @param role   nuevo rol: {@code ADMIN}, {@code SUPERVISOR_OBRA},
     *               {@code TRABAJADOR}, {@code INVERSIONISTA} o {@code VENDEDOR}
     * @return usuario con el rol actualizado
     */
    @PatchMapping("/users/{userId}/role")
    public ResponseEntity<UserDTO> updateRole(
            @PathVariable String userId,
            @RequestParam String role) {
        log.info("PATCH /api/v1/admin/users/{}/role role={}", userId, role);
        return ResponseEntity.ok(usersClient.updateRole(userId, role));
    }

    /**
     * Activa o desactiva un usuario (toggle de estado activo).
     *
     * <p>Un usuario desactivado no puede iniciar sesión en el sistema.</p>
     *
     * @param userId identificador UUID del usuario
     * @return usuario con el estado activo/inactivo alternado
     */
    @PatchMapping("/users/{userId}/toggle")
    public ResponseEntity<UserDTO> toggleActive(@PathVariable String userId) {
        log.info("PATCH /api/v1/admin/users/{}/toggle", userId);
        return ResponseEntity.ok(usersClient.toggleActive(userId));
    }
}
