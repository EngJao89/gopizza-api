package com.gopizza.service;

import com.gopizza.dto.CreateProductDTO;
import com.gopizza.dto.ProductResponseDTO;
import com.gopizza.dto.UpdateProductDTO;
import com.gopizza.model.Product;
import com.gopizza.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
	private static final String PRODUCT_NOT_FOUND_MESSAGE = "Produto não encontrado com ID: ";

	private final ProductRepository productRepository;
	private final FileStorageService fileStorageService;

	public ProductService(ProductRepository productRepository, FileStorageService fileStorageService) {
		this.productRepository = productRepository;
		this.fileStorageService = fileStorageService;
	}

	@Transactional
	public ProductResponseDTO createProduct(CreateProductDTO dto) {
		String marca = dto.getMarca().trim();
		String titulo = dto.getTitulo().trim();
		if (productRepository.existsByMarcaAndTitulo(marca, titulo)) {
			throw new IllegalArgumentException("Já existe produto com a mesma marca e título: " + marca + " / " + titulo);
		}

		Product product = new Product();
		product.setMarca(marca);
		product.setTitulo(titulo);
		product.setDescricao(dto.getDescricao().trim());
		product.setConteudo(dto.getConteudo().trim());
		product.setValor(normalizePrice(dto.getValor()));
		product.setImageUrl(dto.getImagemUrl().trim());

		Product saved = productRepository.save(product);
		return toResponse(saved);
	}

	@Transactional
	public ProductResponseDTO createProductWithImage(String marca, String titulo, String descricao, String conteudo, BigDecimal valor, MultipartFile imagem) {
		if (imagem == null || imagem.isEmpty()) {
			throw new IllegalArgumentException("Imagem é obrigatória");
		}
		String fileName = fileStorageService.storeFile(imagem, titulo);
		String imagemUrl = "/api/images/" + fileName;

		CreateProductDTO dto = new CreateProductDTO();
		dto.setMarca(marca);
		dto.setTitulo(titulo);
		dto.setDescricao(descricao);
		dto.setConteudo(conteudo);
		dto.setValor(valor);
		dto.setImagemUrl(imagemUrl);

		String normalizedMarca = dto.getMarca().trim();
		String normalizedTitulo = dto.getTitulo().trim();
		if (productRepository.existsByMarcaAndTitulo(normalizedMarca, normalizedTitulo)) {
			throw new IllegalArgumentException("Já existe produto com a mesma marca e título: " + normalizedMarca + " / " + normalizedTitulo);
		}

		Product product = new Product();
		product.setMarca(normalizedMarca);
		product.setTitulo(normalizedTitulo);
		product.setDescricao(dto.getDescricao().trim());
		product.setConteudo(dto.getConteudo().trim());
		product.setValor(normalizePrice(dto.getValor()));
		product.setImageUrl(dto.getImagemUrl().trim());
		return toResponse(productRepository.save(product));
	}

	@Transactional(readOnly = true)
	public ProductResponseDTO getProductById(UUID id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND_MESSAGE + id));
		return toResponse(product);
	}

	@Transactional(readOnly = true)
	public ProductResponseDTO getProductByMarcaAndTitulo(String marca, String titulo) {
		Product product = productRepository.findByMarcaAndTitulo(marca.trim(), titulo.trim())
				.orElseThrow(() -> new IllegalArgumentException(
						"Produto não encontrado: " + marca.trim() + " / " + titulo.trim()));
		return toResponse(product);
	}

	@Transactional(readOnly = true)
	public List<ProductResponseDTO> getAllProducts() {
		return productRepository.findAll().stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	public ProductResponseDTO updateProduct(UUID id, CreateProductDTO dto) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND_MESSAGE + id));

		String marca = dto.getMarca().trim();
		String titulo = dto.getTitulo().trim();
		if ((!product.getMarca().equals(marca) || !product.getTitulo().equals(titulo))
				&& productRepository.existsByMarcaAndTituloAndIdNot(marca, titulo, id)) {
			throw new IllegalArgumentException("Já existe produto com a mesma marca e título: " + marca + " / " + titulo);
		}

		product.setMarca(marca);
		product.setTitulo(titulo);
		product.setDescricao(dto.getDescricao().trim());
		product.setConteudo(dto.getConteudo().trim());
		product.setValor(normalizePrice(dto.getValor()));
		product.setImageUrl(dto.getImagemUrl().trim());

		return toResponse(productRepository.save(product));
	}

	@Transactional
	public ProductResponseDTO updateProductPartial(UUID id, UpdateProductDTO dto) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND_MESSAGE + id));

		String newMarca = resolveString(dto.getMarca(), product.getMarca());
		String newTitulo = resolveString(dto.getTitulo(), product.getTitulo());

		if ((!product.getMarca().equals(newMarca) || !product.getTitulo().equals(newTitulo))
				&& productRepository.existsByMarcaAndTituloAndIdNot(newMarca, newTitulo, id)) {
			throw new IllegalArgumentException("Já existe produto com a mesma marca e título: " + newMarca + " / " + newTitulo);
		}

		if (isFilled(dto.getMarca())) {
			product.setMarca(newMarca);
		}
		if (isFilled(dto.getTitulo())) {
			product.setTitulo(newTitulo);
		}
		if (isFilled(dto.getDescricao())) {
			product.setDescricao(dto.getDescricao().trim());
		}
		if (isFilled(dto.getConteudo())) {
			product.setConteudo(dto.getConteudo().trim());
		}
		if (dto.getValor() != null) {
			product.setValor(normalizePrice(dto.getValor()));
		}
		if (isFilled(dto.getImagemUrl())) {
			product.setImageUrl(dto.getImagemUrl().trim());
		}

		return toResponse(productRepository.save(product));
	}

	@Transactional
	public void deleteProduct(UUID id) {
		if (!productRepository.existsById(id)) {
			throw new IllegalArgumentException(PRODUCT_NOT_FOUND_MESSAGE + id);
		}
		productRepository.deleteById(id);
	}

	private ProductResponseDTO toResponse(Product product) {
		ProductResponseDTO response = new ProductResponseDTO();
		response.setId(product.getId());
		response.setMarca(product.getMarca());
		response.setTitulo(product.getTitulo());
		response.setDescricao(product.getDescricao());
		response.setConteudo(product.getConteudo());
		response.setValor(product.getValor());
		response.setImagemUrl(product.getImageUrl());
		response.setCreatedAt(product.getCreatedAt());
		response.setUpdatedAt(product.getUpdatedAt());
		return response;
	}

	private BigDecimal normalizePrice(BigDecimal price) {
		return price.setScale(2, RoundingMode.HALF_UP);
	}

	private boolean isFilled(String value) {
		return value != null && !value.trim().isEmpty();
	}

	private String resolveString(String incoming, String fallback) {
		return isFilled(incoming) ? incoming.trim() : fallback;
	}
}
