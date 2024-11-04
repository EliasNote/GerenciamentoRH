package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.dao.LoginDao;
import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.esand.gerenciamentorh.Utils.loadFXML;
import static com.esand.gerenciamentorh.Utils.showErrorMessage;

public class LoginController {

    private LoginDao loginDao = new LoginDao();

    @FXML
    private TextField CPF;

    @FXML
    private TextField Senha;

    @FXML
    public void logar() {
        if (loginDao.autenticar(CPF.getText(), Senha.getText())) {
            loadFXML("principal.fxml", CPF);
        } else {
            showErrorMessage("CPF ou Senha incorreto");
        }
    }
}
