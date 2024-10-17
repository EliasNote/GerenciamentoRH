package com.esand.gerenciamentorh;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController {

    @FXML
    private Pane contentPane;

    @FXML
    public void initialize() {
        assert contentPane != null : "fx:id=\"contentPane\" was not injected: check your FXML file 'hello.fxml'.";
        createTable();
    }

    @FXML
    public void showCadastrar() {
        loadFXML("cadastro.fxml");
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
}