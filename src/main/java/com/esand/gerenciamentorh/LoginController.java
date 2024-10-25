package com.esand.gerenciamentorh;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField CPF;

    @FXML
    private TextField Senha;

    @FXML
    public void logar() {
        if (validarLogin(CPF.getText(), Senha.getText())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                Stage stage = (Stage) CPF.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Bem-vindo");

                stage.show();
            } catch (IOException e) {
                showErrorMessage("Erro ao carregar a tela: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showErrorMessage("CPF ou senha inválidos.");
        }
    }

    private boolean validarLogin(String cpf, String senha) {
        String selecionarLoginQuery = "SELECT COUNT(*) FROM login WHERE cpf = ? AND senha = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(selecionarLoginQuery)) {
            pstmt.setString(1, cpf);
            pstmt.setString(2, senha);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            showErrorMessage("Erro inesperado: " + e.getMessage());
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
