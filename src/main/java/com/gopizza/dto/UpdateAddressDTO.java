package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para atualizacao parcial de endereco de entrega")
public class UpdateAddressDTO {

	@Schema(description = "Nome da rua", example = "Rua das Flores")
	@Size(max = 180, message = "Rua deve ter no máximo 180 caracteres")
	@Pattern(
			regexp = "^[\\p{L}0-9 .,-]+$",
			message = "Rua deve conter apenas letras, números, espaços, ponto, vírgula e hífen"
	)
	private String rua;

	@Schema(
			description = "Numero/identificador do endereco",
			example = "QD.10 LT.30"
	)
	@Size(max = 40, message = "Numero deve ter no máximo 40 caracteres")
	@Pattern(
			regexp = "^[A-Za-z0-9 ./-]+$",
			message = "Numero deve conter apenas letras, números, espaços, ponto, barra e hífen"
	)
	private String numero;

	@Schema(description = "Bairro", example = "Centro")
	@Size(max = 120, message = "Bairro deve ter no máximo 120 caracteres")
	private String bairro;

	@Schema(description = "CEP", example = "12345-678")
	@Size(max = 20, message = "CEP deve ter no máximo 20 caracteres")
	private String cep;

	@Schema(description = "Cidade", example = "Sao Paulo")
	@Size(max = 120, message = "Cidade deve ter no máximo 120 caracteres")
	private String cidade;

	@Schema(description = "Estado", example = "SP")
	@Size(max = 120, message = "Estado deve ter no máximo 120 caracteres")
	private String estado;

	@Schema(description = "Pais", example = "Brasil")
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
}
