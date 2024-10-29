package com.esand.gerenciamentorh.entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class FolhaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    private Double salarioLiquido;
    private Double horasExtras;
    private Double horasFaltas;

    @Embedded
    private Avaliacao avaliacao;
}
