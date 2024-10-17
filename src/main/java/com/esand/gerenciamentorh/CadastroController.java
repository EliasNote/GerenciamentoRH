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
    protected void onSalvarButtonClick() {
        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String cpf = cpfField.getText();
        double salario = Double.parseDouble(salarioField.getText());

        insertFuncionario(nome, sobrenome, cpf, salario);
        exibirFuncionarios();
    }

    public void insertFuncionario(String nome, String sobrenome, String cpf, double salario) {
        String insertFuncionarioQuery = "INSERT INTO funcionario (nome, sobrenome, cpf, salario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnect.connect();
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

    public void exibirFuncionarios() {
        String selectFuncionariosQuery = "SELECT * FROM funcionario";

        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectFuncionariosQuery)
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                String cpf = rs.getString("cpf");
                double salario = rs.getDouble("salario");

                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Sobrenome: " + sobrenome);
                System.out.println("CPF: " + cpf);
                System.out.println("Salário: " + salario);
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}