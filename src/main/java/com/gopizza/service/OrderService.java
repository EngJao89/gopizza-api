package com.gopizza.service;

import com.gopizza.dto.CreateOrderDTO;
import com.gopizza.dto.CreateOrderItemDTO;
import com.gopizza.dto.OrderItemResponseDTO;
import com.gopizza.dto.OrderResponseDTO;
import com.gopizza.model.Order;
import com.gopizza.model.OrderItem;
import com.gopizza.model.OrderStatus;
import com.gopizza.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public OrderResponseDTO createOrder(CreateOrderDTO dto) {
		Order order = new Order();
		order.setCustomerName(dto.getCustomerName().trim());
		order.setCustomerPhone(dto.getCustomerPhone().trim());
		order.setDeliveryAddress(dto.getDeliveryAddress().trim());
		order.setNotes(dto.getNotes() != null ? dto.getNotes().trim() : null);
		order.setStatus(OrderStatus.PENDING);

		List<OrderItem> items = dto.getItems().stream()
				.map(this::toOrderItem)
				.toList();
		order.setItems(items);
		order.setTotalAmount(calculateTotal(items));

		Order savedOrder = orderRepository.save(order);
		return toResponse(savedOrder);
	}

	@Transactional(readOnly = true)
	public OrderResponseDTO getOrderById(UUID id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
		return toResponse(order);
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDTO> getAllOrders() {
		return orderRepository.findAll().stream()
				.map(this::toResponse)
				.toList();
	}

	private OrderItem toOrderItem(CreateOrderItemDTO dto) {
		OrderItem item = new OrderItem();
		item.setProductId(dto.getProductId());
		item.setProductName(dto.getProductName().trim());
		item.setQuantity(dto.getQuantity());
		item.setUnitPrice(dto.getUnitPrice().setScale(2, RoundingMode.HALF_UP));
		item.setLineTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
				.setScale(2, RoundingMode.HALF_UP));
		return item;
	}

	private BigDecimal calculateTotal(List<OrderItem> items) {
		return items.stream()
				.map(OrderItem::getLineTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(2, RoundingMode.HALF_UP);
	}

	private OrderResponseDTO toResponse(Order order) {
		List<OrderItemResponseDTO> items = order.getItems().stream()
				.map(item -> new OrderItemResponseDTO(
						item.getProductId(),
						item.getProductName(),
						item.getQuantity(),
						item.getUnitPrice(),
						item.getLineTotal()
				))
				.toList();

		return new OrderResponseDTO(
				order.getId(),
				order.getCustomerName(),
				order.getCustomerPhone(),
				order.getDeliveryAddress(),
				order.getNotes(),
				order.getStatus().name(),
				order.getTotalAmount(),
				items,
				order.getCreatedAt(),
				order.getUpdatedAt()
		);
	}
}
