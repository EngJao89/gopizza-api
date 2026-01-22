package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "DTO de resposta com dados do usuário")
public class UserResponseDTO {

	@Schema(description = "ID único do usuário", example = "1")
	private Long id;

	@Schema(description = "Email do usuário", example = "joao.silva@example.com")
	private String email;

	@Schema(description = "Nome completo do usuário", example = "João Silva")
	private String name;

	@Schema(description = "Telefone do usuário", example = "11999999999")
	private String phone;

	@Schema(description = "Data de nascimento do usuário", example = "1990-05-15")
	private LocalDate birthday;

	@Schema(description = "CPF do usuário (11 dígitos)", example = "12345678901")
	private String cpf;

	@Schema(description = "Data e hora de criação do registro", example = "2024-01-15T10:30:00")
	private LocalDateTime createdAt;

	@Schema(description = "Data e hora da última atualização do registro", example = "2024-01-15T10:30:00")
	private LocalDateTime updatedAt;

	// Constructors
	public UserResponseDTO() {
	}

	public UserResponseDTO(Long id, String email, String name, String phone, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UserResponseDTO(Long id, String email, String name, String phone, LocalDate birthday, String cpf, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.birthday = birthday;
		this.cpf = cpf;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
