package com.esand.gerenciamentorh.model.dao;

import com.esand.gerenciamentorh.model.database.DataBase;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import com.esand.gerenciamentorh.model.entidades.Login;
import com.esand.gerenciamentorh.model.entidades.Pagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Dao<T> {

    public T salvar(T entity) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            return entity;
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

    public Login salvarLogin(Login login) {
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

    public T atualizar(T entity) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T updatedEntity = em.merge(entity);
            transaction.commit();
            return updatedEntity;
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

    public void deletarPorTipo(String tipo) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Beneficio> query = em.createQuery("SELECT b FROM Beneficio b WHERE b.tipo = :tipo", Beneficio.class);
            query.setParameter("tipo", tipo);
            Beneficio beneficio = query.getSingleResult();
            if (beneficio != null) {
                em.remove(beneficio);
            }
            transaction.commit();
        } catch (NoResultException e) {
            System.out.println("Nenhum benefício encontrado com o tipo: " + tipo);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deletarPorCpf(Class<T> object, String cpf) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            T loginExistente = em.createQuery("SELECT l FROM " + object.getSimpleName() + " l WHERE l.cpf = :cpf", object)
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
    }

    public List<T> buscarTodos(Class<T> object) {
        EntityManager em = DataBase.getEntityManager();
        try {
            TypedQuery<T> query = em.createQuery("SELECT t FROM " + object.getSimpleName() + " t", object);
            return query.getResultList();
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

    public Login buscarPorLogin(String cpf, String email) {
        EntityManager em = DataBase.getEntityManager();

        Login objeto = null;
        String login = cpf != null ? cpf : email;

        try {
            TypedQuery<Login> query = em.createQuery("SELECT t FROM Login t WHERE t.login = :login", Login.class);
            query.setParameter("login", login);
            objeto = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nada encontrado com o login: " + login);
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
        return objeto;
    }

    public Beneficio buscarBeneficioPorTipo(String tipo) {
        EntityManager em = DataBase.getEntityManager();

        Beneficio beneficio = null;

        try {
            TypedQuery<Beneficio> query = em.createQuery("SELECT b FROM Beneficio b LEFT JOIN FETCH b.funcionarios WHERE b.tipo = :tipo", Beneficio.class);
            query.setParameter("tipo", tipo);
            beneficio = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum benefício encontrado com o tipo: " + tipo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return beneficio;
    }

    public Funcionario buscarFuncionarioPorCpf(String cpf) {
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

    public Pagamento buscarPorFuncionarioECompetencia(String cpf, YearMonth competencia) {
        EntityManager em = DataBase.getEntityManager();

        Pagamento pagamento = null;

        try {
            TypedQuery<Pagamento> query = em.createQuery(
                    "SELECT p FROM Pagamento p WHERE p.funcionario.cpf = :cpf AND p.competencia = :competencia",
                    Pagamento.class
            );
            query.setParameter("cpf", cpf);
            query.setParameter("competencia", competencia);
            pagamento = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum pagamento encontrado para o funcionário com CPF: " + cpf + " e competência: " + competencia);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return pagamento;
    }
}