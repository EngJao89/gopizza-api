package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de autenticação")
public class AuthResponseDTO {

	@Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String token;

	@Schema(description = "Tipo do token", example = "Bearer")
	private String type = "Bearer";

	@Schema(description = "Dados do usuário autenticado")
	private UserResponseDTO user;

	public AuthResponseDTO() {
	}

	public AuthResponseDTO(String token, UserResponseDTO user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserResponseDTO getUser() {
		return user;
	}

	public void setUser(UserResponseDTO user) {
		this.user = user;
	}
}
