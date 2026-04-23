package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "DTO para criação de produto")
public class CreateProductDTO {

	@Schema(description = "Marca", example = "GoPizza")
	@NotBlank(message = "Marca é obrigatória")
	@Size(max = 120, message = "Marca deve ter no máximo 120 caracteres")
	private String marca;

	@Schema(description = "Título do produto", example = "Forno a lenha portátil")
	@NotBlank(message = "Título é obrigatório")
	@Size(max = 200, message = "Título deve ter no máximo 200 caracteres")
	private String titulo;

	@Schema(description = "Descrição resumida", example = "Forno compacto para pizzas artesanais.")
	@NotBlank(message = "Descrição é obrigatória")
	@Size(min = 5, message = "Descrição deve ter no mínimo 5 caracteres")
	private String descricao;

	@Schema(description = "Conteúdo detalhado", example = "Especificações técnicas, modo de uso e garantia.")
	@NotBlank(message = "Conteúdo é obrigatório")
	@Size(min = 1, message = "Conteúdo não pode ser vazio")
	private String conteudo;

	@Schema(description = "Valor do produto", example = "129.90")
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
	@Digits(integer = 10, fraction = 2, message = "Valor deve ter no máximo 2 casas decimais")
	private BigDecimal valor;

	@Schema(description = "URL da imagem após upload em /api/images/upload", example = "/api/images/abc-uuid.jpg")
	@NotBlank(message = "URL da imagem é obrigatória")
	@Size(max = 500, message = "URL da imagem deve ter no máximo 500 caracteres")
	private String imagemUrl;

	public CreateProductDTO() {
		// Default constructor for JSON serialization/deserialization.
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
}
