package com.edusantos.backend_chegaja.controller;

import com.edusantos.backend_chegaja.dto.AddressResponse;
import com.edusantos.backend_chegaja.entity.Address;
import com.edusantos.backend_chegaja.exception.ResourceNotFoundException;
import com.edusantos.backend_chegaja.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // Criar endereço para o usuário
    @PostMapping("/{userId}")
    public ResponseEntity<Address> create(
            @PathVariable Long userId,
            @RequestBody Address address
    ) {
        Address created = addressService.create(userId, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Listar endereços do usuário
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<AddressResponse>> listByUser(@PathVariable Long userId) {
        List<Address> addresses = addressService.listByUser(userId);
        List<AddressResponse> response = addresses.stream()
                .map(addressService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Buscar endereço principal
    @GetMapping("/users/{userId}/principal")
    public ResponseEntity<AddressResponse> getPrincipalAddress(@PathVariable Long userId) {
        Address principal = addressService.getPrincipal(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço principal não encontrado"));
        return ResponseEntity.ok(addressService.toResponse(principal));
    }

    // Deletar endereço
    @DeleteMapping("/users/{userId}/{addressId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        addressService.delete(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}
