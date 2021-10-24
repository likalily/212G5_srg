package com.reserva.hotel.scc.servico;

import org.springframework.web.servlet.ModelAndView;
import com.reserva.hotel.scc.model.Quarto;

public interface QuartoServico {
	public Iterable<Quarto> findAll();

	public Quarto findByIdQuarto(Long idQuarto);

	public void deleteById(Long idQuarto);

	public Quarto findById(Long idQuarto);

	public ModelAndView saveOrUpdate(Quarto quarto);


}