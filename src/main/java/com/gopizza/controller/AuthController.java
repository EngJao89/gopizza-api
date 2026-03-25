package com.gopizza.controller;

import com.gopizza.dto.AuthResponseDTO;
import com.gopizza.dto.LoginRequestDTO;
import com.gopizza.dto.UserResponseDTO;
import com.gopizza.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API para autenticação de usuários")
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	@Operation(
		summary = "Login",
		description = "Autentica um usuário e retorna apenas o token JWT"
	)
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
		AuthResponseDTO authResponse = authService.login(loginRequest);
		return ResponseEntity.ok(authResponse);
	}

	@GetMapping("/me")
	@Operation(
		summary = "Usuário autenticado",
		description = "Retorna os dados do usuário autenticado com base no token JWT enviado no header Authorization"
	)
	public ResponseEntity<UserResponseDTO> me(Authentication authentication) {
		UUID userId = resolveUserId(authentication);
		if (userId == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token JWT necessário ou inválido");
		}
		UserResponseDTO userResponse = authService.getLoggedUser(userId);
		return ResponseEntity.ok(userResponse);
	}

	private static UUID resolveUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof UUID uuid) {
			return uuid;
		}
		if (principal instanceof String s) {
			try {
				return UUID.fromString(s);
			} catch (IllegalArgumentException ignored) {
				return null;
			}
		}
		return null;
	}
}
