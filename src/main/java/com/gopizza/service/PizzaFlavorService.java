package com.gopizza.service;

import com.gopizza.dto.CreatePizzaFlavorDTO;
import com.gopizza.dto.PizzaFlavorResponseDTO;
import com.gopizza.dto.UpdatePizzaFlavorDTO;
import com.gopizza.model.PizzaFlavor;
import com.gopizza.repository.PizzaFlavorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PizzaFlavorService {

	private final PizzaFlavorRepository pizzaFlavorRepository;

	@Autowired
	public PizzaFlavorService(PizzaFlavorRepository pizzaFlavorRepository) {
		this.pizzaFlavorRepository = pizzaFlavorRepository;
	}

	@Transactional
	public PizzaFlavorResponseDTO createPizzaFlavor(CreatePizzaFlavorDTO createPizzaFlavorDTO) {
		if (pizzaFlavorRepository.existsByName(createPizzaFlavorDTO.getName())) {
			throw new IllegalArgumentException("Sabor de pizza já existe com o nome: " + createPizzaFlavorDTO.getName());
		}

		PizzaFlavor pizzaFlavor = new PizzaFlavor();
		pizzaFlavor.setName(createPizzaFlavorDTO.getName());
		pizzaFlavor.setDescription(createPizzaFlavorDTO.getDescription());
		pizzaFlavor.setAvailableOptions(createPizzaFlavorDTO.getAvailableOptions());
		pizzaFlavor.setSizesAndPrices(createPizzaFlavorDTO.getSizesAndPrices());

		PizzaFlavor savedPizzaFlavor = pizzaFlavorRepository.save(pizzaFlavor);
		return convertToDTO(savedPizzaFlavor);
	}

	@Transactional(readOnly = true)
	public PizzaFlavorResponseDTO getPizzaFlavorById(UUID id) {
		PizzaFlavor pizzaFlavor = pizzaFlavorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Sabor de pizza não encontrado com ID: " + id));
		return convertToDTO(pizzaFlavor);
	}

	@Transactional(readOnly = true)
	public PizzaFlavorResponseDTO getPizzaFlavorByName(String name) {
		PizzaFlavor pizzaFlavor = pizzaFlavorRepository.findByName(name)
				.orElseThrow(() -> new IllegalArgumentException("Sabor de pizza não encontrado com nome: " + name));
		return convertToDTO(pizzaFlavor);
	}

	@Transactional(readOnly = true)
	public List<PizzaFlavorResponseDTO> getAllPizzaFlavors() {
		return pizzaFlavorRepository.findAll().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public PizzaFlavorResponseDTO updatePizzaFlavor(UUID id, CreatePizzaFlavorDTO updatePizzaFlavorDTO) {
		PizzaFlavor pizzaFlavor = pizzaFlavorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Sabor de pizza não encontrado com ID: " + id));

		if (!pizzaFlavor.getName().equals(updatePizzaFlavorDTO.getName()) &&
				pizzaFlavorRepository.existsByName(updatePizzaFlavorDTO.getName())) {
			throw new IllegalArgumentException("Sabor de pizza já existe com o nome: " + updatePizzaFlavorDTO.getName());
		}

		pizzaFlavor.setName(updatePizzaFlavorDTO.getName());
		pizzaFlavor.setDescription(updatePizzaFlavorDTO.getDescription());
		pizzaFlavor.setAvailableOptions(updatePizzaFlavorDTO.getAvailableOptions());
		pizzaFlavor.setSizesAndPrices(updatePizzaFlavorDTO.getSizesAndPrices());

		PizzaFlavor updatedPizzaFlavor = pizzaFlavorRepository.save(pizzaFlavor);
		return convertToDTO(updatedPizzaFlavor);
	}

	@Transactional
	public PizzaFlavorResponseDTO updatePizzaFlavorPartial(UUID id, UpdatePizzaFlavorDTO updatePizzaFlavorDTO) {
		PizzaFlavor pizzaFlavor = pizzaFlavorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Sabor de pizza não encontrado com ID: " + id));

		if (updatePizzaFlavorDTO.getName() != null && !updatePizzaFlavorDTO.getName().trim().isEmpty()) {
			String newName = updatePizzaFlavorDTO.getName().trim();
			if (!pizzaFlavor.getName().equals(newName) &&
					pizzaFlavorRepository.existsByName(newName)) {
				throw new IllegalArgumentException("Sabor de pizza já existe com o nome: " + newName);
			}
			pizzaFlavor.setName(newName);
		}

		if (updatePizzaFlavorDTO.getDescription() != null && !updatePizzaFlavorDTO.getDescription().trim().isEmpty()) {
			pizzaFlavor.setDescription(updatePizzaFlavorDTO.getDescription().trim());
		}

		if (updatePizzaFlavorDTO.getAvailableOptions() != null) {
			pizzaFlavor.setAvailableOptions(updatePizzaFlavorDTO.getAvailableOptions());
		}

		if (updatePizzaFlavorDTO.getSizesAndPrices() != null && !updatePizzaFlavorDTO.getSizesAndPrices().isEmpty()) {
			pizzaFlavor.setSizesAndPrices(updatePizzaFlavorDTO.getSizesAndPrices());
		}

		PizzaFlavor updatedPizzaFlavor = pizzaFlavorRepository.save(pizzaFlavor);
		return convertToDTO(updatedPizzaFlavor);
	}

	@Transactional
	public void deletePizzaFlavor(UUID id) {
		if (!pizzaFlavorRepository.existsById(id)) {
			throw new IllegalArgumentException("Sabor de pizza não encontrado com ID: " + id);
		}
		pizzaFlavorRepository.deleteById(id);
	}

	public PizzaFlavorResponseDTO convertToDTO(PizzaFlavor pizzaFlavor) {
		return new PizzaFlavorResponseDTO(
				pizzaFlavor.getId(),
				pizzaFlavor.getName(),
				pizzaFlavor.getDescription(),
				pizzaFlavor.getAvailableOptions(),
				pizzaFlavor.getSizesAndPrices(),
				pizzaFlavor.getCreatedAt(),
				pizzaFlavor.getUpdatedAt()
		);
	}
}
