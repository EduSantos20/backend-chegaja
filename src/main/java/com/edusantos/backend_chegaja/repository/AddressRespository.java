package com.edusantos.backend_chegaja.repository;

import com.edusantos.backend_chegaja.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRespository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);

    Optional<Address> findByUserIdAndPrincipal(Long userId, Boolean principal);
    // Desmarcar endereço principal (caso vá salvar outro como principal)
    @Modifying
    @Query("""
        UPDATE Address a
        SET a.principal = false
        WHERE a.user.id = :userId
    """)
    void desmarcarEnderecosPrincipais(Long userId);
}
