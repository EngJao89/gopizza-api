package com.gopizza.controller;

import com.gopizza.dto.AuthResponseDTO;
import com.gopizza.dto.LoginRequestDTO;
import com.gopizza.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		description = "Autentica um usuário e retorna um token JWT"
	)
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
		AuthResponseDTO authResponse = authService.login(loginRequest);
		return ResponseEntity.ok(authResponse);
	}
}
