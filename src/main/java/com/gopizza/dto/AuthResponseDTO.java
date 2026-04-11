package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de autenticação")
public class AuthResponseDTO {

	@Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String token;

	@Schema(description = "Se o usuário autenticado é administrador (espelha o perfil no banco)", example = "false")
	private boolean admin;

	public AuthResponseDTO() {
	}

	public AuthResponseDTO(String token, boolean admin) {
		this.token = token;
		this.admin = admin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
