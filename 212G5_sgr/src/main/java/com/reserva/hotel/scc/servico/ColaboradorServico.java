package com.reserva.hotel.scc.servico;

import org.springframework.web.servlet.ModelAndView;
import com.reserva.hotel.scc.model.Colaborador;
import com.reserva.hotel.scc.model.Endereco;

public interface ColaboradorServico {
	public Iterable<Colaborador> findAll();

	public Colaborador findByCpf(String cpf);

	public void deleteById(Long id);

	public Colaborador findById(Long id);

	public ModelAndView saveOrUpdate(Colaborador colaborador);

	public Endereco obtemEndereco(String cep);
}