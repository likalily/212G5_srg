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
import com.reserva.hotel.scc.servico.ColaboradorServico;
import com.reserva.hotel.scc.model.Colaborador;

@Controller
@RequestMapping(path = "/sig")
public class ColaboradorController {
Logger logger = LogManager.getLogger(ColaboradorController.class);
@Autowired
ColaboradorServico servico;
@GetMapping("/colaboradores")
public ModelAndView retornaFormDeConsultaTodosColaboradores() {
ModelAndView modelAndView = new ModelAndView("consultarColaborador");
modelAndView.addObject("colaboradores", servico.findAll());
return modelAndView;
}
@GetMapping("/colaborador")
public ModelAndView retornaFormDeCadastroDe(Colaborador colaborador) {
ModelAndView mv = new ModelAndView("cadastrarColaborador");
mv.addObject("colaborador", colaborador);
return mv;
}
@GetMapping("/colaboradores/{cpf}") // diz ao metodo que ira responder a uma requisicao do tipo get
public ModelAndView retornaFormParaEditarColaborador(@PathVariable("cpf") String cpf) {
ModelAndView modelAndView = new ModelAndView("atualizarColaborador");
modelAndView.addObject("colaborador", servico.findByCpf(cpf)); // o repositorio e injetado no controller
return modelAndView; // addObject adiciona objetos para view
}
@GetMapping("/colaborador/{id}")
public ModelAndView excluirNoFormDeConsultaColaborador(@PathVariable("id") Long id) {
servico.deleteById(id);
logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
ModelAndView modelAndView = new ModelAndView("consultarColaborador");
modelAndView.addObject("colaboradores", servico.findAll());
return modelAndView;
}
@PostMapping("/colaboradores")
public ModelAndView save(@Valid Colaborador colaborador, BindingResult result) {
ModelAndView modelAndView = new ModelAndView("consultarColaborador");
if (result.hasErrors()) {
modelAndView.setViewName("cadastrarColaborador");
} else {
modelAndView = servico.saveOrUpdate(colaborador);
}
return modelAndView;
}
@PostMapping("/colaboradores/{id}")
public ModelAndView atualizaCliente(@PathVariable("id") Long id, @Valid Colaborador colaborador, BindingResult result) {
ModelAndView modelAndView = new ModelAndView("consultarColaborador");
if (result.hasErrors()) {
colaborador.setId(id);
return new ModelAndView("atualizarColaborador");
}
// programacao defensiva - deve-se verificar se o Colaborador existe antes de atualizar
Colaborador umColaborador = servico.findById(id);
umColaborador.setCpf(colaborador.getCpf());
umColaborador.setNome(colaborador.getNome());
umColaborador.setEmail(colaborador.getEmail());
umColaborador.setCep(colaborador.getCep());
modelAndView = servico.saveOrUpdate(umColaborador);
return modelAndView;
}
}