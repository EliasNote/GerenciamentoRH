package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.dao.FuncionarioDao;
import com.esand.gerenciamentorh.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.esand.gerenciamentorh.Utils.loadFXML;

public class VisualizarController {

    @FXML
    private TableView<Funcionario> tableView;
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

    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private FuncionarioDao funcionarioDao = new FuncionarioDao();

    public void initialize() {
        inicializarDados();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void inicializarDados() {
        funcionarios.addAll(funcionarioDao.buscarTodos());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        sobrenomeColumn.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));

        tableView.setItems(funcionarios);
    }

    public void editar() {
        EditarController.funcionario = tableView.getSelectionModel().getSelectedItem();
        EditarController.visualizarController = this;
        loadFXML("editar.fxml", new Stage());
        tableView.refresh();
    }

    public void excluir() {
        Funcionario funcionario = tableView.getSelectionModel().getSelectedItem();
        funcionarioDao.excluirPorCpf(funcionario.getCpf());
        funcionarios.remove(funcionario);
    }

    public void atualizarTabela() {
        funcionarios.clear();
        funcionarios.addAll(funcionarioDao.buscarTodos());
        tableView.refresh();
    }
}