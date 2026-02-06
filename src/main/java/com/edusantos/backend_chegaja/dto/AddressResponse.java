package com.edusantos.backend_chegaja.dto;

import java.time.LocalDateTime;

public record AddressResponse(
        Long id,
        Long userId,
        String endereco,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Boolean principal,
        LocalDateTime createdAt
) {}
