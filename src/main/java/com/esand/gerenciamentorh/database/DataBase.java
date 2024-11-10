package com.esand.gerenciamentorh.database;

import com.esand.gerenciamentorh.dao.LoginDao;
import com.esand.gerenciamentorh.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataBase {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("gerenciamentoRh");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private LoginDao loginDao = new LoginDao();

    public DataBase() {
        inicializarAdmin();
    }

    private void inicializarAdmin() {
        if (!loginDao.existePorCpf("admin")) {
            Login login = new Login();
            login.setCpf("admin");
            login.setSenha("admin");

            loginDao.salvar(login);
        }
    }

    private void inicializarBeneficios() {
    }
}