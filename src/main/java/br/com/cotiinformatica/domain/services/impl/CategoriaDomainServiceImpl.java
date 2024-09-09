package br.com.cotiinformatica.domain.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.models.dtos.CategoriaRequestDto;
import br.com.cotiinformatica.domain.models.dtos.CategoriaResponseDto;
import br.com.cotiinformatica.domain.models.entities.Categoria;
import br.com.cotiinformatica.domain.services.interfaces.CategoriaDomainService;
import br.com.cotiinformatica.infrastructure.repositories.CategoriaRepository;

@Service
public class CategoriaDomainServiceImpl implements CategoriaDomainService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoriaResponseDto inserir(CategoriaRequestDto request) throws Exception {

		//capturar os dados do DTO
		Categoria categoria = modelMapper.map(request, Categoria.class);
		categoria.setId(UUID.randomUUID());
		
		//gravar os dados da categoria no banco de dados
		categoriaRepository.save(categoria);
		
		//retornar os dados de resposta
		CategoriaResponseDto response = modelMapper.map(categoria, CategoriaResponseDto.class);		
		return response;
	}

	@Override
	public CategoriaResponseDto atualizar(UUID id, CategoriaRequestDto request) throws Exception {

		//consultando a categoria no banco de dados através do ID,
		//e caso não seja encontrado, será retornado erro
		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada para edição. Verifique o ID informado."));
		
		//modificar o nome da categoria
		categoria.setNome(request.getNome());
		
		//atualizando no banco de dados
		categoriaRepository.save(categoria);
		
		//retornando os dados de resposta
		CategoriaResponseDto response = modelMapper.map(categoria, CategoriaResponseDto.class);
		return response;
	}

	@Override
	public CategoriaResponseDto excluir(UUID id) throws Exception {
		
		//consultando a categoria no banco de dados através do ID,
		//e caso não seja encontrado, será retornado erro
		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada para exclusão. Verifique o ID informado."));
		
		//excluindo a categoria
		categoriaRepository.delete(categoria);
		
		//retornando os dados de resposta
		CategoriaResponseDto response = modelMapper.map(categoria, CategoriaResponseDto.class);
		return response;
	}

	@Override
	public List<CategoriaResponseDto> consultar() throws Exception {

		//consultando todas as categorias do banco de dados
		List<Categoria> categorias = categoriaRepository.findAll();
		
		//copiando os dados obtidos para uma lista da classe DTO
		List<CategoriaResponseDto> response = new ArrayList<CategoriaResponseDto>();
		for(Categoria categoria : categorias) {
			response.add(modelMapper.map(categoria, CategoriaResponseDto.class));
		}
		
		return response;
	}

	@Override
	public CategoriaResponseDto obterPorId(UUID id) throws Exception {

		//consultando a categoria no banco de dados através do id
		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada. Verifique o ID informado."));
		
		CategoriaResponseDto response = modelMapper.map(categoria, CategoriaResponseDto.class);		
		return response;
	}

}



