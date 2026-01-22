package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para requisição de login")
public class LoginRequestDTO {

	@Schema(description = "Email do usuário", example = "joao.silva@example.com")
	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Email deve ter um formato válido")
	private String email;

	@Schema(description = "Senha do usuário", example = "senha123")
	@NotBlank(message = "Senha é obrigatória")
	private String password;

	public LoginRequestDTO() {
	}

	public LoginRequestDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
