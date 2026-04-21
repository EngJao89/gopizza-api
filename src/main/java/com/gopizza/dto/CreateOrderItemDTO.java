package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Item para criação de pedido")
public class CreateOrderItemDTO {

	@Schema(description = "ID do produto (opcional)", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID productId;

	@Schema(description = "Nome do produto no momento da compra", example = "Pizza Margherita")
	@NotBlank(message = "Nome do produto é obrigatório")
	@Size(max = 200, message = "Nome do produto deve ter no máximo 200 caracteres")
	private String productName;

	@Schema(description = "Quantidade", example = "2")
	@NotNull(message = "Quantidade é obrigatória")
	@Positive(message = "Quantidade deve ser maior que zero")
	private Integer quantity;

	@Schema(description = "Preço unitário", example = "39.90")
	@NotNull(message = "Preço unitário é obrigatório")
	@DecimalMin(value = "0.01", message = "Preço unitário deve ser maior que zero")
	@Digits(integer = 10, fraction = 2, message = "Preço unitário deve ter no máximo 2 casas decimais")
	private BigDecimal unitPrice;

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
}
