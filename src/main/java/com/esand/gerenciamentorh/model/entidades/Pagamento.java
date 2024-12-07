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

    @OneToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;
}
