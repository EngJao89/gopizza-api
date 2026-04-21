package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criação de endereço de entrega")
public class CreateAddressDTO {

	@Schema(description = "Rua", example = "Rua das Flores, 123")
	@NotBlank(message = "Rua é obrigatória")
	@Size(max = 180, message = "Rua deve ter no máximo 180 caracteres")
	private String rua;

	@Schema(description = "Bairro", example = "Centro")
	@NotBlank(message = "Bairro é obrigatório")
	@Size(max = 120, message = "Bairro deve ter no máximo 120 caracteres")
	private String bairro;

	@Schema(description = "CEP", example = "12345-678")
	@NotBlank(message = "CEP é obrigatório")
	@Size(max = 20, message = "CEP deve ter no máximo 20 caracteres")
	private String cep;

	@Schema(description = "Cidade", example = "Sao Paulo")
	@NotBlank(message = "Cidade é obrigatória")
	@Size(max = 120, message = "Cidade deve ter no máximo 120 caracteres")
	private String cidade;

	@Schema(description = "Estado", example = "SP")
	@NotBlank(message = "Estado é obrigatório")
	@Size(max = 120, message = "Estado deve ter no máximo 120 caracteres")
	private String estado;

	@Schema(description = "Pais", example = "Brasil")
	@NotBlank(message = "Pais é obrigatório")
	@Size(max = 120, message = "Pais deve ter no máximo 120 caracteres")
	private String pais;

	@Schema(description = "Complemento", example = "Apto 203")
	@Size(max = 200, message = "Complemento deve ter no máximo 200 caracteres")
	private String complemento;

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
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
}
