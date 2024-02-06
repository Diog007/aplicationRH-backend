package com.AplicationRH.appRH.controllers;

import com.AplicationRH.appRH.models.Vaga;
import com.AplicationRH.appRH.repositories.CandidatoRepository;
import com.AplicationRH.appRH.repositories.VagaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VagaController {

    private VagaRepository vagaRepository;
    private CandidatoRepository candidatoRepository;


}
