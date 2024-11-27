package com.esand.gerenciamentorh.dao;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Beneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class BeneficioDao {
    public Beneficio salvar(Beneficio beneficio) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(beneficio);
            transaction.commit();
            return beneficio;
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

    public List<Beneficio> buscarTodos() {
        EntityManager em = DataBase.getEntityManager();
        List<Beneficio> beneficios = new ArrayList<>();

        try {
            TypedQuery<Beneficio> query = em.createQuery("SELECT f FROM Beneficio f", Beneficio.class);
            beneficios.addAll(query.getResultList());
            return beneficios;
        } catch (Exception e) {
            e.printStackTrace();
            return beneficios;
        } finally {
            em.close();
        }
    }

    public Beneficio buscarPorNome(String nome) {
        EntityManager em = DataBase.getEntityManager();
        Beneficio beneficio = null;

        try {
            TypedQuery<Beneficio> query = em.createQuery("SELECT b FROM Beneficio b WHERE b.tipo = :nome", Beneficio.class);
            query.setParameter("nome", nome);
            beneficio = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum benefício encontrado com o nome: " + nome);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return beneficio;
    }

    public void excluirPorTipo(String tipo) {
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
}
