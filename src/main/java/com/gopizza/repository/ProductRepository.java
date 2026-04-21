package com.gopizza.repository;

import com.gopizza.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

	Optional<Product> findByMarcaAndTitulo(String marca, String titulo);

	boolean existsByMarcaAndTitulo(String marca, String titulo);

	boolean existsByMarcaAndTituloAndIdNot(String marca, String titulo, UUID id);
}
