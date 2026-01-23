package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Schema(description = "DTO de resposta com dados do sabor de pizza")
public class PizzaFlavorResponseDTO {

	@Schema(description = "ID único do sabor de pizza (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID id;

	@Schema(description = "Nome do sabor de pizza", example = "Margherita")
	private String name;

	@Schema(description = "Descrição dos ingredientes", example = "Molho de tomate, mussarela e manjericão")
	private String description;

	@Schema(description = "Opcionais disponíveis", example = "[\"Borda Recheada\", \"Bacon Extra\"]")
	private List<String> availableOptions;

	@Schema(description = "Tamanhos disponíveis e seus valores", example = "{\"P\": 25.00, \"M\": 35.00, \"G\": 45.00}")
	private Map<String, BigDecimal> sizesAndPrices;

	@Schema(description = "Data de criação do registro")
	private LocalDateTime createdAt;

	@Schema(description = "Data da última atualização do registro")
	private LocalDateTime updatedAt;

	public PizzaFlavorResponseDTO() {
	}

	public PizzaFlavorResponseDTO(UUID id, String name, String description, List<String> availableOptions,
			Map<String, BigDecimal> sizesAndPrices, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.availableOptions = availableOptions;
		this.sizesAndPrices = sizesAndPrices;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAvailableOptions() {
		return availableOptions;
	}

	public void setAvailableOptions(List<String> availableOptions) {
		this.availableOptions = availableOptions;
	}

	public Map<String, BigDecimal> getSizesAndPrices() {
		return sizesAndPrices;
	}

	public void setSizesAndPrices(Map<String, BigDecimal> sizesAndPrices) {
		this.sizesAndPrices = sizesAndPrices;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
