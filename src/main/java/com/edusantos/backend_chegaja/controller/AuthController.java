package com.edusantos.backend_chegaja.controller;


import com.edusantos.backend_chegaja.dto.AuthDTO;
import com.edusantos.backend_chegaja.entity.User;
import com.edusantos.backend_chegaja.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint para registro
    @PostMapping("/register")
    public ResponseEntity<AuthDTO.AuthResponse> register(
            @Valid @RequestBody AuthDTO.RegisterRequest registerRequest) {
        AuthDTO.AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<AuthDTO.AuthResponse> login(
            @Valid @RequestBody AuthDTO.LoginRequest loginRequest) {
        AuthDTO.AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }


    //lista usuários
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }
}

