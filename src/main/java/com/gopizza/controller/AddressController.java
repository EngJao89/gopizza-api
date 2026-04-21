package com.gopizza.controller;

import com.gopizza.dto.AddressResponseDTO;
import com.gopizza.dto.CreateAddressDTO;
import com.gopizza.dto.UpdateAddressDTO;
import com.gopizza.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@Operation(
			summary = "Criar endereco para usuario",
			description = "Informe rua e numero em campos separados. O campo numero aceita formatos como QD.10 LT.30."
	)
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

	@PutMapping("/{addressId}")
	@Operation(summary = "Atualizar endereco completo de um usuario")
	public ResponseEntity<AddressResponseDTO> updateAddress(
			@PathVariable UUID userId,
			@PathVariable UUID addressId,
			@Valid @RequestBody CreateAddressDTO dto) {
		return ResponseEntity.ok(addressService.updateAddress(userId, addressId, dto));
	}

	@PatchMapping("/{addressId}")
	@Operation(summary = "Atualizar endereco parcialmente de um usuario")
	public ResponseEntity<AddressResponseDTO> updateAddressPartial(
			@PathVariable UUID userId,
			@PathVariable UUID addressId,
			@Valid @RequestBody UpdateAddressDTO dto) {
		return ResponseEntity.ok(addressService.updateAddressPartial(userId, addressId, dto));
	}

	@DeleteMapping("/{addressId}")
	@Operation(summary = "Deletar endereco de um usuario")
	public ResponseEntity<Void> deleteAddress(
			@PathVariable UUID userId,
			@PathVariable UUID addressId) {
		addressService.deleteAddress(userId, addressId);
		return ResponseEntity.noContent().build();
	}
}
