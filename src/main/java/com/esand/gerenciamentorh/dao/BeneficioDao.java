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
            if (transaction.isActive()) {
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
            em.getTransaction().begin();
            TypedQuery<Beneficio> query = em.createQuery("SELECT f FROM Beneficio f", Beneficio.class);
            beneficios.addAll(query.getResultList());
            return beneficios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public Beneficio buscarPorNome(String nome) {
        EntityManager em = DataBase.getEntityManager();
        Beneficio beneficio = null;

        try {
            em.getTransaction().begin();
            TypedQuery<Beneficio> query = em.createQuery("SELECT b FROM Beneficio b WHERE b.tipo = :nome", Beneficio.class);
            query.setParameter("nome", nome);
            beneficio = query.getSingleResult();
            em.getTransaction().commit();
        } catch (NoResultException e) {
            System.out.println("Nenhum benefício encontrado com o nome: " + nome);
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }

        return beneficio;
    }
}
