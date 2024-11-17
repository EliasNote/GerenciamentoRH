package com.esand.gerenciamentorh.dao;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Pagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PagamentoDao {
    public Pagamento salvar(Pagamento pagamento) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(pagamento);
            transaction.commit();
            return pagamento;
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

    public Pagamento buscarPorId(Long id) {
        EntityManager em = DataBase.getEntityManager();
        Pagamento pagamento = null;

        try {
            pagamento = em.find(Pagamento.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return pagamento;
    }

    public Pagamento buscarPorCpf(String cpf) {
        EntityManager em = DataBase.getEntityManager();
        Pagamento pagamento = null;

        try {
            TypedQuery<Pagamento> query = em.createQuery(
                    "SELECT p FROM Pagamento p WHERE p.funcionario.cpf = :cpf",
                    Pagamento.class
            );
            query.setParameter("cpf", cpf);
            pagamento = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return pagamento;
    }

    public Pagamento atualizarPagamentoPorId(Long id, Pagamento novosDados) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Pagamento pagamentoExistente = buscarPorId(id);
            if (pagamentoExistente != null) {
                pagamentoExistente.setCompetencia(novosDados.getCompetencia());
                pagamentoExistente.setSalarioLiquido(novosDados.getSalarioLiquido());
                pagamentoExistente.setHorasExtras(novosDados.getHorasExtras());
                pagamentoExistente.setHorasFaltas(novosDados.getHorasFaltas());

                em.merge(pagamentoExistente);
                transaction.commit();
                return pagamentoExistente;
            } else {
                transaction.rollback();
                return null;
            }
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
}
