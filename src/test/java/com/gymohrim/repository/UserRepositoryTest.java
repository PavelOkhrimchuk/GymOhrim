package com.gymohrim.repository;

import com.gymohrim.IntegrationTestBase;
import com.gymohrim.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;



    @Test
    void testFindAllUsers() {

        List<User> users = userRepository.findAll();


        assertThat(users).isNotEmpty();
        assertThat(users.size()).isGreaterThan(0);


        User user = users.get(0);
        assertThat(user.getEmail()).isIn("test1@example.com", "test2@example.com", "test3@example.com");
        assertThat(user.getName()).isIn("Test User 1", "Test User 2", "Test User 3");
    }

}