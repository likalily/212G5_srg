package com.reserva.hotel.scc.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.reserva.hotel.scc.model.Colaborador;
import com.reserva.hotel.scc.model.ColaboradorRepository;
import com.reserva.hotel.scc.model.Endereco;
import com.reserva.hotel.scc.model.EnderecoRepository;

@Service
public class ColaboradorServicoI implements ColaboradorServico {
	Logger logger = LogManager.getLogger(ColaboradorServicoI.class);
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Iterable<Colaborador> findAll() {
		return colaboradorRepository.findAll();
	}

	public Colaborador findByCpf(String cpf) {
		return colaboradorRepository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		colaboradorRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Colaborador findById(Long id) {
		return colaboradorRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Colaborador colaborador) {
		ModelAndView modelAndView = new ModelAndView("consultarColaborador");
		try {
			Endereco endereco = obtemEndereco(colaborador.getCep());
			if (endereco != null) {
				colaborador.setDataCadastro(new DateTime());
				endereco.setCpf(colaborador.getCpf());
				enderecoRepository.save(endereco);
				colaborador.setEndereco(endereco);
				colaboradorRepository.save(colaborador);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("colaboradores", colaboradorRepository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarColaborador");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - colaborador já cadastrado.");
				logger.info(">>>>>> 5. colaborador ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
	}

	public Endereco obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		Endereco endereco = template.getForObject(url, Endereco.class, cep);
		logger.info(">>>>>> 3. obtem endereco ==> " + endereco.toString());
		return endereco;
	}
}