package com.gopizza.repository;

import com.gopizza.model.PizzaFlavor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PizzaFlavorRepository extends JpaRepository<PizzaFlavor, UUID> {

	Optional<PizzaFlavor> findByName(String name);

	boolean existsByName(String name);
}
