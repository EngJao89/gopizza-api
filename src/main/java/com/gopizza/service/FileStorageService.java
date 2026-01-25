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
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

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
		return storeFile(file, null);
	}

	public String storeFile(MultipartFile file, String name) {
		if (file.isEmpty()) {
			throw new RuntimeException("Arquivo vazio não pode ser salvo");
		}

		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new RuntimeException("Apenas arquivos de imagem são permitidos");
		}

		// Validar tamanho (máximo 5MB)
		if (file.getSize() > 5 * 1024 * 1024) {
			throw new RuntimeException("Arquivo muito grande. Tamanho máximo: 5MB");
		}

		try {
			String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
			String fileExtension = "";
			int lastDotIndex = originalFilename.lastIndexOf('.');
			if (lastDotIndex > 0) {
				fileExtension = originalFilename.substring(lastDotIndex);
			}

			String fileName;
			if (name != null && !name.trim().isEmpty()) {
				String sanitizedName = name.trim()
						.toLowerCase()
						.replaceAll("[^a-z0-9\\s-]", "")
						.replaceAll("\\s+", "-") 
						.replaceAll("-+", "-")
						.replaceAll("^-|-$", "");

				if (sanitizedName.length() > 50) {
					sanitizedName = sanitizedName.substring(0, 50);
				}

				fileName = sanitizedName + "-" + UUID.randomUUID().toString() + fileExtension;
			} else {
				fileName = UUID.randomUUID().toString() + fileExtension;
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);

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

	public List<ImageFileInfo> listAllFiles() {
		List<ImageFileInfo> fileInfos = new ArrayList<>();
		try {
			if (!Files.exists(this.fileStorageLocation) || !Files.isDirectory(this.fileStorageLocation)) {
				return fileInfos;
			}

			try (Stream<Path> paths = Files.list(this.fileStorageLocation)) {
				paths.filter(Files::isRegularFile)
						.forEach(filePath -> {
							try {
								String fileName = filePath.getFileName().toString();
								long size = Files.size(filePath);
								FileTime fileTime = Files.getLastModifiedTime(filePath);
								LocalDateTime lastModified = LocalDateTime.ofInstant(
										fileTime.toInstant(),
										ZoneId.systemDefault()
								);

								fileInfos.add(new ImageFileInfo(fileName, size, lastModified));
							} catch (IOException ex) {
								// Ignorar arquivos que não podem ser lidos
							}
						});
			}
		} catch (IOException ex) {
			throw new RuntimeException("Erro ao listar arquivos", ex);
		}

		fileInfos.sort((a, b) -> b.getLastModified().compareTo(a.getLastModified()));

		return fileInfos;
	}

	public static class ImageFileInfo {
		private final String fileName;
		private final long size;
		private final LocalDateTime lastModified;

		public ImageFileInfo(String fileName, long size, LocalDateTime lastModified) {
			this.fileName = fileName;
			this.size = size;
			this.lastModified = lastModified;
		}

		public String getFileName() {
			return fileName;
		}

		public long getSize() {
			return size;
		}

		public LocalDateTime getLastModified() {
			return lastModified;
		}
	}

	public String formatFileSize(long bytes) {
		if (bytes < 1024) {
			return bytes + " B";
		} else if (bytes < 1024 * 1024) {
			return String.format("%.2f KB", bytes / 1024.0);
		} else {
			return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
		}
	}
}
