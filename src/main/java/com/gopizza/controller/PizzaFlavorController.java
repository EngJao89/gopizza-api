package com.gopizza.controller;

import com.gopizza.dto.CreatePizzaFlavorDTO;
import com.gopizza.dto.PizzaFlavorResponseDTO;
import com.gopizza.dto.UpdatePizzaFlavorDTO;
import com.gopizza.service.PizzaFlavorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pizza-flavors")
@Tag(name = "Pizza Flavors", description = "API para gerenciamento de sabores de pizza")
public class PizzaFlavorController {

	private final PizzaFlavorService pizzaFlavorService;

	@Autowired
	public PizzaFlavorController(PizzaFlavorService pizzaFlavorService) {
		this.pizzaFlavorService = pizzaFlavorService;
	}

	@PostMapping
	@Operation(
		summary = "Criar novo sabor de pizza",
		description = "Cria um novo sabor de pizza no sistema. Campos obrigatórios: name, description e sizesAndPrices. availableOptions é opcional."
	)
	public ResponseEntity<PizzaFlavorResponseDTO> createPizzaFlavor(@Valid @RequestBody CreatePizzaFlavorDTO createPizzaFlavorDTO) {
		PizzaFlavorResponseDTO pizzaFlavorResponse = pizzaFlavorService.createPizzaFlavor(createPizzaFlavorDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pizzaFlavorResponse);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar sabor de pizza por ID", description = "Retorna os dados de um sabor de pizza específico usando UUID")
	public ResponseEntity<PizzaFlavorResponseDTO> getPizzaFlavorById(@PathVariable UUID id) {
		PizzaFlavorResponseDTO pizzaFlavorResponse = pizzaFlavorService.getPizzaFlavorById(id);
		return ResponseEntity.ok(pizzaFlavorResponse);
	}

	@GetMapping("/name/{name}")
	@Operation(summary = "Buscar sabor de pizza por nome", description = "Retorna os dados de um sabor de pizza pelo nome")
	public ResponseEntity<PizzaFlavorResponseDTO> getPizzaFlavorByName(@PathVariable String name) {
		PizzaFlavorResponseDTO pizzaFlavorResponse = pizzaFlavorService.getPizzaFlavorByName(name);
		return ResponseEntity.ok(pizzaFlavorResponse);
	}

	@GetMapping
	@Operation(summary = "Listar todos os sabores de pizza", description = "Retorna uma lista com todos os sabores de pizza cadastrados")
	public ResponseEntity<List<PizzaFlavorResponseDTO>> getAllPizzaFlavors() {
		List<PizzaFlavorResponseDTO> pizzaFlavors = pizzaFlavorService.getAllPizzaFlavors();
		return ResponseEntity.ok(pizzaFlavors);
	}

	@PutMapping("/{id}")
	@Operation(
		summary = "Atualizar sabor de pizza",
		description = "Atualiza os dados de um sabor de pizza existente usando UUID. Todos os campos podem ser atualizados."
	)
	public ResponseEntity<PizzaFlavorResponseDTO> updatePizzaFlavor(
			@PathVariable UUID id,
			@Valid @RequestBody CreatePizzaFlavorDTO updatePizzaFlavorDTO) {
		PizzaFlavorResponseDTO pizzaFlavorResponse = pizzaFlavorService.updatePizzaFlavor(id, updatePizzaFlavorDTO);
		return ResponseEntity.ok(pizzaFlavorResponse);
	}

	@PatchMapping("/{id}")
	@Operation(
		summary = "Atualizar sabor de pizza parcialmente",
		description = "Atualiza parcialmente os dados de um sabor de pizza existente usando UUID. Apenas os campos enviados serão atualizados, os demais permanecerão inalterados."
	)
	public ResponseEntity<PizzaFlavorResponseDTO> updatePizzaFlavorPartial(
			@PathVariable UUID id,
			@Valid @RequestBody UpdatePizzaFlavorDTO updatePizzaFlavorDTO) {
		PizzaFlavorResponseDTO pizzaFlavorResponse = pizzaFlavorService.updatePizzaFlavorPartial(id, updatePizzaFlavorDTO);
		return ResponseEntity.ok(pizzaFlavorResponse);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar sabor de pizza", description = "Remove um sabor de pizza do sistema usando UUID")
	public ResponseEntity<Void> deletePizzaFlavor(@PathVariable UUID id) {
		pizzaFlavorService.deletePizzaFlavor(id);
		return ResponseEntity.noContent().build();
	}
}
