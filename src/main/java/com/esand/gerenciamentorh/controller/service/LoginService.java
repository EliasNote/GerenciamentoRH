package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Login;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class LoginService {
    private final Dao<Login> loginDao = new Dao();

    public void salvar(Login login) {
        validarCpf(login.getCpf());
        login.setSenha(BCrypt.hashpw(login.getSenha(), BCrypt.gensalt()));
        loginDao.salvar(login);
    }

    public void deletar(String cpf) {
        loginDao.deletar(Login.class, cpf);
    }

    public List<Login> buscarTodos() {
        return loginDao.buscarTodos(Login.class);
    }

    public boolean autenticar(String cpf, String senha) {
        Login login = loginDao.buscarPorCpfEEmail(cpf, null);
        if (login == null) {
            return false;
        }
        return BCrypt.checkpw(senha, login.getSenha());
    }

    public boolean validarCpf(String cpf) {
        String cpfFormatado = cpf.replaceAll("[.-]", "");

        if (cpfFormatado.length() != 11) {
            return false;
        }

        for (char x : cpfFormatado.toCharArray()) {
            if (!Character.isDigit(x)) {
                return false;
            }
        }

        return true;
    }
}
