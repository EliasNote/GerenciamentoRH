package com.esand.gerenciamentorh.controller;

import com.esand.gerenciamentorh.model.dao.LoginDao;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.esand.gerenciamentorh.controller.Utils.loadFXML;
import static com.esand.gerenciamentorh.controller.Utils.showErrorMessage;

public class LoginController {

    private LoginDao loginDao = new LoginDao();

    @FXML
    private TextField CPF;

    @FXML
    private PasswordField Senha;
    public void logar() {
        if (loginDao.autenticar(CPF.getText(), Senha.getText())) {
            Stage stage = (Stage) CPF.getScene().getWindow();
            loadFXML("principal.fxml", stage);
        } else {
            showErrorMessage("CPF ou Senha incorreto");
        }
    }
}
