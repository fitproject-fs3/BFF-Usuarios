package com.fitproject.bffusuarios.client.fallback;

import com.fitproject.bffusuarios.client.UsersClient;
import com.fitproject.bffusuarios.dto.AuthResponseMs;
import com.fitproject.bffusuarios.dto.CreateUserRequestBff;
import com.fitproject.bffusuarios.dto.LoginRequest;
import com.fitproject.bffusuarios.dto.UserDTO;
import feign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Fallback Factory para {@link UsersClient} del BFF-Usuarios.
 *
 * <p>Proporciona degradación segura cuando MS-Users no está disponible.
 * La autenticación lanza excepción para evitar accesos silenciosos con servicio caído.</p>
 *
 * @see UsersClient
 */
@Slf4j
@Component
public class UsersClientFallbackFactory implements FallbackFactory<UsersClient> {

    /**
     * Crea una instancia fallback de {@link UsersClient} registrando el fallo
     * y aplicando degradación segura por método.
     *
     * @param cause excepción que activó el circuit breaker
     * @return implementación de degradación de {@link UsersClient}
     */
    @Override
    public UsersClient create(Throwable cause) {
        log.error("[CircuitBreaker] MS-Users (BFF-Usuarios) no disponible: {}", cause.getMessage());
        return new UsersClient() {

            @Override
            public AuthResponseMs authenticate(LoginRequest request) {
                throw new IllegalStateException("Servicio de autenticación no disponible. Intenta de nuevo más tarde.");
            }

            @Override
            public List<UserDTO> getAllUsers() {
                log.warn("[Fallback] getAllUsers → lista vacía");
                return Collections.emptyList();
            }

            @Override
            public List<UserDTO> getWorkers() {
                log.warn("[Fallback] getWorkers → lista vacía");
                return Collections.emptyList();
            }

            @Override
            public UserDTO createUser(CreateUserRequestBff request) {
                log.warn("[Fallback] createUser → null");
                return null;
            }

            @Override
            public UserDTO updateRole(String userId, String role) {
                log.warn("[Fallback] updateRole({}) → null", userId);
                return null;
            }

            @Override
            public UserDTO toggleActive(String userId) {
                log.warn("[Fallback] toggleActive({}) → null", userId);
                return null;
            }
        };
    }
}
