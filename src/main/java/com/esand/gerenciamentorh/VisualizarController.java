package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
//        inicializarDados();
    }

//    public void inicializarDados() {
//        String selectFuncionariosQuery = "SELECT * FROM funcionario";
//
//        try (Connection conn = DBConnect.connect();
//             Statement stmt = conn.createStatement();
//             ResultSet query = stmt.executeQuery(selectFuncionariosQuery)) {
//            while (query.next()) {
//                Long id = query.getLong("id");
//                String nome = query.getString("nome");
//                String sobrenome = query.getString("sobrenome");
//                String cpf = query.getString("cpf");
//                double salario = query.getDouble("salario");
//
//                funcionarios.add(new Funcionario(id, nome, sobrenome, cpf, salario));
//            }
//
//            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
//            sobrenomeColumn.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
//            cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
//            salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));
//
//            tableView.setItems(funcionarios);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}

