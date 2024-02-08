package com.AplicationRH.appRH.controllers;


import com.AplicationRH.appRH.models.Candidato;
import com.AplicationRH.appRH.models.Vaga;
import com.AplicationRH.appRH.repositories.CandidatoRepository;
import com.AplicationRH.appRH.repositories.VagaRepository;
import com.AplicationRH.appRH.services.VagaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VagaController {

    private final VagaService vagaService;

    @Autowired
    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @GetMapping("/cadastrarVaga")
    public String formVaga() {
        return "vaga/formVaga";
    }

    @PostMapping("/cadastrarVaga")
    public String cadastrarVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
        return vagaService.cadastrarVaga(vaga, result, attributes);
    }

    @GetMapping("/vagas")
    public ModelAndView listaVagas() {
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        mv.addObject("vagas", vagaService.listarVagas());
        return mv;
    }

    @GetMapping("/{codigo}")
    public ModelAndView detalhesVaga(@PathVariable("codigo") long codigo) {
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vaga", vagaService.detalhesVaga(codigo));
        mv.addObject("candidatos", vagaService.detalhesVaga(codigo).getCandidato());
        return mv;
    }

    @PostMapping("/{codigo}")
    public String adicionarCandidato(@PathVariable("codigo") long codigo, @Valid Candidato candidato,
                                     BindingResult result, RedirectAttributes attributes) {
        return vagaService.adicionarCandidato(codigo, candidato, result, attributes);
    }

    @GetMapping("/deletarVaga")
    public String deletarVaga(long codigo) {
        return vagaService.deletarVaga(codigo);
    }

    @GetMapping("/deletarCandidato")
    public String deletarCandidato(String rg) {
        return vagaService.deletarCandidato(rg);
    }

    @GetMapping("/editar-vaga")
    public ModelAndView editarVaga(long codigo) {
        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        mv.addObject("vaga", vagaService.editarVaga(codigo));
        return mv;
    }

    @PostMapping("/editar-vaga")
    public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
        return vagaService.updateVaga(vaga, result, attributes);
    }
}

