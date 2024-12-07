package com.esand.gerenciamentorh.model.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataBase {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("gerenciamentoRh");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
