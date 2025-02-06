package com.esand.gerenciamentorh.controller;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import static com.esand.gerenciamentorh.controller.util.Utils.loadFXML;
import static com.esand.gerenciamentorh.controller.util.Utils.showErrorMessage;
import static com.esand.gerenciamentorh.controller.EnumView.*;

public class LoginController {

    @FXML private TextField Login;
    @FXML private PasswordField Senha;

    private Dao<Login> loginDao = new Dao<>();

    public void logar() {
        String loginText = Login.getText();

        if (autenticar(loginText, Senha.getText())) {
            Stage stage = (Stage) Login.getScene().getWindow();
            loadFXML(PRINCIPAL.getPath(), stage);
        } else {
            showErrorMessage("Login ou Senha incorreto");
        }
    }

    public boolean autenticar(String cpf, String senha) {
        Login login = loginDao.buscarPorLogin(cpf, null);
        if (login == null) {
            return false;
        }
        return BCrypt.checkpw(senha, login.getSenha());
    }
}
