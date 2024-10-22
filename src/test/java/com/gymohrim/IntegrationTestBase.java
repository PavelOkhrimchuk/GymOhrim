package com.gymohrim;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Sql({
        "classpath:sql/data.sql"
})

@Testcontainers
public abstract class IntegrationTestBase {
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    static {
        container.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}
