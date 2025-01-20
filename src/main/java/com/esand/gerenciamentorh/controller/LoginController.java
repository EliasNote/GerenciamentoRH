package com.esand.gerenciamentorh.controller;

import com.esand.gerenciamentorh.controller.autenticacao.strategy.LoginCpfStrategy;
import com.esand.gerenciamentorh.controller.autenticacao.strategy.LoginEmailStrategy;
import com.esand.gerenciamentorh.controller.autenticacao.LoginStrategy;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

import static com.esand.gerenciamentorh.controller.Utils.loadFXML;
import static com.esand.gerenciamentorh.controller.Utils.showErrorMessage;

public class LoginController {

    @FXML
    private TextField Login;

    @FXML
    private PasswordField Senha;

    private final Map<String, LoginStrategy> mapStrategy = Map.of(
            "cpf", new LoginCpfStrategy(),
            "email", new LoginEmailStrategy()
    );

    public void logar() {
        String loginText = Login.getText();
        LoginStrategy strategy = loginText.contains("@") ? mapStrategy.get("email") : mapStrategy.get("cpf");

        if (strategy.autenticar(loginText, Senha.getText())) {
            Stage stage = (Stage) Login.getScene().getWindow();
            loadFXML("principal.fxml", stage);
        } else {
            showErrorMessage("Login ou Senha incorreto");
        }
    }
}
