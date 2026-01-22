package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "DTO para criação de usuário")
public class CreateUserDTO {

	@Schema(description = "Email do usuário", example = "joao.silva@example.com")
	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Email deve ter um formato válido")
	@Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
	private String email;

	@Schema(description = "Nome completo do usuário", example = "João Silva")
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	private String name;

	@Schema(description = "Telefone do usuário", example = "11999999999")
	@NotBlank(message = "Telefone é obrigatório")
	@Size(min = 10, max = 20, message = "Telefone deve ter entre 10 e 20 caracteres")
	private String phone;

	@Schema(description = "Senha do usuário (mínimo 6 caracteres)", example = "senha123")
	@NotBlank(message = "Senha é obrigatória")
	@Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
	private String password;

	@Schema(description = "Data de nascimento do usuário (formato: YYYY-MM-DD)", example = "1990-05-15")
	@Past(message = "Data de nascimento deve ser uma data no passado")
	private LocalDate birthday;

	@Schema(description = "CPF do usuário (11 dígitos numéricos, sem formatação)", example = "12345678901")
	@Pattern(regexp = "^\\d{11}$", message = "CPF deve conter exatamente 11 dígitos numéricos")
	private String cpf;

	public CreateUserDTO() {
	}

	public CreateUserDTO(String email, String name, String phone, String password) {
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
	}

	public CreateUserDTO(String email, String name, String phone, String password, LocalDate birthday, String cpf) {
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.birthday = birthday;
		this.cpf = cpf;
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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
