package com.esand.gerenciamentorh.dao;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

import static com.esand.gerenciamentorh.Utils.showErrorMessage;

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

    public Funcionario carregarFuncionarioComBeneficios(String cpf) {
        EntityManager em = DataBase.getEntityManager();
        Funcionario funcionario = null;

        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f LEFT JOIN FETCH f.beneficios WHERE f.cpf = :cpf", Funcionario.class);
            query.setParameter("cpf", cpf);
            funcionario = query.getSingleResult();
        } catch (NoResultException e) {
            showErrorMessage("Nenhum funcionário encontrado com o CPF: " + cpf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return funcionario;
    }

    public List<Funcionario> buscarTodos() {
        EntityManager em = DataBase.getEntityManager();
        List<Funcionario> funcionarios = null;

        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f LEFT JOIN FETCH f.beneficios", Funcionario.class);
            funcionarios = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return funcionarios;
    }
}
