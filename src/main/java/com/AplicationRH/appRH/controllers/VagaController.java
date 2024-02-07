package com.AplicationRH.appRH.controllers;

import com.AplicationRH.appRH.models.Candidato;
import com.AplicationRH.appRH.models.Vaga;
import com.AplicationRH.appRH.repositories.CandidatoRepository;
import com.AplicationRH.appRH.repositories.VagaRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class VagaController {

    private VagaRepository vr;
    private CandidatoRepository cr;

    // Método para retornar o formulário de cadastro de vaga
    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.GET)
    public String form(){
        return "vaga/formVaga.html";
    }

    // Método para processar o formulário de cadastro de vaga
    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.POST)
    public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "verifique os campos...");
            return "redirect:/cadastrarVaga";
        }
        vr.save(vaga);
        attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");

        return "redirect:/cadastrarVaga";
    }

    // Método para listar todas as vagas
    @RequestMapping("/vagas")
    public ModelAndView listarVagas(){
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        Iterable<Vaga>vagas = vr.findAll();
        mv.addObject("vagas", vagas);
        return mv;
    }

    // Método para mostrar os detalhes de uma vaga específica
    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesVaga(@PathVariable("codigo") Long codigo){
        Vaga vaga = vr.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vaga", vaga);

        Iterable<Candidato> candidatos = cr.findByVaga(vaga);
        mv.addObject("candidatos", candidatos);

        return mv;
    }

    // Método para deletar uma vaga específica
    @RequestMapping("/deletarVaga")
    public String deletarVaga(Long codigo){
        Vaga vaga = vr.findByCodigo(codigo);
        vr.delete(vaga);
        return "redirect:/vagas";
    }

    // Método para processar o formulário de cadastro de candidato para uma vaga específica
    public String detalhesVagaPost(@PathVariable("codigo") Long codigo, @Valid Candidato candidato,
                                   BindingResult result, RedirectAttributes attributes ){

        if(result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{codigo}";
        }
        if(cr.findByRg(candidato.getRg()) != null){
            attributes.addFlashAttribute("mensagem erro", "RG duplicado");
            return "redirect:/{codigo}";
        }
        Vaga vaga = vr.findByCodigo(codigo);
        candidato.setVaga(vaga);
        cr.save(candidato);
        attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");
        return "redirect:/{codigo}";

    }

    // GET que deleta o candidato pelo RG
    @RequestMapping("/deletarCandidato")
    public String deletarCandidato(String rg) {
        Candidato candidato = cr.findByRg(rg);
        Vaga vaga = candidato.getVaga();
        String codigo = "" + vaga.getCodigo();

        cr.delete(candidato);

        return "redirect:/vaga/" + codigo;

    }

    // Métodos que atualizam vaga
    // GET que chama o formulário de edição da vaga
    @RequestMapping(value="/editar-vaga", method = RequestMethod.GET)
    public ModelAndView editarVaga(Long codigo) {
        Vaga vaga = vr.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        mv.addObject("vaga", vaga);
        return mv;
    }

    // POST do FORM que atualiza a vaga
    @RequestMapping(value = "/editar-vaga", method = RequestMethod.POST)
    public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
        vr.save(vaga);
        attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

        long codigoLong = vaga.getCodigo();
        String codigo = "" +codigoLong;
        return "redirect:/" + codigo;
    }
}
