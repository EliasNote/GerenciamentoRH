package com.esand.gerenciamentorh.dao;


import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class LoginDao {
    public Login save(Login login) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(login);
            transaction.commit();
            return login;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public boolean existsByCpf(String cpf) {
        EntityManager em = DataBase.getEntityManager();
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

    public boolean autenticar(String cpf, String senha) {
        EntityManager entityManager = DataBase.getEntityManager();

        try {
            String query = "SELECT l FROM Login l WHERE l.cpf = :cpf AND l.senha = :senha";
            Login login = entityManager.createQuery(query, Login.class)
                    .setParameter("cpf", cpf)
                    .setParameter("senha", senha)
                    .getSingleResult();
            return login != null;
        } catch (NoResultException e) {
            return false;
        } finally {
            entityManager.close();
        }
    }
}
