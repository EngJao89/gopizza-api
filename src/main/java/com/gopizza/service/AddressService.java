package com.gopizza.service;

import com.gopizza.dto.AddressResponseDTO;
import com.gopizza.dto.CreateAddressDTO;
import com.gopizza.model.Address;
import com.gopizza.model.User;
import com.gopizza.repository.AddressRepository;
import com.gopizza.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public AddressResponseDTO createAddress(UUID userId, CreateAddressDTO dto) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado com ID: " + userId));

		Address address = new Address();
		address.setUser(user);
		address.setStreet(dto.getRua().trim());
		address.setNumberReference(dto.getNumero().trim());
		address.setNeighborhood(dto.getBairro().trim());
		address.setZipCode(dto.getCep().trim());
		address.setCity(dto.getCidade().trim());
		address.setState(dto.getEstado().trim());
		address.setCountry(dto.getPais().trim());
		address.setComplement(dto.getComplemento() != null ? dto.getComplemento().trim() : null);

		Address savedAddress = addressRepository.save(address);
		return toResponse(savedAddress);
	}

	@Transactional(readOnly = true)
	public List<AddressResponseDTO> getAddressesByUserId(UUID userId) {
		if (!userRepository.existsById(userId)) {
			throw new IllegalArgumentException("Usuario nao encontrado com ID: " + userId);
		}
		return addressRepository.findByUserId(userId).stream()
				.map(this::toResponse)
				.toList();
	}

	private AddressResponseDTO toResponse(Address address) {
		AddressResponseDTO dto = new AddressResponseDTO();
		dto.setId(address.getId());
		dto.setUserId(address.getUser().getId());
		dto.setRua(address.getStreet());
		dto.setNumero(address.getNumberReference());
		dto.setBairro(address.getNeighborhood());
		dto.setCep(address.getZipCode());
		dto.setCidade(address.getCity());
		dto.setEstado(address.getState());
		dto.setPais(address.getCountry());
		dto.setComplemento(address.getComplement());
		dto.setCreatedAt(address.getCreatedAt());
		dto.setUpdatedAt(address.getUpdatedAt());
		return dto;
	}
}
