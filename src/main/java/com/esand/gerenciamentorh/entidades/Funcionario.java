package com.esand.gerenciamentorh.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Departamento departamento;
    private String cargo;
    private Double salario;
    @OneToMany(mappedBy = "funcionario")
    private List<Beneficio> beneficios = new ArrayList<>();
    private LocalDate dataAdmissao = LocalDate.now();

    public enum Departamento {
        RH,
        PRODUCAO,
        GERENCIA
    }
}
