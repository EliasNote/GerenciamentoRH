package com.esand.gerenciamentorh.controller.autenticacao;

public interface LoginStrategy {
    boolean autenticar(String login, String senha);
    boolean validar(String login);
}
