package com.gopizza.controller;

import com.gopizza.dto.CreateUserDTO;
import com.gopizza.dto.UserResponseDTO;
import com.gopizza.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API para gerenciamento de usuários")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema")
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
		UserResponseDTO userResponse = userService.createUser(createUserDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		UserResponseDTO userResponse = userService.getUserById(id);
		return ResponseEntity.ok(userResponse);
	}

	@GetMapping("/email/{email}")
	@Operation(summary = "Buscar usuário por email", description = "Retorna os dados de um usuário pelo email")
	public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
		UserResponseDTO userResponse = userService.getUserByEmail(email);
		return ResponseEntity.ok(userResponse);
	}

	@GetMapping
	@Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		List<UserResponseDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
	public ResponseEntity<UserResponseDTO> updateUser(
			@PathVariable Long id,
			@Valid @RequestBody CreateUserDTO updateUserDTO) {
		UserResponseDTO userResponse = userService.updateUser(id, updateUserDTO);
		return ResponseEntity.ok(userResponse);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
