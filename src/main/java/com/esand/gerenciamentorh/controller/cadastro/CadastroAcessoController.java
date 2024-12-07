package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.model.dao.LoginDao;
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

    private LoginDao loginDao = new LoginDao();

    public void cadastrar() {
        loginDao.salvar(new Login(null, cpf.getText(), senha.getText()));
    }

    public void remover() {
        loginDao.deletar(cpfRemocao.getText());
    }
}
