package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

import static com.esand.gerenciamentorh.controller.util.Utils.validarCpf;

public class LoginService {
    private final Dao<Login> loginDao = new Dao();

    public boolean salvar(Login login) {
        if (!validarCpf(login.getCpf())) {
            return false;
        }
        login.setSenha(BCrypt.hashpw(login.getSenha(), BCrypt.gensalt()));
        login.setCpf(login.getCpf().replaceAll("[.-]", ""));
        loginDao.salvar(login);

        return true;
    }

    public boolean deletar(String cpf) {
        return loginDao.deletar(Login.class, cpf);
    }

    public List<Login> buscarTodos() {
        return loginDao.buscarTodos(Login.class);
    }

    public boolean autenticar(String cpf, String senha) {
        Login login = loginDao.buscarLoginPorCpf(cpf);
        if (login == null) {
            return false;
        }
        return BCrypt.checkpw(senha, login.getSenha());
    }
}
