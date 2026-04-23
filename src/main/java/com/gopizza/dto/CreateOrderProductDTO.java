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

@Schema(description = "Produto para criação de pedido")
public class CreateOrderProductDTO {

	@Schema(description = "ID do produto", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID id;

	@Schema(description = "Marca do produto", example = "Coca-Cola")
	private String marca;

	@Schema(description = "Título do produto", example = "Coca-Cola 2L")
	@NotBlank(message = "Título do produto é obrigatório")
	@Size(max = 200, message = "Título do produto deve ter no máximo 200 caracteres")
	private String titulo;

	@Schema(description = "Descrição do produto")
	private String descricao;

	@Schema(description = "Conteúdo do produto")
	private String conteudo;

	@Schema(description = "Valor unitário do produto", example = "12.50")
	@NotNull(message = "Valor do produto é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor do produto deve ser maior que zero")
	@Digits(integer = 10, fraction = 2, message = "Valor do produto deve ter no máximo 2 casas decimais")
	private BigDecimal valor;

	@Schema(description = "URL da imagem do produto")
	private String imagemUrl;

	@Schema(description = "Quantidade", example = "2")
	@NotNull(message = "Quantidade do produto é obrigatória")
	@Positive(message = "Quantidade do produto deve ser maior que zero")
	private Integer quantity;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
