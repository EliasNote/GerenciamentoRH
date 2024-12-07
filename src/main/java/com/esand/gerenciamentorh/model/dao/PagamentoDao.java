package com.esand.gerenciamentorh.model.dao;

import com.esand.gerenciamentorh.model.database.DataBase;
import com.esand.gerenciamentorh.model.entidades.Pagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.YearMonth;
import java.util.List;

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

    public Pagamento atualizar(Pagamento pagamento) {
        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Pagamento pagamentoAtualizado = em.merge(pagamento);
            transaction.commit();
            return pagamentoAtualizado;
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
