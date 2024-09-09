package br.com.cotiinformatica.domain.services.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cotiinformatica.domain.models.dtos.ContaRequestDto;
import br.com.cotiinformatica.domain.models.dtos.ContaResponseDto;
import br.com.cotiinformatica.domain.models.entities.Conta;
import br.com.cotiinformatica.domain.services.interfaces.ContaDomainService;
import br.com.cotiinformatica.infrastructure.repositories.ContaRepository;
@Service
public class ContaDomainServiceImpl implements ContaDomainService {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ContaResponseDto inserir(ContaRequestDto request) throws Exception {
		Conta conta = modelMapper.map(request, Conta.class);
		conta.setId(UUID.randomUUID());
		contaRepository.save(conta);
		ContaResponseDto response = modelMapper.map(conta, ContaResponseDto.class);
		return response;
	}
	
	@Override
	public ContaResponseDto atualizar(UUID id, ContaRequestDto request) throws Exception {
		Conta Conta = contaRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("Conta não encontrada para edição. Verifique o ID informado."));
		Conta.setNome(request.getNome());
		contaRepository.save(Conta);
		ContaResponseDto response = modelMapper.map(Conta, ContaResponseDto.class);
		return response;
		
	}
	@Override
	public ContaResponseDto excluir(UUID id) throws Exception {
		Conta Conta = contaRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("Conta não encontrada para exclusão. Verifique o ID informado."));
		contaRepository.delete(Conta);
		ContaResponseDto response = modelMapper.map(Conta, ContaResponseDto.class);
		return response;
	}
	@Override
	public List<ContaResponseDto> consultar() throws Exception {
		List<Conta> contas = contaRepository.findAll();
		List<ContaResponseDto> response = new ArrayList<ContaResponseDto>();
		for (Conta conta : contas) {
			response.add(modelMapper.map(conta, ContaResponseDto.class));
		}
		return response;
	}
	@Override
	public ContaResponseDto obterPorId(UUID id) throws Exception {
		Conta Conta = contaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada. Verifique o ID informado."));
		ContaResponseDto response = modelMapper.map(Conta, ContaResponseDto.class);
		return response;
	}
}


