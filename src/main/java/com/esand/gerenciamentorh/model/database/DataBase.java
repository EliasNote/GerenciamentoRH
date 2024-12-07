package com.esand.gerenciamentorh.model.database;

import com.esand.gerenciamentorh.model.dao.*;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DataBase {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("gerenciamentoRh");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private LoginDao loginDao = new LoginDao();
    private BeneficioDao beneficioDao = new BeneficioDao();

    public DataBase() {
        inicializarAdmin();
        inicializarBeneficios();
    }

    private void inicializarAdmin() {
        if (loginDao.buscarTodos().isEmpty()) {
            Login login = new Login();
            login.setCpf("admin");
            login.setSenha("admin");

            loginDao.salvar(login);
        }
    }

    private void inicializarBeneficios() {
        if (beneficioDao.buscarTodos().isEmpty()) {
            List<Beneficio> beneficios = List.of(
                    new Beneficio(null, "Vale Transporte", "Auxílio para transporte", 150.0, null),
                    new Beneficio(null, "Vale Refeição", "Auxílio para alimentação", 200.0, null),
                    new Beneficio(null, "Assistência Médica", "Plano de saúde", 500.0, null),
                    new Beneficio(null, "Seguro de Vida", "Seguro de vida em grupo", 100.0, null)
            );

            for (Beneficio beneficio : beneficios) {
                beneficioDao.salvar(beneficio);
            }
        }
    }
}
