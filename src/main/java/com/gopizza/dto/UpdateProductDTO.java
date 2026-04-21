package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para atualização parcial de produto (campos opcionais)")
public class UpdateProductDTO {

	@Schema(description = "Marca", example = "GoPizza")
	@Size(max = 120, message = "Marca deve ter no máximo 120 caracteres")
	private String marca;

	@Schema(description = "Título do produto", example = "Forno a lenha portátil")
	@Size(max = 200, message = "Título deve ter no máximo 200 caracteres")
	private String titulo;

	@Schema(description = "Descrição resumida")
	@Size(min = 5, message = "Descrição deve ter no mínimo 5 caracteres")
	private String descricao;

	@Schema(description = "Conteúdo detalhado")
	@Size(min = 1, message = "Conteúdo não pode ser vazio")
	private String conteudo;

	@Schema(description = "URL da imagem", example = "/api/images/abc-uuid.jpg")
	@Size(max = 500, message = "URL da imagem deve ter no máximo 500 caracteres")
	private String imagemUrl;

	public UpdateProductDTO() {
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

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}
}
