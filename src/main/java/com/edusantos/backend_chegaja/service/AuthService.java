package com.edusantos.backend_chegaja.service;

import com.edusantos.backend_chegaja.dto.AuthDTO;
import com.edusantos.backend_chegaja.entity.User;
import com.edusantos.backend_chegaja.exception.ResourceAlreadyExistsException;
import com.edusantos.backend_chegaja.repository.UserRepository;
import com.edusantos.backend_chegaja.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthService {


    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    // Método para registro de usuário
    @Transactional
    public AuthDTO.AuthResponse register(AuthDTO.RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Email já está em uso!");
        }

        // Criar usuário
        User user = User.builder()
                .nome(registerRequest.getNome())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .telefone(registerRequest.getTelefone())
                .roles(new HashSet<>(Set.of("ROLE_USER")))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        user = userRepository.save(user);

        // Gerar token JWT
        String jwt = jwtUtils.generateTokenFromUsername(user.getEmail());
        
        // Retornar resposta com token e informações do usuário
        return AuthDTO.AuthResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .nome(user.getNome())
                .roles(user.getRoles())
                .build();
    }

    // Método para login de usuário
    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        // Se a autenticação for bem-sucedida, o contexto de segurança é atualizado
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        // Obter detalhes do usuário autenticado
        User user = (User) authentication.getPrincipal();

        // Retornar resposta com token e informações do usuário
        return AuthDTO.AuthResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .nome(user.getNome())
                .roles(user.getRoles())
                .build();
    }


    public List<User> findAll(){
        return userRepository.findAll();
    }
}

