package com.AplicationRH.appRH.repositories;

import com.AplicationRH.appRH.models.Candidato;
import com.AplicationRH.appRH.models.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    Iterable<Candidato> findByVaga(Vaga vaga);

    Candidato findByRg(String rg);

    List<Candidato>findByNome(String nomeCandidato);
}
