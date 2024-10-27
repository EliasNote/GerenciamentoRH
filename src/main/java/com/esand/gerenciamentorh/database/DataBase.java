package com.esand.gerenciamentorh.database;

import com.esand.gerenciamentorh.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataBase {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("gerenciamentoRh");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void inicializarAdmin() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            if (!adminExists(em)) {
                inserirLoginAdmin(em);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    private static boolean adminExists(EntityManager em) {
        String query = "SELECT COUNT(l) FROM Login l WHERE l.cpf = :cpf";
        Long count;
        try {
            count = em.createQuery(query, Long.class)
                    .setParameter("cpf", "admin")
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return count > 0;
    }

    private static void inserirLoginAdmin(EntityManager em) {
        Login login = new Login();
        login.setCpf("admin");
        login.setSenha("admin");

        em.persist(login);
    }
}