package com.esand.gerenciamentorh.model.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.YearMonth;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private YearMonth competencia;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    private Double salarioLiquido;
    private Double horasExtras;
    private Double horasFaltas;
    private Double inss;
    private Double irpf;
    private Double fgts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;

    public String getNome() {
        return funcionario.getNome();
    }

    public String getCpf() {
        return funcionario.getCpf();
    }

    public Double getSalarioBruto() {
        return funcionario.getSalario();
    }
}
