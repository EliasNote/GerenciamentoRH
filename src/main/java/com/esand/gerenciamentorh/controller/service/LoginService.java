package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class LoginService {

    private final Dao<Login> loginDao = new Dao();

    public void salvar(Login login) {
        login.setSenha(BCrypt.hashpw(login.getSenha(), BCrypt.gensalt()));
        loginDao.salvar(login);
    }

    public void deletar(String cpf) {
        loginDao.deletar(Login.class, cpf);
    }

    public List<Login> buscarTodos() {
        return loginDao.buscarTodos(Login.class);
    }
}
