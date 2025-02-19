package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.function.Consumer;

public class FuncionarioService {
    private final Dao<Funcionario> funcionarioDao = new Dao();

    public ObservableList<HBox> criarItensListaFuncionarios(Consumer<String> onSelecionar) {
        ObservableList<HBox> itens = FXCollections.observableArrayList();

        funcionarioDao.buscarTodos().forEach(funcionario -> {
            HBox hBox = new HBox();
            hBox.getStyleClass().add("hbox-custom");

            Label nomeLabel = new Label(funcionario.getNome() + " " + funcionario.getSobrenome());
            nomeLabel.setMaxWidth(132);

            Button btnSelecionar = new Button("Selecionar");
            btnSelecionar.getStyleClass().add("botao");
            btnSelecionar.setOnAction(e -> onSelecionar.accept(funcionario.getCpf()));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            hBox.getChildren().addAll(nomeLabel, spacer, btnSelecionar);
            itens.add(hBox);
        });

        return itens;
    }

    public void salvar(Funcionario funcionario) {
        funcionario.setCpf(funcionario.getCpf().replaceAll("[.-]", ""));
        funcionarioDao.salvar(funcionario);
    }

    public Funcionario buscarPorCpf(String cpf) {
        return funcionarioDao.buscarPorCpf(cpf);
    }

    public void atualizar(Funcionario funcionario) {
        funcionarioDao.atualizar(funcionario);
    }

    public void deletar(String cpf) {
        funcionarioDao.deletar(Funcionario.class, cpf);
    }

    public List<Funcionario> buscarTodos() {
        return funcionarioDao.buscarTodos(Funcionario.class);
    }
}
