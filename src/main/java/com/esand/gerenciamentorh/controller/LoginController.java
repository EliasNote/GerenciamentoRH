package com.esand.gerenciamentorh.controller;

import com.esand.gerenciamentorh.controller.service.LoginService;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import static com.esand.gerenciamentorh.controller.util.Utils.loadFXML;

import static com.esand.gerenciamentorh.controller.util.EnumView.*;

public class LoginController {

    @FXML private Label errorLabel;
    @FXML private TextField Login;
    @FXML private PasswordField Senha;

    private final LoginService loginService = new LoginService();

    public void logar() {
        String loginText = Login.getText();

        if (loginService.autenticar(loginText, Senha.getText())) {
            Stage stage = (Stage) Login.getScene().getWindow();
            loadFXML(PRINCIPAL.getPath(), stage);
        } else {
            errorLabel.setText("Login ou Senha incorreto");
        }
    }
}
