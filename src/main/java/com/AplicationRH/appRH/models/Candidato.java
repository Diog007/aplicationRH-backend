package com.AplicationRH.appRH.models;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String rg;
    @NotNull
    private String nomeCandidato;
    @NotNull
    private String email;
    @ManyToOne
    private Vaga vaga;
}
