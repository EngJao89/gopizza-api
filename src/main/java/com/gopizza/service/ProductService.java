package com.gopizza.service;

import com.gopizza.dto.CreateProductDTO;
import com.gopizza.dto.ProductResponseDTO;
import com.gopizza.dto.UpdateProductDTO;
import com.gopizza.model.Product;
import com.gopizza.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

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
		product.setImageUrl(dto.getImagemUrl().trim());

		Product saved = productRepository.save(product);
		return toResponse(saved);
	}

	@Transactional
	public ProductResponseDTO createProductWithImage(String marca, String titulo, String descricao, String conteudo, MultipartFile imagem) {
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
		dto.setImagemUrl(imagemUrl);
		return createProduct(dto);
	}

	@Transactional(readOnly = true)
	public ProductResponseDTO getProductById(UUID id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
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
				.collect(Collectors.toList());
	}

	@Transactional
	public ProductResponseDTO updateProduct(UUID id, CreateProductDTO dto) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));

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
		product.setImageUrl(dto.getImagemUrl().trim());

		return toResponse(productRepository.save(product));
	}

	@Transactional
	public ProductResponseDTO updateProductPartial(UUID id, UpdateProductDTO dto) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));

		String newMarca = product.getMarca();
		if (dto.getMarca() != null && !dto.getMarca().trim().isEmpty()) {
			newMarca = dto.getMarca().trim();
		}
		String newTitulo = product.getTitulo();
		if (dto.getTitulo() != null && !dto.getTitulo().trim().isEmpty()) {
			newTitulo = dto.getTitulo().trim();
		}

		if ((!product.getMarca().equals(newMarca) || !product.getTitulo().equals(newTitulo))
				&& productRepository.existsByMarcaAndTituloAndIdNot(newMarca, newTitulo, id)) {
			throw new IllegalArgumentException("Já existe produto com a mesma marca e título: " + newMarca + " / " + newTitulo);
		}

		if (dto.getMarca() != null && !dto.getMarca().trim().isEmpty()) {
			product.setMarca(newMarca);
		}
		if (dto.getTitulo() != null && !dto.getTitulo().trim().isEmpty()) {
			product.setTitulo(newTitulo);
		}
		if (dto.getDescricao() != null && !dto.getDescricao().trim().isEmpty()) {
			product.setDescricao(dto.getDescricao().trim());
		}
		if (dto.getConteudo() != null && !dto.getConteudo().trim().isEmpty()) {
			product.setConteudo(dto.getConteudo().trim());
		}
		if (dto.getImagemUrl() != null && !dto.getImagemUrl().trim().isEmpty()) {
			product.setImageUrl(dto.getImagemUrl().trim());
		}

		return toResponse(productRepository.save(product));
	}

	@Transactional
	public void deleteProduct(UUID id) {
		if (!productRepository.existsById(id)) {
			throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
		}
		productRepository.deleteById(id);
	}

	private ProductResponseDTO toResponse(Product product) {
		return new ProductResponseDTO(
				product.getId(),
				product.getMarca(),
				product.getTitulo(),
				product.getDescricao(),
				product.getConteudo(),
				product.getImageUrl(),
				product.getCreatedAt(),
				product.getUpdatedAt()
		);
	}
}
