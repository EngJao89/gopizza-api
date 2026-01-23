package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Schema(description = "DTO para criação de sabor de pizza")
public class CreatePizzaFlavorDTO {

	@Schema(description = "Nome do sabor de pizza", example = "Margherita")
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	private String name;

	@Schema(description = "Descrição dos ingredientes", example = "Molho de tomate, mussarela e manjericão")
	@NotBlank(message = "Descrição é obrigatória")
	@Size(min = 5, message = "Descrição deve ter no mínimo 5 caracteres")
	private String description;

	@Schema(description = "Opcionais disponíveis (ex: borda recheada)", example = "[\"Borda Recheada\", \"Bacon Extra\"]")
	private List<String> availableOptions;

	@Schema(description = "Tamanhos disponíveis e seus valores (P, M, G)", example = "{\"P\": 25.00, \"M\": 35.00, \"G\": 45.00}")
	@NotNull(message = "Tamanhos e preços são obrigatórios")
	@NotEmpty(message = "Deve haver pelo menos um tamanho disponível")
	private Map<@NotBlank String, @NotNull @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero") @Digits(integer = 10, fraction = 2, message = "Preço deve ter no máximo 2 casas decimais") BigDecimal> sizesAndPrices;

	public CreatePizzaFlavorDTO() {
	}

	public CreatePizzaFlavorDTO(String name, String description, List<String> availableOptions, Map<String, BigDecimal> sizesAndPrices) {
		this.name = name;
		this.description = description;
		this.availableOptions = availableOptions;
		this.sizesAndPrices = sizesAndPrices;
	}

	// Getters and Setters
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
}
