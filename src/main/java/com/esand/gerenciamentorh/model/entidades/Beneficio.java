package com.esand.gerenciamentorh.model.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Beneficio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String tipo;
    private String descricao;
    private Double valor;

    @ManyToMany(mappedBy = "beneficios")
    private List<Funcionario> funcionarios = new ArrayList<>();;
}
