package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.database.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.*;

public class HelloController {

    @FXML
    private Pane contentPane;

    @FXML
    public void initialize() {

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
}