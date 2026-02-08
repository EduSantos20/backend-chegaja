package com.edusantos.backend_chegaja.repository;

import com.edusantos.backend_chegaja.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRespository extends JpaRepository<Address, UUID> {
    // Método para encontrar endereços por ID do usuário
    List<Address> findByUserId(UUID userId);

    Optional<Address> findByUserIdAndPrincipal(UUID userId, Boolean principal);
    // Desmarcar endereço principal (caso vá salvar outro como principal)
    @Modifying
    @Query("""
        UPDATE Address a
        SET a.principal = false
        WHERE a.user.id = :userId
    """)
    void desmarcarEnderecosPrincipais(UUID userId);
}
