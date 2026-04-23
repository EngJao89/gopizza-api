package com.gopizza.service;

import com.gopizza.dto.CreateOrderDTO;
import com.gopizza.dto.CreateOrderPizzaDTO;
import com.gopizza.dto.CreateOrderProductDTO;
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

		List<OrderItem> items = getIncomingItems(dto);
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

	@Transactional
	public void deleteOrder(UUID id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
		orderRepository.delete(order);
	}

	private OrderItem toOrderItemFromPizza(CreateOrderPizzaDTO dto) {
		OrderItem item = new OrderItem();
		item.setProductId(dto.getId());
		item.setProductName(dto.getName().trim());
		item.setQuantity(dto.getQuantity());
		item.setUnitPrice(resolvePizzaUnitPrice(dto));
		item.setLineTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
				.setScale(2, RoundingMode.HALF_UP));
		return item;
	}

	private OrderItem toOrderItemFromProduct(CreateOrderProductDTO dto) {
		OrderItem item = new OrderItem();
		item.setProductId(dto.getId());
		item.setProductName(dto.getTitulo().trim());
		item.setQuantity(dto.getQuantity());
		item.setUnitPrice(dto.getValor().setScale(2, RoundingMode.HALF_UP));
		item.setLineTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
				.setScale(2, RoundingMode.HALF_UP));
		return item;
	}

	private BigDecimal resolvePizzaUnitPrice(CreateOrderPizzaDTO dto) {
		BigDecimal resolved = dto.getUnitPrice();
		if (resolved == null) {
			throw new IllegalArgumentException("Preço da pizza é obrigatório");
		}
		if (resolved.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Preço da pizza deve ser maior que zero");
		}
		return resolved.setScale(2, RoundingMode.HALF_UP);
	}

	private List<OrderItem> getIncomingItems(CreateOrderDTO dto) {
		List<OrderItem> mergedItems = new ArrayList<>();
		if (dto.getPizzas() != null) {
			mergedItems.addAll(dto.getPizzas().stream()
					.map(this::toOrderItemFromPizza)
					.toList());
		}
		if (dto.getProducts() != null) {
			mergedItems.addAll(dto.getProducts().stream()
					.map(this::toOrderItemFromProduct)
					.toList());
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
