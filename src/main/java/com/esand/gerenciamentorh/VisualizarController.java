package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void initialize() {
        inicializarDados();
    }

    public void inicializarDados() {
        EntityManager em = DataBase.getEntityManager();

        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
            funcionarios.addAll(query.getResultList());

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            sobrenomeColumn.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
            cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));

            tableView.setItems(funcionarios);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Erro ao carregar dados", "Ocorreu um erro ao carregar os funcionários: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}