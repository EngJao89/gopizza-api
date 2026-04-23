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
import java.util.ArrayList;
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
		order.setDeliveryNumber(dto.getDeliveryNumber().trim());
		order.setDeliveryNeighborhood(dto.getDeliveryNeighborhood().trim());
		order.setStatus(OrderStatus.PENDING);

		List<OrderItem> items = getIncomingItems(dto).stream()
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

	private List<CreateOrderItemDTO> getIncomingItems(CreateOrderDTO dto) {
		if (dto.getItems() != null && !dto.getItems().isEmpty()) {
			return dto.getItems();
		}

		List<CreateOrderItemDTO> mergedItems = new ArrayList<>();
		if (dto.getPizzas() != null) {
			mergedItems.addAll(dto.getPizzas());
		}
		if (dto.getProducts() != null) {
			mergedItems.addAll(dto.getProducts());
		}
		return mergedItems;
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

		OrderResponseDTO response = new OrderResponseDTO();
		response.setId(order.getId());
		response.setCustomerName(order.getCustomerName());
		response.setCustomerPhone(order.getCustomerPhone());
		response.setDeliveryAddress(order.getDeliveryAddress());
		response.setDeliveryNumber(order.getDeliveryNumber());
		response.setDeliveryNeighborhood(order.getDeliveryNeighborhood());
		response.setStatus(order.getStatus().name());
		response.setTotalAmount(order.getTotalAmount());
		response.setItems(items);
		response.setCreatedAt(order.getCreatedAt());
		response.setUpdatedAt(order.getUpdatedAt());
		return response;
	}
}
