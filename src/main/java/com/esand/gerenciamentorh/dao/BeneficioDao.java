package com.esand.gerenciamentorh.dao;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Beneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
}
