package com.AplicationRH.appRH.repositories;

import com.AplicationRH.appRH.models.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Vaga findByCodigo(Long codigo);
    List<Vaga> findByNome(String nome);
}
