package com.esand.gerenciamentorh;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.io.IOException;
import java.sql.*;

public class HelloController {

    @FXML
    private Pane contentPane;

    @FXML
    public void initialize() {
        assert contentPane != null : "fx:id=\"contentPane\" was not injected: check your FXML file 'hello.fxml'.";
        createTable();
        if (adminExists()) {
            inserirLoginAdmin();
        }
    }

    @FXML
    public void showCadastrar() {
        loadFXML("cadastro.fxml");
    }

    @FXML
    public void showVisualizar() {
        loadFXML("visualizar.fxml");
    }

    private void loadFXML(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Pane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
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
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    folha_pagamento_id BIGINT NOT NULL,\n" +
                        "    pontuacao DECIMAL(3, 2),\n" +
                        "    comentarios VARCHAR(255),\n" +
                        "    FOREIGN KEY (folha_pagamento_id) REFERENCES folha_pagamento(id)\n" +
                        ");";

        String createLogin =
                "CREATE TABLE IF NOT EXISTS login (\n" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    cpf VARCHAR(255),\n" +
                        "    senha VARCHAR(255),\n" +
                        ");";

        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(createFuncionario);
            stmt.executeUpdate(createBeneficio);
            stmt.executeUpdate(createFolhaPagamento);
            stmt.executeUpdate(createAvaliacao);
            stmt.executeUpdate(createLogin);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirLoginAdmin() {
        String inserirFuncionarioQuery = "INSERT INTO funcionario (cpf, senha) VALUES (?, ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement pstmt = conn.prepareStatement(inserirFuncionarioQuery)) {
            pstmt.setString(1, "admin");
            pstmt.setString(2, "admin");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showErrorMessage("Erro inesperado" + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean adminExists() {
        String verificarAdminQuery = "SELECT COUNT(*) FROM login WHERE cpf = ?";

        try (Connection conn = DBConnect.connect()) {
            PreparedStatement pstmt = conn.prepareStatement(verificarAdminQuery);
                pstmt.setString(1, "admin");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }catch (SQLException e) {
            showErrorMessage("Erro ao verificar admin: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}