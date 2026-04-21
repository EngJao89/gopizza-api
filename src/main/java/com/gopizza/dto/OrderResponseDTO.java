package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Resposta com dados do pedido")
public class OrderResponseDTO {

	private UUID id;
	private String customerName;
	private String customerPhone;
	private String deliveryAddress;
	private String notes;
	private String status;
	private BigDecimal totalAmount;
	private List<OrderItemResponseDTO> items;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public OrderResponseDTO() {
	}

	public OrderResponseDTO(UUID id, String customerName, String customerPhone, String deliveryAddress, String notes,
			String status, BigDecimal totalAmount, List<OrderItemResponseDTO> items, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.deliveryAddress = deliveryAddress;
		this.notes = notes;
		this.status = status;
		this.totalAmount = totalAmount;
		this.items = items;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderItemResponseDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemResponseDTO> items) {
		this.items = items;
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
}
