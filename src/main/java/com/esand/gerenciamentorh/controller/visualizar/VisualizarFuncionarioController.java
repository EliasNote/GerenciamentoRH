package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.cadastro.CadastroFuncionarioController;
import com.esand.gerenciamentorh.controller.editar.EditarFuncionarioController;
import com.esand.gerenciamentorh.controller.service.FuncionarioService;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

import static com.esand.gerenciamentorh.controller.util.Utils.loadFXML;
import static com.esand.gerenciamentorh.controller.EnumView.*;

public class VisualizarFuncionarioController {

    @FXML private TableView<Funcionario> tabela;
    @FXML private TableColumn<Funcionario, Long> idColumn;
    @FXML private TableColumn<Funcionario, String> nomeColumn, sobrenomeColumn, cpfColumn, cargoColumn;
    @FXML private TableColumn<Funcionario, Double> salarioColumn;
    @FXML private TableColumn<Funcionario, LocalDate> admissaoColumn;

    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private FuncionarioService funcionarioService = new FuncionarioService();

    public void initialize() {
        funcionarios.addAll(funcionarioService.buscarTodos());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        sobrenomeColumn.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        admissaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataAdmissao"));

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabela.setItems(funcionarios);
    }

    public void editar() {
        EditarFuncionarioController.funcionario = tabela.getSelectionModel().getSelectedItem();
        EditarFuncionarioController.visualizarFuncionarioController = this;
        loadFXML(FUNCIONARIO_EDITAR.getPath(), new Stage());
    }

    public void excluir() {
        Funcionario funcionario = tabela.getSelectionModel().getSelectedItem();
        funcionarioService.deletar(funcionario.getCpf());
        funcionarios.remove(funcionario);
    }

    public void cadastrar() {
        CadastroFuncionarioController.visualizarFuncionarioController = this;
        loadFXML(FUNCIONARIO_CADASTRO.getPath(), new Stage());
    }

    public void atualizarTabela() {
        funcionarios.clear();
        funcionarios.addAll(funcionarioService.buscarTodos());
        tabela.refresh();
    }
}