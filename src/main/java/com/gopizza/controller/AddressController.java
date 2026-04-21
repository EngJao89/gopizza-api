package com.gopizza.controller;

import com.gopizza.dto.AddressResponseDTO;
import com.gopizza.dto.CreateAddressDTO;
import com.gopizza.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
@Tag(name = "Addresses", description = "API para enderecos de entrega vinculados a usuarios")
public class AddressController {

	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@PostMapping
	@Operation(summary = "Criar endereco para usuario")
	public ResponseEntity<AddressResponseDTO> createAddress(
			@PathVariable UUID userId,
			@Valid @RequestBody CreateAddressDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(userId, dto));
	}

	@GetMapping
	@Operation(summary = "Listar enderecos de um usuario")
	public ResponseEntity<List<AddressResponseDTO>> getAddressesByUserId(@PathVariable UUID userId) {
		return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
	}
}
