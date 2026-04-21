package com.gopizza.service;

import com.gopizza.dto.AddressResponseDTO;
import com.gopizza.dto.CreateAddressDTO;
import com.gopizza.dto.UpdateAddressDTO;
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

		AddressData data = AddressData.fromCreateDTO(dto);
		validateDuplicateAddress(userId, null, data);

		Address address = new Address();
		address.setUser(user);
		applyAddressData(address, data);

		Address savedAddress = addressRepository.save(address);
		return toResponse(savedAddress);
	}

	@Transactional
	public AddressResponseDTO updateAddress(UUID userId, UUID addressId, CreateAddressDTO dto) {
		Address address = getAddressByIdAndUserId(userId, addressId);

		AddressData data = AddressData.fromCreateDTO(dto);
		validateDuplicateAddress(userId, addressId, data);
		applyAddressData(address, data);

		return toResponse(addressRepository.save(address));
	}

	@Transactional
	public AddressResponseDTO updateAddressPartial(UUID userId, UUID addressId, UpdateAddressDTO dto) {
		Address address = getAddressByIdAndUserId(userId, addressId);

		AddressData data = AddressData.fromPatchDTO(dto, address);
		validateDuplicateAddress(userId, addressId, data);
		applyAddressData(address, data);

		return toResponse(addressRepository.save(address));
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

	@Transactional
	public void deleteAddress(UUID userId, UUID addressId) {
		Address address = getAddressByIdAndUserId(userId, addressId);
		addressRepository.delete(address);
	}

	private Address getAddressByIdAndUserId(UUID userId, UUID addressId) {
		if (!userRepository.existsById(userId)) {
			throw new IllegalArgumentException("Usuario nao encontrado com ID: " + userId);
		}

		return addressRepository.findByIdAndUserId(addressId, userId)
				.orElseThrow(() -> new IllegalArgumentException("Endereco nao encontrado para usuario informado"));
	}

	private void validateDuplicateAddress(UUID userId, UUID currentAddressId, AddressData data) {
		boolean duplicateExists = addressRepository.findByUserId(userId).stream()
				.filter(existing -> currentAddressId == null || !existing.getId().equals(currentAddressId))
				.anyMatch(existing ->
						normalize(existing.getStreet()).equals(normalize(data.street))
								&& normalize(existing.getNumberReference()).equals(normalize(data.number))
								&& normalize(existing.getNeighborhood()).equals(normalize(data.neighborhood))
								&& normalize(existing.getZipCode()).equals(normalize(data.zipCode))
								&& normalize(existing.getCity()).equals(normalize(data.city))
								&& normalize(existing.getState()).equals(normalize(data.state))
								&& normalize(existing.getCountry()).equals(normalize(data.country))
								&& normalize(existing.getComplement()).equals(normalize(data.complement))
				);

		if (duplicateExists) {
			throw new IllegalArgumentException("Endereco ja cadastrado para este usuario");
		}
	}

	private String normalize(String value) {
		if (value == null) {
			return "";
		}
		return value.trim().toLowerCase();
	}

	private void applyAddressData(Address address, AddressData data) {
		address.setStreet(data.street);
		address.setNumberReference(data.number);
		address.setNeighborhood(data.neighborhood);
		address.setZipCode(data.zipCode);
		address.setCity(data.city);
		address.setState(data.state);
		address.setCountry(data.country);
		address.setComplement(data.complement);
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

	private static final class AddressData {
		private String street;
		private String number;
		private String neighborhood;
		private String zipCode;
		private String city;
		private String state;
		private String country;
		private String complement;

		private static AddressData fromCreateDTO(CreateAddressDTO dto) {
			AddressData data = new AddressData();
			data.street = dto.getRua().trim();
			data.number = dto.getNumero().trim();
			data.neighborhood = dto.getBairro().trim();
			data.zipCode = dto.getCep().trim();
			data.city = dto.getCidade().trim();
			data.state = dto.getEstado().trim();
			data.country = dto.getPais().trim();
			data.complement = dto.getComplemento() != null ? dto.getComplemento().trim() : null;
			return data;
		}

		private static AddressData fromPatchDTO(UpdateAddressDTO dto, Address current) {
			AddressData data = new AddressData();
			data.street = resolveText(dto.getRua(), current.getStreet());
			data.number = resolveText(dto.getNumero(), current.getNumberReference());
			data.neighborhood = resolveText(dto.getBairro(), current.getNeighborhood());
			data.zipCode = resolveText(dto.getCep(), current.getZipCode());
			data.city = resolveText(dto.getCidade(), current.getCity());
			data.state = resolveText(dto.getEstado(), current.getState());
			data.country = resolveText(dto.getPais(), current.getCountry());
			data.complement = dto.getComplemento() != null ? dto.getComplemento().trim() : current.getComplement();
			return data;
		}

		private static String resolveText(String incoming, String current) {
			if (incoming == null || incoming.trim().isEmpty()) {
				return current;
			}
			return incoming.trim();
		}
	}
}
