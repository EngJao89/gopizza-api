package com.gopizza.controller;

import com.gopizza.dto.CreateOrderDTO;
import com.gopizza.dto.OrderResponseDTO;
import com.gopizza.dto.UpdateOrderDTO;
import com.gopizza.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping("/{id}")
	@Operation(
			summary = "Atualizar pedido",
			description = "Substitui dados do pedido e itens (mesmo contrato da criação). O status do pedido é mantido."
	)
	public ResponseEntity<OrderResponseDTO> updateOrder(
			@PathVariable UUID id,
			@Valid @RequestBody CreateOrderDTO dto) {
		return ResponseEntity.ok(orderService.updateOrder(id, dto));
	}

	@PatchMapping("/{id}")
	@Operation(
			summary = "Atualizar pedido parcialmente",
			description = "Atualiza apenas os campos enviados. Para alterar itens, envie pizzas e/ou produtos; a lista resultante deve ter pelo menos um item."
	)
	public ResponseEntity<OrderResponseDTO> updateOrderPartial(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateOrderDTO dto) {
		return ResponseEntity.ok(orderService.updateOrderPartial(id, dto));
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
