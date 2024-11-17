package com.esand.gerenciamentorh.dao;


import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Beneficio;
import com.esand.gerenciamentorh.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

public class LoginDao {
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

    public Login buscarPorCpf(String cpf) {
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

    public boolean autenticar(String cpf, String senha) {
        EntityManager em = DataBase.getEntityManager();

        try {
            Login login = buscarPorCpf(cpf);

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

    public boolean removerPorCpf(String cpf) {
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
                return true;
            } else {
                return false;
            }
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public Long buscarUltimoId() {
        EntityManager em = DataBase.getEntityManager();
        String query = "SELECT l.id FROM Login l ORDER BY l.id DESC";

        try {
            return em.createQuery(query, Long.class)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
