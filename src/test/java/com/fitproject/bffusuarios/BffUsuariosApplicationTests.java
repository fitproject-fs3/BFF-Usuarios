package com.fitproject.bffusuarios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "ms.users.url=http://localhost:8090",
        "jwt.secret=test-secret-key-at-least-32-chars-long"
})
class BffUsuariosApplicationTests {
    @Test
    void contextLoads() {}
}
