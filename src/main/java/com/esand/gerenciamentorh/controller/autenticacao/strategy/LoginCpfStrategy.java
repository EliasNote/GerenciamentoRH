package com.esand.gerenciamentorh.controller.autenticacao.strategy;

import com.esand.gerenciamentorh.controller.autenticacao.LoginStrategy;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import org.mindrot.jbcrypt.BCrypt;

public class LoginCpfStrategy implements LoginStrategy {

    private Dao<Login> loginDao = new Dao<>();

    @Override
    public boolean autenticar(String cpf, String senha) {
        if (!validar(cpf)) {
            return false;
        }

        Login login = loginDao.buscarPorLogin(cpf, null);
        if (login == null) {
            return false;
        }
        return BCrypt.checkpw(senha, login.getSenha());
    }

    @Override
    public boolean validar(String cpf) {
        String cpfFormatado = cpf.replaceAll("[.-]", "");

        if (cpfFormatado.length() != 11) {
            return false;
        }

        for (char x : cpf.toCharArray()) {
            if (!Character.isDigit(x)) {
                return false;
            }
        }

        return true;
    }
}
