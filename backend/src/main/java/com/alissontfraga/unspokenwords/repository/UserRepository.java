package com.alissontfraga.unspokenwords.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alissontfraga.unspokenwords.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}