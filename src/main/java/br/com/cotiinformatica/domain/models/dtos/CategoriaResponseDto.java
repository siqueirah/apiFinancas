package br.com.cotiinformatica.domain.models.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class CategoriaResponseDto {
	
	private UUID id;
	private String nome;
}
