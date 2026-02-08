package com.edusantos.backend_chegaja.service;

import com.edusantos.backend_chegaja.dto.AddressResponse;
import com.edusantos.backend_chegaja.entity.Address;
import com.edusantos.backend_chegaja.entity.User;
import com.edusantos.backend_chegaja.exception.ResourceNotFoundException;
import com.edusantos.backend_chegaja.repository.AddressRespository;
import com.edusantos.backend_chegaja.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final UserRepository userRepository;
    private final AddressRespository addressRespository;

    public AddressService( UserRepository userRepository, AddressRespository addressRespository) {
        this.userRepository = userRepository;
        this.addressRespository = addressRespository;
    }

    // Criar endereço para o usuário
    @Transactional
    public Address create(UUID userId, Address address){
        User user = (User) userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        address.setUser(user);

        // se quiser garantir só um endereço principal
        if (Boolean.TRUE.equals(address.getPrincipal())) {
            addressRespository.desmarcarEnderecosPrincipais(user.getId());
        }

        return addressRespository.save(address);
    }

    // Listar endereços do usuário
    public List<Address> listByUser(UUID userId){
        return addressRespository.findByUserId(userId);
    }

    // Buscar endereço principal
    public Optional<Address> getPrincipal(UUID userId){
        return addressRespository.findByUserIdAndPrincipal(userId, true);
    }

    // Deletar endereço
    @Transactional
    public void delete(UUID userId, UUID addressId){
        Address address = addressRespository.findById(addressId)
                .filter(a -> a.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        addressRespository.delete(address);
    }

    // Converter entidade para DTO
    public AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getUser().getId(),
                address.getEndereco(),
                address.getComplemento(),
                address.getBairro(),
                address.getCidade(),
                address.getEstado(),
                address.getCep(),
                address.getPrincipal(),
                address.getCreatedAt()
        );
    }
}
