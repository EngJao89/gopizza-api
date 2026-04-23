package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
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

	@Schema(description = "Rua de entrega", example = "Rua das Flores")
	@NotBlank(message = "Rua de entrega é obrigatória")
	@Size(max = 200, message = "Rua de entrega deve ter no máximo 200 caracteres")
	private String deliveryAddress;

	@Schema(description = "Número de entrega", example = "123")
	@NotBlank(message = "Número de entrega é obrigatório")
	@Size(max = 20, message = "Número de entrega deve ter no máximo 20 caracteres")
	private String deliveryNumber;

	@Schema(description = "Bairro de entrega", example = "Centro")
	@NotBlank(message = "Bairro de entrega é obrigatório")
	@Size(max = 120, message = "Bairro de entrega deve ter no máximo 120 caracteres")
	private String deliveryNeighborhood;

	@Schema(description = "Pizzas do pedido")
	private List<@Valid CreateOrderPizzaDTO> pizzas;

	@Schema(description = "Produtos do pedido")
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

	@AssertTrue(message = "Pedido deve conter pelo menos um item")
	public boolean hasAnyOrderItem() {
		return isNotEmpty(pizzas) || isNotEmpty(products);
	}

	private boolean isNotEmpty(List<?> list) {
		return list != null && !list.isEmpty();
	}
}
