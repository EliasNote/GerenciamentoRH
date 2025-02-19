package com.esand.gerenciamentorh.model.dao;

import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import com.esand.gerenciamentorh.model.entidades.Login;
import com.esand.gerenciamentorh.model.entidades.Pagamento;
import jakarta.persistence.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Dao<T> {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gerenciamentoRh");

    public static final EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public T salvar(T entity) {
        EntityManager em = getEntityManager();
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

    public T atualizar(T entity) {
        EntityManager em = getEntityManager();
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

    public void deletar(String tipo) {
        EntityManager em = getEntityManager();
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

    public boolean deletar(Class<T> object, String cpf) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            T objetoExistente = em.createQuery("SELECT l FROM " + object.getSimpleName() + " l WHERE l.cpf = :cpf", object)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            if (objetoExistente == null) {
                return false;
            }

            em.remove(objetoExistente);
            transaction.commit();

            return true;
        } catch (NoResultException e) {
            System.out.println("Não encontrado");
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

    public void deletar(String cpf, YearMonth competencia) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            TypedQuery<Pagamento> query = em.createQuery(
                    "SELECT p FROM Pagamento p WHERE p.competencia = :competencia AND p.funcionario.cpf = :cpf",
                    Pagamento.class
            );
            query.setParameter("competencia", competencia);
            query.setParameter("cpf", cpf);

            Pagamento pagamento = query.getSingleResult();

            if (pagamento != null) {
                em.remove(pagamento);
                transaction.commit();
            }
        } catch (NoResultException e) {
            System.out.println("Nenhum pagamento encontrado para a competência: " + competencia + " e CPF: " + cpf);
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
        EntityManager em = getEntityManager();
        try {
            TypedQuery<T> query = em.createQuery("SELECT t FROM " + object.getSimpleName() + " t", object);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Funcionario> buscarTodos() {
        EntityManager em = getEntityManager();

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

    public Login buscarLoginPorCpf(String cpf) {
        EntityManager em = getEntityManager();

        Login objeto = null;

        try {
            TypedQuery<Login> query = em.createQuery("SELECT t FROM Login t WHERE t.cpf = :cpf", Login.class);
            query.setParameter("cpf", cpf);
            objeto = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nada encontrado com o cpf: " + cpf);
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

    public Beneficio buscarPorTipo(String tipo) {
        EntityManager em = getEntityManager();

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

    public Funcionario buscarPorCpf(String cpf) {
        EntityManager em = getEntityManager();

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

    public Pagamento buscarPorCpfECompetencia(String cpf, YearMonth competencia) {
        EntityManager em = getEntityManager();

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

    public List<Pagamento> buscarPorCompetencia(YearMonth competencia) {
        EntityManager em = getEntityManager();
        List<Pagamento> pagamentos = new ArrayList<>();

        try {
            TypedQuery<Pagamento> query = em.createQuery(
                    "SELECT distinct p FROM Pagamento p " +
                            "LEFT JOIN FETCH p.proventos " +
                            "LEFT JOIN FETCH p.descontos " +
                            "WHERE p.competencia = :competencia",
                    Pagamento.class
            );
            query.setParameter("competencia", competencia);
            pagamentos = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return pagamentos;
    }
}