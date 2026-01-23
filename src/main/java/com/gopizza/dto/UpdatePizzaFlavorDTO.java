package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Schema(description = "DTO para atualização parcial de sabor de pizza (todos os campos são opcionais)")
public class UpdatePizzaFlavorDTO {

	@Schema(description = "Nome do sabor de pizza", example = "Margherita")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	private String name;

	@Schema(description = "Descrição dos ingredientes", example = "Molho de tomate, mussarela e manjericão")
	@Size(min = 5, message = "Descrição deve ter no mínimo 5 caracteres")
	private String description;

	@Schema(description = "Opcionais disponíveis (ex: borda recheada)", example = "[\"Borda Recheada\", \"Bacon Extra\"]")
	private List<String> availableOptions;

	@Schema(description = "Tamanhos disponíveis e seus valores (P, M, G)", example = "{\"P\": 25.00, \"M\": 35.00, \"G\": 45.00}")
	private Map<@NotBlank String, @NotNull @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero") @Digits(integer = 10, fraction = 2, message = "Preço deve ter no máximo 2 casas decimais") BigDecimal> sizesAndPrices;

	@Schema(description = "URL ou caminho da imagem do sabor de pizza", example = "/api/images/550e8400-e29b-41d4-a716-446655440000.jpg")
	@Size(max = 500, message = "URL da imagem deve ter no máximo 500 caracteres")
	private String imageUrl;

	public UpdatePizzaFlavorDTO() {
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
