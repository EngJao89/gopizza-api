package com.gopizza.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserDTO {

	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Email deve ter um formato válido")
	@Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
	private String email;

	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	private String name;

	@NotBlank(message = "Telefone é obrigatório")
	@Size(min = 10, max = 20, message = "Telefone deve ter entre 10 e 20 caracteres")
	private String phone;

	@NotBlank(message = "Senha é obrigatória")
	@Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
	private String password;

	public CreateUserDTO() {
	}

	public CreateUserDTO(String email, String name, String phone, String password) {
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
	}

	// Getters and Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
