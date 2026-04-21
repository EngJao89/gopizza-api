package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "DTO para criação de pedido")
public class CreateOrderDTO {

	@Schema(description = "Nome do cliente", example = "João Silva")
	@NotBlank(message = "Nome do cliente é obrigatório")
	@Size(max = 120, message = "Nome do cliente deve ter no máximo 120 caracteres")
	private String customerName;

	@Schema(description = "Telefone do cliente", example = "(11) 99999-9999")
	@NotBlank(message = "Telefone do cliente é obrigatório")
	@Size(max = 30, message = "Telefone do cliente deve ter no máximo 30 caracteres")
	private String customerPhone;

	@Schema(description = "Endereço de entrega", example = "Rua das Flores, 123 - Centro")
	@NotBlank(message = "Endereço de entrega é obrigatório")
	private String deliveryAddress;

	@Schema(description = "Observações do pedido", example = "Sem cebola e com borda recheada")
	private String notes;

	@Schema(description = "Itens do pedido")
	@NotNull(message = "Itens do pedido são obrigatórios")
	@NotEmpty(message = "Pedido deve conter pelo menos um item")
	private List<@Valid CreateOrderItemDTO> items;

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

	public List<CreateOrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CreateOrderItemDTO> items) {
		this.items = items;
	}
}
