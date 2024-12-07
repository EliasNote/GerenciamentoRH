package com.esand.gerenciamentorh.model.dao;

import com.esand.gerenciamentorh.model.database.DataBase;
import com.esand.gerenciamentorh.model.entidades.Avaliacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AvaliacaoDao {

    private EntityManager em;

    public AvaliacaoDao() {
        this.em = DataBase.getEntityManager();
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(avaliacao);
            transaction.commit();
            return avaliacao;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public List<Avaliacao> buscarTodos() {
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a", Avaliacao.class);
        return query.getResultList();
    }

    public Avaliacao atualizar(Avaliacao avaliacao) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Avaliacao avaliacaoAtualizada = em.merge(avaliacao);
            transaction.commit();
            return avaliacaoAtualizada;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
}