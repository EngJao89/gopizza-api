package com.gopizza.controller;

import com.gopizza.dto.CreateOrderDTO;
import com.gopizza.dto.OrderResponseDTO;
import com.gopizza.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "API para pedidos")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@Operation(summary = "Criar pedido")
	public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(dto));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar pedido por ID")
	public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID id) {
		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	@GetMapping
	@Operation(summary = "Listar pedidos")
	public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar pedido por ID")
	public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
		orderService.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}
}
