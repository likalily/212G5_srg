package com.reserva.hotel.scc.controller;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.reserva.hotel.scc.servico.QuartoServico;
import com.reserva.hotel.scc.model.Quarto;

@Controller
@RequestMapping(path = "/sig")
public class QuartoController {
	Logger logger = LogManager.getLogger(QuartoController.class);
	@Autowired
	QuartoServico servico;

	@GetMapping("/quartos")
	public ModelAndView retornaFormDeConsultaTodosQuartos() {
		ModelAndView modelAndView = new ModelAndView("consultarQuarto");
		modelAndView.addObject("quartos", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/quarto")
	public ModelAndView retornaFormDeCadastroDe(Quarto quarto) {
		ModelAndView mv = new ModelAndView("cadastrarQuarto");
		mv.addObject("quarto", quarto);
		return mv;
	}

	@GetMapping("/quartos/{idQuarto}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarQuarto(@PathVariable("idQuarto") Long idQuarto) {
		ModelAndView modelAndView = new ModelAndView("atualizarQuarto");
		modelAndView.addObject("quarto", servico.findByIdQuarto(idQuarto)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/quarto/{idQuarto}")
	public ModelAndView excluirNoFormDeConsultaQuarto(@PathVariable("idQuarto") Long idQuarto) {
		servico.deleteById(idQuarto);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + idQuarto);
		ModelAndView modelAndView = new ModelAndView("consultarQuarto");
		modelAndView.addObject("quartos", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/quartos")
	public ModelAndView save(@Valid Quarto quarto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarQuarto");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarQuarto");
		} else {
			modelAndView = servico.saveOrUpdate(quarto);
		}
		return modelAndView;
	}

	@PostMapping("/quartos/{idQuarto}")
	public ModelAndView atualizaQuarto(@PathVariable("idQuarto") Long idQuarto, @Valid Quarto quarto,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarQuarto");
		if (result.hasErrors()) {
			quarto.setIdQuarto(idQuarto);
			return new ModelAndView("atualizarQuarto");
		}
		// programacao defensiva - deve-se verificar se o Cliente existe antes de
		// atualizar
		Quarto umQuarto = servico.findByIdQuarto(idQuarto);
		umQuarto.setNumQuarto(quarto.getNumQuarto());
		umQuarto.setCategoriaQuarto(quarto.getCategoriaQuarto());
		umQuarto.setQuantidadeDias(quarto.getQuantidadeDias());
		umQuarto.setValorQuarto(quarto.getValorQuarto());
		modelAndView = servico.saveOrUpdate(umQuarto);

		return modelAndView;
	}

}