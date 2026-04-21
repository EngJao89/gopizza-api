package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta com dados do produto")
public class ProductResponseDTO {

	private UUID id;
	private String marca;
	private String titulo;
	private String descricao;
	private String conteudo;
	private String imagemUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public ProductResponseDTO() {
	}

	public ProductResponseDTO(UUID id, String marca, String titulo, String descricao, String conteudo, String imagemUrl,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.marca = marca;
		this.titulo = titulo;
		this.descricao = descricao;
		this.conteudo = conteudo;
		this.imagemUrl = imagemUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

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

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
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
