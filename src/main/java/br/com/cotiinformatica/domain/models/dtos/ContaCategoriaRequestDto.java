package br.com.cotiinformatica.domain.models.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ContaCategoriaRequestDto {
	
	public UUID ContaId;
	public UUID CategoriaId;

	
	
}
