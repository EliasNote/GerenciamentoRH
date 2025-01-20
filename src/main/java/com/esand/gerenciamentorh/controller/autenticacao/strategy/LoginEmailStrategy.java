package com.esand.gerenciamentorh.controller.autenticacao.strategy;

import com.esand.gerenciamentorh.controller.autenticacao.LoginStrategy;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import org.mindrot.jbcrypt.BCrypt;

public class LoginEmailStrategy implements LoginStrategy {

    private Dao<Login> loginDao = new Dao<>();

    @Override
    public boolean autenticar(String email, String senha) {
        if (!validar(email)) {
            return false;
        }

        Login login = loginDao.buscarPorLogin(null, email);
        if (login == null) {
            return false;
        }
        return BCrypt.checkpw(senha, login.getSenha());
    }

    @Override
    public boolean validar(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
