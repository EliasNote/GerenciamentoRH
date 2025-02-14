package com.esand.gerenciamentorh.model.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@Entity
@Getter
@Setter
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

    @OneToOne
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
