package br.com.cotiinformatica.domain.services.impl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.models.dtos.CategoriaResponseDto;
import br.com.cotiinformatica.domain.models.dtos.ContaCategoriaRequestDto;
import br.com.cotiinformatica.domain.models.dtos.ContaCategoriaResponseDto;
import br.com.cotiinformatica.domain.models.dtos.ContaResponseDto;
import br.com.cotiinformatica.domain.models.entities.Categoria;
import br.com.cotiinformatica.domain.models.entities.Conta;
import br.com.cotiinformatica.domain.services.interfaces.ContaCategoriaDomainService;
import br.com.cotiinformatica.infrastructure.repositories.CategoriaRepository;
import br.com.cotiinformatica.infrastructure.repositories.ContaRepository;
@Service
public class ContaCategoriaDomainServiceImpl implements ContaCategoriaDomainService {
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ContaCategoriaResponseDto inserir(ContaCategoriaRequestDto request) throws Exception {
		//consultar a conta no banco de dados através do ID
		Conta conta = contaRepository.findById(request.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada. Verifique o ID informado."));
		
		//consultar a categoria no banco de dados através do ID
		Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada. Verifique o ID informado."));
		
		//adicionando a categoria na conta
		conta.getCategoria().add(categoria);
		
		contaRepository.save(conta);
		
		//copiando os dados da conta e da categoria para o DTO de resposta
		ContaCategoriaResponseDto response = new ContaCategoriaResponseDto();
		response.setConta(modelMapper.map(conta, ContaResponseDto.class));
		response.setCategoria(modelMapper.map(categoria, CategoriaResponseDto.class));
		
		return response;
	}	
}


