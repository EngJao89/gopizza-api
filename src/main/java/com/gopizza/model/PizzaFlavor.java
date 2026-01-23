package com.gopizza.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "pizza_flavors")
public class PizzaFlavor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
	private UUID id;

	@Column(nullable = false, unique = true, length = 100)
	private String name;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "available_options", columnDefinition = "jsonb")
	private List<String> availableOptions = new ArrayList<>();

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "sizes_and_prices", nullable = false, columnDefinition = "jsonb")
	private Map<String, BigDecimal> sizesAndPrices = new HashMap<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	// Constructors
	public PizzaFlavor() {
	}

	public PizzaFlavor(String name, String description, List<String> availableOptions, Map<String, BigDecimal> sizesAndPrices) {
		this.name = name;
		this.description = description;
		this.availableOptions = availableOptions != null ? availableOptions : new ArrayList<>();
		this.sizesAndPrices = sizesAndPrices != null ? sizesAndPrices : new HashMap<>();
	}

	// Getters and Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAvailableOptions() {
		return availableOptions;
	}

	public void setAvailableOptions(List<String> availableOptions) {
		this.availableOptions = availableOptions != null ? availableOptions : new ArrayList<>();
	}

	public Map<String, BigDecimal> getSizesAndPrices() {
		return sizesAndPrices;
	}

	public void setSizesAndPrices(Map<String, BigDecimal> sizesAndPrices) {
		this.sizesAndPrices = sizesAndPrices != null ? sizesAndPrices : new HashMap<>();
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PizzaFlavor that = (PizzaFlavor) o;
		return Objects.equals(id, that.id) && Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "PizzaFlavor{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", availableOptions=" + availableOptions +
				", sizesAndPrices=" + sizesAndPrices +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				'}';
	}
}
