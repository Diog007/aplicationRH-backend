package com.AplicationRH.appRH.services;

import com.AplicationRH.appRH.models.Candidato;
import com.AplicationRH.appRH.models.Vaga;
import com.AplicationRH.appRH.repositories.CandidatoRepository;
import com.AplicationRH.appRH.repositories.VagaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class VagaService {

    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;

    @Autowired
    public VagaService(VagaRepository vagaRepository, CandidatoRepository candidatoRepository) {
        this.vagaRepository = vagaRepository;
        this.candidatoRepository = candidatoRepository;
    }

    @Transactional
    public String cadastrarVaga(@Valid  Vaga vaga, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarVaga";
        }
        vagaRepository.save(vaga);
        attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
        return "redirect:/cadastrarVaga";
    }

    public Iterable<Vaga> listarVagas() {
        return vagaRepository.findAll();
    }

    public Vaga detalhesVaga(long codigo) {
        return vagaRepository.findByCodigo(codigo);
    }

    @Transactional
    public String adicionarCandidato(long codigo, @Valid Candidato candidato,
                                     BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{codigo}";
        }

        if (candidatoRepository.findByRg(candidato.getRg()) != null) {
            attributes.addFlashAttribute("mensagem_erro", "RG duplicado");
            return "redirect:/{codigo}";
        }

        Vaga vaga = vagaRepository.findByCodigo(codigo);
        candidato.setVaga(vaga);
        candidatoRepository.save(candidato);
        attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");
        return "redirect:/{codigo}";
    }

    @Transactional
    public String deletarVaga(long codigo) {
        Vaga vaga = vagaRepository.findByCodigo(codigo);
        vagaRepository.delete(vaga);
        return "redirect:/vagas";
    }

    @Transactional
    public String deletarCandidato(String rg) {
        Candidato candidato = candidatoRepository.findByRg(rg);
        candidatoRepository.delete(candidato);
        return "redirect:/vagas";
    }

    public Vaga editarVaga(long codigo) {
        return vagaRepository.findByCodigo(codigo);
    }

    @Transactional
    public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "vaga/update-vaga";
        }
        vagaRepository.save(vaga);
        attributes.addFlashAttribute("mensagem", "Vaga alterada com sucesso!");
        return "redirect:/vagas";
    }
}

