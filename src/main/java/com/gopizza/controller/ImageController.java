package com.gopizza.controller;

import com.gopizza.dto.ImageInfoDTO;
import com.gopizza.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Images", description = "API para upload e download de imagens")
public class ImageController {

	private final FileStorageService fileStorageService;

	public ImageController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@GetMapping
	@Operation(
		summary = "Listar todas as imagens",
		description = "Retorna uma lista com todas as imagens disponíveis, incluindo nome, URL, tamanho e data de modificação"
	)
	public ResponseEntity<List<ImageInfoDTO>> listAllImages() {
		List<FileStorageService.ImageFileInfo> fileInfos = fileStorageService.listAllFiles();
		
		List<ImageInfoDTO> images = fileInfos.stream()
				.map(fileInfo -> {
					String fileDownloadUri = "/api/images/" + fileInfo.getFileName();
					String sizeFormatted = fileStorageService.formatFileSize(fileInfo.getSize());
					
					return new ImageInfoDTO(
							fileInfo.getFileName(),
							fileDownloadUri,
							fileInfo.getSize(),
							sizeFormatted,
							fileInfo.getLastModified()
					);
				})
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(images);
	}

	@PostMapping("/upload")
	@Operation(
		summary = "Upload de imagem",
		description = "Faz upload de uma imagem com um nome opcional para facilitar identificação. Retorna o nome do arquivo e URL para acesso."
	)
	public ResponseEntity<Map<String, String>> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam(value = "name", required = false) String name) {
		String fileName = fileStorageService.storeFile(file, name);
		String fileDownloadUri = "/api/images/" + fileName;
		
		Map<String, String> response = new HashMap<>();
		response.put("fileName", fileName);
		response.put("fileDownloadUri", fileDownloadUri);
		response.put("message", "Upload realizado com sucesso");
		if (name != null && !name.trim().isEmpty()) {
			response.put("name", name);
		}
		
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{fileName:.+}")
	@Operation(
		summary = "Download de imagem",
		description = "Retorna a imagem pelo nome do arquivo"
	)
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		
		String contentType = "application/octet-stream";
		try {
			String originalFileName = resource.getFilename();
			if (originalFileName != null) {
				if (originalFileName.toLowerCase().endsWith(".jpg") || originalFileName.toLowerCase().endsWith(".jpeg")) {
					contentType = MediaType.IMAGE_JPEG_VALUE;
				} else if (originalFileName.toLowerCase().endsWith(".png")) {
					contentType = MediaType.IMAGE_PNG_VALUE;
				} else if (originalFileName.toLowerCase().endsWith(".gif")) {
					contentType = MediaType.IMAGE_GIF_VALUE;
				} else if (originalFileName.toLowerCase().endsWith(".webp")) {
					contentType = "image/webp";
				}
			}
		} catch (Exception ex) {
			// Usar content type padrão se não conseguir determinar
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@DeleteMapping("/{fileName:.+}")
	@Operation(
		summary = "Deletar imagem",
		description = "Remove uma imagem do servidor"
	)
	public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String fileName) {
		fileStorageService.deleteFile(fileName);
		
		Map<String, String> response = new HashMap<>();
		response.put("message", "Imagem deletada com sucesso: " + fileName);
		
		return ResponseEntity.ok(response);
	}
}
