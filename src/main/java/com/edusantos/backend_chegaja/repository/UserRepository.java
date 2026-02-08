package com.edusantos.backend_chegaja.repository;

import com.edusantos.backend_chegaja.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Método para encontrar usuário por email
    Optional<User> findByEmail(String email);

    // Método para encontrar usuário por ID
    boolean existsByEmail(String email);
}
