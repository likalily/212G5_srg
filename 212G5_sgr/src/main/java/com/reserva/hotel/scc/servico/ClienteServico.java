package com.reserva.hotel.scc.servico;

import org.springframework.web.servlet.ModelAndView;
import com.reserva.hotel.scc.model.Cliente;
import com.reserva.hotel.scc.model.Endereco;

public interface ClienteServico {
	public Iterable<Cliente> findAll();

	public Cliente findByCpf(String cpf);

	public void deleteById(Long id);

	public Cliente findById(Long id);

	public ModelAndView saveOrUpdate(Cliente cliente);

	public Endereco obtemEndereco(String cep);
}