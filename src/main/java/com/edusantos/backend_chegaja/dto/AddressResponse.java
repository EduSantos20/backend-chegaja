package com.edusantos.backend_chegaja.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressResponse(
        UUID id,
        UUID userId,
        String endereco,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Boolean principal,
        LocalDateTime createdAt
) {}
