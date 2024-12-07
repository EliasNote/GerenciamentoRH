package com.esand.gerenciamentorh.model.entidades;

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
    @Column(unique = true)
    private String cpf;
    private String cargo;
    private Double salario = 0.00;
    @ManyToMany
    @JoinTable(
            name = "funcionario_beneficio",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficio_id")
    )
    private List<Beneficio> beneficios = new ArrayList<>();
    private LocalDate dataAdmissao = LocalDate.now();
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();
}
