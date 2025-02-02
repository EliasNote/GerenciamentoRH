package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.cadastro.CadastroFuncionarioController;
import com.esand.gerenciamentorh.controller.editar.EditarFuncionarioController;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

import static com.esand.gerenciamentorh.controller.Utils.loadFXML;

public class VisualizarFuncionarioController {

    @FXML
    private TableView<Funcionario> tabela;
    @FXML
    private TableColumn<Funcionario, Long> idColumn;
    @FXML
    private TableColumn<Funcionario, String> nomeColumn;
    @FXML
    private TableColumn<Funcionario, String> sobrenomeColumn;
    @FXML
    private TableColumn<Funcionario, String> cpfColumn;
    @FXML
    private TableColumn<Funcionario, Double> salarioColumn;
    @FXML
    private TableColumn<Funcionario, String> cargoColumn;
    @FXML
    private TableColumn<Funcionario, LocalDate> admissaoColumn;

    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private Dao<Funcionario> funcionarioDao = new Dao();

    public void initialize() {
        funcionarios.addAll(funcionarioDao.buscarTodos());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        sobrenomeColumn.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        admissaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataAdmissao"));

        tabela.setItems(funcionarios);
    }

    public void editar() {
        EditarFuncionarioController.funcionario = tabela.getSelectionModel().getSelectedItem();
        EditarFuncionarioController.visualizarFuncionarioController = this;
        loadFXML("editar.fxml", new Stage());
    }

    public void excluir() {
        Funcionario funcionario = tabela.getSelectionModel().getSelectedItem();
        funcionarioDao.deletarPorCpf(Funcionario.class, funcionario.getCpf());
        funcionarios.remove(funcionario);
    }

    public void cadastrar() {
        CadastroFuncionarioController.visualizarFuncionarioController = this;
        loadFXML("cadastroFuncionario.fxml", new Stage());
    }

    public void atualizarTabela() {
        funcionarios.clear();
        funcionarios.addAll(funcionarioDao.buscarTodos());
        tabela.refresh();
    }
}