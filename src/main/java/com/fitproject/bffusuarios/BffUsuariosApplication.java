package com.fitproject.bffusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BffUsuariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(BffUsuariosApplication.class, args);
    }
}
