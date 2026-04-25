package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "DTO para atualização parcial de pedido (todos os campos são opcionais)")
public class UpdateOrderDTO {

	@Schema(description = "Nome do cliente", example = "João Silva")
	@Size(max = 120, message = "Nome do cliente deve ter no máximo 120 caracteres")
	@NotBlank(message = "Nome do cliente não pode ser vazio")
	private String customerName;

	@Schema(description = "Telefone do cliente", example = "(11) 99999-9999")
	@Size(max = 30, message = "Telefone do cliente deve ter no máximo 30 caracteres")
	@NotBlank(message = "Telefone do cliente não pode ser vazio")
	private String customerPhone;

	@Schema(description = "Rua de entrega", example = "Rua das Flores")
	@Size(max = 200, message = "Rua de entrega deve ter no máximo 200 caracteres")
	@NotBlank(message = "Rua de entrega não pode ser vazia")
	private String deliveryAddress;

	@Schema(description = "Número de entrega", example = "123")
	@Size(max = 20, message = "Número de entrega deve ter no máximo 20 caracteres")
	@NotBlank(message = "Número de entrega não pode ser vazio")
	private String deliveryNumber;

	@Schema(description = "Bairro de entrega", example = "Centro")
	@Size(max = 120, message = "Bairro de entrega deve ter no máximo 120 caracteres")
	@NotBlank(message = "Bairro de entrega não pode ser vazio")
	private String deliveryNeighborhood;

	@Schema(description = "Observações do pedido (vazio remove a observação)")
	@Size(max = 2000, message = "Observações devem ter no máximo 2000 caracteres")
	private String notes;

	@Schema(
			description = "Status do pedido",
			example = "CONFIRMED",
			allowableValues = { "PENDING", "CONFIRMED", "IN_PREPARATION", "OUT_FOR_DELIVERY", "DELIVERED", "CANCELLED" }
	)
	@Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
	private String status;

	@Schema(description = "Pizzas do pedido (se enviado junto com produtos ou sozinho, substitui os itens)")
	private List<@Valid CreateOrderPizzaDTO> pizzas;

	@Schema(description = "Produtos do pedido (se enviado junto com pizzas ou sozinho, substitui os itens)")
	private List<@Valid CreateOrderProductDTO> products;

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

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public String getDeliveryNeighborhood() {
		return deliveryNeighborhood;
	}

	public void setDeliveryNeighborhood(String deliveryNeighborhood) {
		this.deliveryNeighborhood = deliveryNeighborhood;
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

	public List<CreateOrderPizzaDTO> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<CreateOrderPizzaDTO> pizzas) {
		this.pizzas = pizzas;
	}

	public List<CreateOrderProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<CreateOrderProductDTO> products) {
		this.products = products;
	}
}
