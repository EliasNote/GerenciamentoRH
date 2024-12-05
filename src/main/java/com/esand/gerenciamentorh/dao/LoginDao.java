package com.esand.gerenciamentorh.dao;


import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class LoginDao implements CrudDao<Login> {

    @Override
    public Login salvar(Login login) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        login.setSenha(BCrypt.hashpw(login.getSenha(), BCrypt.gensalt()));

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

    @Override
    public List<Login> buscarTodos() {
        EntityManager em = DataBase.getEntityManager();
        List<Login> logins = new ArrayList<>();

        try {
            TypedQuery<Login> query = em.createQuery("SELECT l FROM Login l", Login.class);
            logins = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return logins;
    }

    @Override
    public Login buscarPorId(Long id) {
        return null;
    }

    @Override
    public Login buscarGenerico(String cpf) {
        EntityManager em = DataBase.getEntityManager();
        Login login = null;

        try {
            TypedQuery<Login> query = em.createQuery("SELECT l FROM Login l WHERE l.cpf = :cpf", Login.class);
            query.setParameter("cpf", cpf);
            login = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum login encontrado com o cpf: " + cpf);
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
        return login;
    }

    @Override
    public Login atualizar(Login login) {
        return null;
    }

    @Override
    public Login deletar(String cpf) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            String query = "SELECT l FROM Login l WHERE l.cpf = :cpf";
            Login loginExistente = em.createQuery(query, Login.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            if (loginExistente != null) {
                em.remove(loginExistente);
                transaction.commit();
            }
        } catch (NoResultException e) {
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return null;
    }

    public boolean autenticar(String cpf, String senha) {
        EntityManager em = DataBase.getEntityManager();

        try {
            Login login = buscarGenerico(cpf);

            if (login == null) {
                return false;
            }

            return BCrypt.checkpw(senha, login.getSenha());
        } catch (NoResultException e) {
            return false;
        } finally {
            em.close();
        }
    }
}
