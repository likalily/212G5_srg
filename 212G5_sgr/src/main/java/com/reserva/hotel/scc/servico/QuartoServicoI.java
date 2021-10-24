package com.reserva.hotel.scc.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.reserva.hotel.scc.model.Quarto;
import com.reserva.hotel.scc.model.QuartoRepository;

@Service
public class QuartoServicoI implements QuartoServico {
	Logger logger = LogManager.getLogger(QuartoServicoI.class);
	@Autowired
	private QuartoRepository repository;

	public Iterable<Quarto> findAll() {
		return repository.findAll();
	}

	public Quarto findByIdQuarto(Long idQuarto) {
		return repository.findByIdQuarto(idQuarto);
	}

	public void deleteById(Long idQuarto) {
		repository.deleteById(idQuarto);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + idQuarto);
	}

	public Quarto findById(Long idQuarto) {
		return repository.findById(idQuarto).get();
	}

	public ModelAndView saveOrUpdate(Quarto quarto) {
		ModelAndView modelAndView = new ModelAndView("consultarQuarto");
		try {
				repository.save(quarto);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("quartos", repository.findAll());
		} catch (Exception e) { // captura validacoes na camada de persistencia
			modelAndView.setViewName("cadastrarQuarto");
			modelAndView.addObject("message", "Dados invalidos - " + e.getMessage());
			logger.error(">>>>>> 4. erro nao esperado ==> " + e.getMessage());
		}
		return modelAndView;
	}
	
}