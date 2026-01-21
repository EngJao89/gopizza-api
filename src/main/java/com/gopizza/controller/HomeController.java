package com.gopizza.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

	@GetMapping("/api")
	public ResponseEntity<Map<String, Object>> home() {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Bem-vindo à API GoPizza!");
		response.put("version", "1.0.0");
		response.put("status", "online");
		
		Map<String, String> endpoints = new HashMap<>();
		endpoints.put("Swagger UI", "/swagger-ui.html");
		endpoints.put("API Docs", "/api-docs");
		endpoints.put("Listar usuários", "GET /api/users");
		endpoints.put("Buscar usuário por ID", "GET /api/users/{id}");
		endpoints.put("Buscar usuário por email", "GET /api/users/email/{email}");
		endpoints.put("Criar usuário", "POST /api/users");
		endpoints.put("Atualizar usuário", "PUT /api/users/{id}");
		endpoints.put("Deletar usuário", "DELETE /api/users/{id}");
		
		response.put("endpoints", endpoints);
		
		return ResponseEntity.ok(response);
	}
}
