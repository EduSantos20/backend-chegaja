package com.edusantos.backend_chegaja.repository;

import com.edusantos.backend_chegaja.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

Optional<User> findByEmail(String email);

boolean existsByEmail(String email);
}
