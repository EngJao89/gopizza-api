package com.gopizza.controller;

import com.gopizza.dto.CreateProductDTO;
import com.gopizza.dto.ProductResponseDTO;
import com.gopizza.dto.UpdateProductDTO;
import com.gopizza.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "API para cadastro de produtos (marca, título, descrição, conteúdo e imagem)")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	@Operation(
			summary = "Criar produto (JSON)",
			description = "Cria um produto. Envie imagemUrl obtida após POST /api/images/upload, ou use POST /api/products/with-image com multipart."
	)
	public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody CreateProductDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(dto));
	}

	@PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(
			summary = "Criar produto com upload de imagem",
			description = "Cadastro completo em uma requisição: campos de texto + arquivo de imagem (campo \"imagem\")."
	)
	public ResponseEntity<ProductResponseDTO> createProductWithImage(
			@RequestParam String marca,
			@RequestParam String titulo,
			@RequestParam String descricao,
			@RequestParam String conteudo,
			@RequestParam BigDecimal valor,
			@RequestParam("imagem") MultipartFile imagem) {
		if (marca == null || marca.isBlank()) {
			throw new IllegalArgumentException("Marca é obrigatória");
		}
		if (titulo == null || titulo.isBlank()) {
			throw new IllegalArgumentException("Título é obrigatório");
		}
		if (descricao == null || descricao.isBlank()) {
			throw new IllegalArgumentException("Descrição é obrigatória");
		}
		if (conteudo == null || conteudo.isBlank()) {
			throw new IllegalArgumentException("Conteúdo é obrigatório");
		}
		if (descricao.trim().length() < 5) {
			throw new IllegalArgumentException("Descrição deve ter no mínimo 5 caracteres");
		}
		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Valor deve ser maior que zero");
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(productService.createProductWithImage(marca, titulo, descricao, conteudo, valor, imagem));
	}

	@GetMapping("/lookup")
	@Operation(summary = "Buscar produto por marca e título", description = "Query params: marca, titulo")
	public ResponseEntity<ProductResponseDTO> getProductByMarcaAndTitulo(
			@RequestParam String marca,
			@RequestParam String titulo) {
		return ResponseEntity.ok(productService.getProductByMarcaAndTitulo(marca, titulo));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar produto por ID")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}

	@GetMapping
	@Operation(summary = "Listar todos os produtos")
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar produto (substituição completa)")
	public ResponseEntity<ProductResponseDTO> updateProduct(
			@PathVariable UUID id,
			@Valid @RequestBody CreateProductDTO dto) {
		return ResponseEntity.ok(productService.updateProduct(id, dto));
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Atualizar produto parcialmente")
	public ResponseEntity<ProductResponseDTO> updateProductPartial(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateProductDTO dto) {
		return ResponseEntity.ok(productService.updateProductPartial(id, dto));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Remover produto")
	public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
