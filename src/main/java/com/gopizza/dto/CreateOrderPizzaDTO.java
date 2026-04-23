package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Pizza para criação de pedido")
public class CreateOrderPizzaDTO {

	@Schema(description = "ID da pizza", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID id;

	@Schema(description = "Nome da pizza", example = "Margherita")
	@NotBlank(message = "Nome da pizza é obrigatório")
	@Size(max = 200, message = "Nome da pizza deve ter no máximo 200 caracteres")
	private String name;

	@Schema(description = "Descrição da pizza")
	private String description;

	@Schema(description = "Opcionais da pizza")
	private List<String> availableOptions;

	@Schema(description = "Tamanho selecionado", example = "M")
	private String size;

	@Schema(description = "Preço unitário final da pizza", example = "39.90")
	@NotNull(message = "Preço unitário da pizza é obrigatório")
	@DecimalMin(value = "0.01", message = "Preço unitário da pizza deve ser maior que zero")
	@Digits(integer = 10, fraction = 2, message = "Preço unitário da pizza deve ter no máximo 2 casas decimais")
	private BigDecimal unitPrice;

	@Schema(description = "URL da imagem da pizza")
	private String imageUrl;

	@Schema(description = "Quantidade", example = "2")
	@NotNull(message = "Quantidade da pizza é obrigatória")
	@Positive(message = "Quantidade da pizza deve ser maior que zero")
	private Integer quantity;

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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
