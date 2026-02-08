package com.edusantos.backend_chegaja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class AuthDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "Senha é obrigatória")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegisterRequest {
        @NotBlank(message = "Nome é obrigatório")
        private String nome;

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        private String password;

        private String telefone;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthResponse {
        private String token;
        private String type = "Bearer";
        private UUID id;
        private String email; //
        private String nome;
        private java.util.Set<String> roles;
    }
}
