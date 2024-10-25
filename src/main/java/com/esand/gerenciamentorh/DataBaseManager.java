package com.esand.gerenciamentorh;


import com.esand.gerenciamentorh.entidades.Funcionario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.*;

public class DataBaseManager {

    private static String URL = "jdbc:h2:./data/";
    private static String DB = "rh";
    private static String USER = "root";
    private static String PASSWORD = "root";

    public static Connection connect() {
        Connection databaseLink;

        try {
            Class.forName("org.h2.Driver");
            databaseLink = DriverManager.getConnection(URL + DB, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return databaseLink;
    }

    public void inicializar() {
        createTable();
        if (!adminExiste()) {
            inserirLoginAdmin();
        }
    }

    public void createTable() {
        String createFuncionario =
                "CREATE TABLE IF NOT EXISTS funcionario (\n" +
                        "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "nome VARCHAR(255) NOT NULL,\n" +
                        "sobrenome VARCHAR(255) NOT NULL,\n" +
                        "cpf VARCHAR(14) NOT NULL UNIQUE,\n" +
                        "departamento VARCHAR(255),\n" +
                        "salario DECIMAL(10, 2)\n" +
                        ");";

        String createBeneficio =
                "CREATE TABLE IF NOT EXISTS beneficio (\n" +
                        "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "tipo VARCHAR(255) NOT NULL,\n" +
                        "descricao VARCHAR(255),\n" +
                        "valor DECIMAL(10, 2) NOT NULL\n" +
                        ");";

        String createFolhaPagamento =
                "CREATE TABLE IF NOT EXISTS folha_pagamento (\n" +
                        "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "funcionario_id BIGINT NOT NULL,\n" +
                        "salario_liquido DECIMAL(10, 2),\n" +
                        "FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)\n" +
                        ");";

        String createAvaliacao =
                "CREATE TABLE IF NOT EXISTS avaliacao (\n" +
                        "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "folha_pagamento_id BIGINT NOT NULL,\n" +
                        "pontuacao DECIMAL(3, 2),\n" +
                        "comentarios VARCHAR(255),\n" +
                        "FOREIGN KEY (folha_pagamento_id) REFERENCES folha_pagamento(id)\n" +
                        ");";

        String createLogin =
                "CREATE TABLE IF NOT EXISTS login (\n" +
                        "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "cpf VARCHAR(255) NOT NULL UNIQUE,\n" +
                        "senha VARCHAR(255) NOT NULL\n" +
                        ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createFuncionario);
            stmt.executeUpdate(createBeneficio);
            stmt.executeUpdate(createFolhaPagamento);
            stmt.executeUpdate(createAvaliacao);
            stmt.executeUpdate(createLogin);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inserirLoginAdmin() {
        String inserirFuncionarioQuery = "INSERT INTO login (cpf, senha) VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(inserirFuncionarioQuery)) {
            pstmt.setString(1, "admin");
            pstmt.setString(2, "admin");
            pstmt.executeUpdate();
            System.out.println("DADOS ADMIN INSERIDOS");
        } catch (SQLException e) {
            showErrorMessage("Erro inesperado" + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean adminExiste() {
        String verificarAdminQuery = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM login WHERE cpf = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(verificarAdminQuery)) {

            pstmt.setString(1, "admin");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            showErrorMessage("Erro ao verificar admin: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Funcionario buscarFuncionario(String cpf) {
        String selectFuncionariosQuery = "SELECT * FROM funcionario WHERE cpf = ?";



    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
