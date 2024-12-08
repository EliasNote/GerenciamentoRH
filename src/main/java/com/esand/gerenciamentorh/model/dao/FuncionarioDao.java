package com.esand.gerenciamentorh.model.dao;

import com.esand.gerenciamentorh.model.database.DataBase;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static com.esand.gerenciamentorh.controller.Utils.showErrorMessage;

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
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Funcionario> buscarTodos() {
        EntityManager em = DataBase.getEntityManager();

        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            TypedQuery<Funcionario> query = em.createQuery(
                    "SELECT f FROM Funcionario f LEFT JOIN FETCH f.beneficios",
                    Funcionario.class
            );
            funcionarios = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return funcionarios;
    }

    public Funcionario buscarGenerico(String cpf) {
        EntityManager em = DataBase.getEntityManager();

        Funcionario funcionario = null;

        try {
            TypedQuery<Funcionario> query = em.createQuery(
                    "SELECT f FROM Funcionario f LEFT JOIN FETCH f.beneficios WHERE f.cpf = :cpf",
                    Funcionario.class
            );
            query.setParameter("cpf", cpf);
            funcionario = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return funcionario;
    }

    public Funcionario atualizar(Funcionario funcionario) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Funcionario funcionarioAtualizado = em.merge(funcionario);
            transaction.commit();
            return funcionarioAtualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public Funcionario deletar(String cpf) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Funcionario> query = em.createQuery(
                    "SELECT f FROM Funcionario f WHERE f.cpf = :cpf",
                    Funcionario.class
            );
            query.setParameter("cpf", cpf);
            Funcionario funcionario = query.getSingleResult();
            if (funcionario != null) {
                em.remove(funcionario);
                transaction.commit();
            }
        } catch (NoResultException e) {
            showErrorMessage("Nenhum funcionário encontrado com o CPF: " + cpf);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return null;
    }


}
