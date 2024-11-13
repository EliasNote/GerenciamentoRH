package com.esand.gerenciamentorh.dao;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class FuncionarioDao {
    public Funcionario salvar(Funcionario funcionario) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(funcionario);
            transaction.commit();
            return funcionario;
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

    public boolean existePorCpf(String cpf) {
        EntityManager em = DataBase.getEntityManager();
        String query = "SELECT COUNT(l) FROM Funcionario l WHERE l.cpf = :cpf";
        Long count;
        try {
            count = em.createQuery(query, Long.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return count > 0;
    }
}
