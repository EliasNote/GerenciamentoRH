package com.esand.gerenciamentorh;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.*;

public class CadastroController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField sobrenomeField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField salarioField;

    @FXML
    protected void salvar() {
        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String cpf = cpfField.getText();
        double salario = Double.parseDouble(salarioField.getText());

        insertFuncionario(nome, sobrenome, cpf, salario);
    }

    public void insertFuncionario(String nome, String sobrenome, String cpf, double salario) {
        String insertFuncionarioQuery = "INSERT INTO funcionario (nome, sobrenome, cpf, salario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertFuncionarioQuery)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, sobrenome);
            pstmt.setString(3, cpf);
            pstmt.setDouble(4, salario);
            pstmt.executeUpdate();
        } catch (JdbcSQLIntegrityConstraintViolationException e) {
            showErrorMessage("Erro de Integridade", "O CPF " + cpf + " já está cadastrado.");
        } catch (SQLException e) {
            showErrorMessage("Erro SQL", "Ocorreu um erro ao inserir o funcionário: " + e.getMessage());
            e.printStackTrace();
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