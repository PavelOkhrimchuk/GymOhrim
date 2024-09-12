package com.gymohrim.repository;

import com.gymohrim.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail (String email);

    Optional<User> findByName(String name);

    boolean existsByName(String name);
    boolean existsByEmail(String email);
}