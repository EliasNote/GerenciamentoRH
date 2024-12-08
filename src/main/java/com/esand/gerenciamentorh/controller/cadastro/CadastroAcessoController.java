package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

public class CadastroAcessoController {
    @FXML
    private TextField cpf;
    @FXML
    private PasswordField senha;
    @FXML
    private TextField cpfRemocao;

    private Dao<Login> loginDao = new Dao();

    public void cadastrar() {
        loginDao.salvarLogin(new Login(null, cpf.getText(), senha.getText()));
    }

    public void remover() {
        loginDao.deletarPorCpf(Login.class, cpfRemocao.getText());
    }
}
