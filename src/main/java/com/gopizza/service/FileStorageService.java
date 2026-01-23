package com.gopizza.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageService(@Value("${file.upload-dir:uploads/images}") String uploadDir) {
		this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Não foi possível criar o diretório de upload: " + uploadDir, ex);
		}
	}

	public String storeFile(MultipartFile file) {
		if (file.isEmpty()) {
			throw new RuntimeException("Arquivo vazio não pode ser salvo");
		}

		// Validar tipo de arquivo
		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new RuntimeException("Apenas arquivos de imagem são permitidos");
		}

		// Validar tamanho (máximo 5MB)
		if (file.getSize() > 5 * 1024 * 1024) {
			throw new RuntimeException("Arquivo muito grande. Tamanho máximo: 5MB");
		}

		try {
			// Gerar nome único para o arquivo
			String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
			String fileExtension = "";
			int lastDotIndex = originalFilename.lastIndexOf('.');
			if (lastDotIndex > 0) {
				fileExtension = originalFilename.substring(lastDotIndex);
			}
			
			String fileName = UUID.randomUUID().toString() + fileExtension;
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			
			// Salvar arquivo
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		} catch (IOException ex) {
			throw new RuntimeException("Erro ao salvar arquivo: " + file.getOriginalFilename(), ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if (resource.exists() && resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Arquivo não encontrado: " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new RuntimeException("Erro ao carregar arquivo: " + fileName, ex);
		}
	}

	public void deleteFile(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Files.deleteIfExists(filePath);
		} catch (IOException ex) {
			throw new RuntimeException("Erro ao deletar arquivo: " + fileName, ex);
		}
	}

	public boolean fileExists(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			return Files.exists(filePath) && Files.isRegularFile(filePath);
		} catch (Exception ex) {
			return false;
		}
	}
}
