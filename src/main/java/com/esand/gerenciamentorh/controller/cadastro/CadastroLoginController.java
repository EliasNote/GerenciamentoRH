package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.controller.service.LoginService;
import com.esand.gerenciamentorh.model.entidades.Login;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

public class CadastroLoginController {
    @FXML private Label labelCadastro, labelRemocao;
    @FXML private TextField cpf, cpfRemocao;
    @FXML private PasswordField senha;

    private final LoginService loginService = new LoginService();

    public void cadastrar() {
        resetarLabels();
        boolean sucesso = loginService.salvar(new Login(null, cpf.getText(), senha.getText()));
        labelCadastro.setText(sucesso ? "Cadastro realizado com sucesso" : "Erro ao realizar cadastro");
        labelCadastro.setStyle(sucesso ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    public void remover() {
        resetarLabels();
        boolean sucesso = loginService.deletar(cpfRemocao.getText());
        labelRemocao.setText(sucesso ? "Deletado com sucesso" : "Cadastro não encontrado");
        labelRemocao.setStyle(sucesso ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    public void resetarLabels() {
        labelCadastro.setText("");
        labelCadastro.setStyle("-fx-text-fill: black;");
        labelRemocao.setText("");
        labelRemocao.setStyle("-fx-text-fill: black;");
    }
}
