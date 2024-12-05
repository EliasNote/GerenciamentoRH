package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.dao.FuncionarioDao;
import com.esand.gerenciamentorh.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.esand.gerenciamentorh.Utils.loadFXML;

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

    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private FuncionarioDao funcionarioDao = new FuncionarioDao();

    public void initialize() {
        funcionarios.addAll(funcionarioDao.buscarTodos());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        sobrenomeColumn.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));

        tabela.setItems(funcionarios);
    }

    public void editar() {
        EditarFuncionarioController.funcionario = tabela.getSelectionModel().getSelectedItem();
        EditarFuncionarioController.visualizarFuncionarioController = this;
        loadFXML("editar.fxml", new Stage());
    }

    public void excluir() {
        Funcionario funcionario = tabela.getSelectionModel().getSelectedItem();
        funcionarioDao.deletar(funcionario.getCpf());
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