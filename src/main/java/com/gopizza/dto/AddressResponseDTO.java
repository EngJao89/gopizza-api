package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta com dados de endereco de entrega")
public class AddressResponseDTO {

	@Schema(description = "ID do endereco", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID id;
	@Schema(description = "ID do usuario dono do endereco", example = "550e8400-e29b-41d4-a716-446655440001")
	private UUID userId;
	@Schema(
			description = "Nome da rua",
			example = "Rua das Flores"
	)
	private String rua;
	@Schema(
			description = "Numero/identificador do endereco",
			example = "QD.10 LT.30"
	)
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
	private String estado;
	private String pais;
	private String complemento;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public AddressResponseDTO() {
		// Default constructor for JSON serialization/deserialization.
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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
