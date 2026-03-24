package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de autenticação")
public class AuthResponseDTO {

	@Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String token;

	public AuthResponseDTO() {
	}

	public AuthResponseDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
