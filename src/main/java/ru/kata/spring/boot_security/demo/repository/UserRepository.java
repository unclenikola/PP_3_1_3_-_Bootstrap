package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}