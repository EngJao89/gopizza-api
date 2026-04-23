package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Schema(description = "Item para criação de pedido")
public class CreateOrderItemDTO {

	@Schema(description = "ID do produto (opcional)", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID productId;

	@Schema(description = "ID no formato completo retornado pelo catálogo", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID id;

	@Schema(description = "Nome do produto no momento da compra", example = "Pizza Margherita")
	private String productName;

	@Schema(description = "Nome de pizza (payload completo)", example = "Margherita")
	private String name;

	@Schema(description = "Título de produto (payload completo)", example = "Coca-Cola 2L")
	private String titulo;

	@Schema(description = "Quantidade", example = "2")
	@NotNull(message = "Quantidade é obrigatória")
	@Positive(message = "Quantidade deve ser maior que zero")
	private Integer quantity;

	@Schema(description = "Preço unitário", example = "39.90")
	@DecimalMin(value = "0.01", message = "Preço unitário deve ser maior que zero")
	@Digits(integer = 10, fraction = 2, message = "Preço unitário deve ter no máximo 2 casas decimais")
	private BigDecimal unitPrice;

	@Schema(description = "Valor de produto (payload completo)", example = "12.50")
	@DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
	@Digits(integer = 10, fraction = 2, message = "Valor deve ter no máximo 2 casas decimais")
	private BigDecimal valor;

	@Schema(description = "Preço genérico (payload completo)", example = "39.90")
	@DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
	@Digits(integer = 10, fraction = 2, message = "Preço deve ter no máximo 2 casas decimais")
	private BigDecimal price;

	@Schema(description = "Tamanho selecionado da pizza", example = "M")
	private String size;

	@Schema(description = "Tabela de tamanhos e preços da pizza", example = "{\"P\":25.00,\"M\":35.00,\"G\":45.00}")
	private Map<String, BigDecimal> sizesAndPrices;

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Map<String, BigDecimal> getSizesAndPrices() {
		return sizesAndPrices;
	}

	public void setSizesAndPrices(Map<String, BigDecimal> sizesAndPrices) {
		this.sizesAndPrices = sizesAndPrices;
	}
}
