package com.gopizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO com informações de uma imagem")
public class ImageInfoDTO {

	@Schema(description = "Nome do arquivo", example = "margherita-pizza-a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg")
	private String fileName;

	@Schema(description = "URL para acessar a imagem", example = "/api/images/margherita-pizza-a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg")
	private String fileDownloadUri;

	@Schema(description = "Tamanho do arquivo em bytes", example = "245760")
	private Long size;

	@Schema(description = "Tamanho do arquivo formatado", example = "240 KB")
	private String sizeFormatted;

	@Schema(description = "Data de criação/modificação do arquivo")
	private LocalDateTime lastModified;

	public ImageInfoDTO() {
	}

	public ImageInfoDTO(String fileName, String fileDownloadUri, Long size, String sizeFormatted, LocalDateTime lastModified) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.size = size;
		this.sizeFormatted = sizeFormatted;
		this.lastModified = lastModified;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getSizeFormatted() {
		return sizeFormatted;
	}

	public void setSizeFormatted(String sizeFormatted) {
		this.sizeFormatted = sizeFormatted;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}
}
