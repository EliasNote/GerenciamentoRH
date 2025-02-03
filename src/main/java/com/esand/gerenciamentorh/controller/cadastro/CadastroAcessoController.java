package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.controller.service.LoginService;
import com.esand.gerenciamentorh.model.entidades.Login;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

public class CadastroAcessoController {
    @FXML private TextField cpf, cpfRemocao;
    @FXML private PasswordField senha;

    private final LoginService loginService = new LoginService();

    public void cadastrar() {
        loginService.salvar(new Login(null, cpf.getText(), senha.getText()));
    }

    public void remover() {
        loginService.deletar(cpfRemocao.getText());
    }
}
