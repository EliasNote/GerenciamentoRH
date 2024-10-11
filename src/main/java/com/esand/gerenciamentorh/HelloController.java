package com.esand.gerenciamentorh;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

public class HelloController {
    @FXML
    private TextField nomeField;
    @FXML
    private TextField sobrenomeField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField salarioField;

    @FXML
    public void initialize() {
        createTable();
    }

    @FXML
    protected void onSalvarButtonClick() {
        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String cpf = cpfField.getText();
        double salario = Double.parseDouble(salarioField.getText());

        insertFuncionario(nome, sobrenome, cpf, salario);
        exibirFuncionarios();
    }

    public void createTable() {
        String createFuncionarioTable =
                "CREATE TABLE IF NOT EXISTS funcionario (\n" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    nome VARCHAR(255) NOT NULL,\n" +
                        "    sobrenome VARCHAR(255) NOT NULL,\n" +
                        "    cpf VARCHAR(14) NOT NULL UNIQUE,\n" +
                        "    salario DECIMAL(10, 2)\n" +
                        ");";

        String createBeneficioTable =
                "CREATE TABLE IF NOT EXISTS beneficio (\n" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    nome VARCHAR(255) NOT NULL,\n" +
                        "    valor DECIMAL(10, 2) NOT NULL\n" +
                        ");";
        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(createFuncionarioTable);
            stmt.executeUpdate(createBeneficioTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFuncionario(String nome, String sobrenome, String cpf, double salario) {
        String insertFuncionarioQuery =
                "INSERT INTO funcionario (nome, sobrenome, cpf, salario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertFuncionarioQuery)
        ) {
            pstmt.setString(1, nome);
            pstmt.setString(2, sobrenome);
            pstmt.setString(3, cpf);
            pstmt.setDouble(4, salario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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