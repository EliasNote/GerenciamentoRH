package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Beneficio;

import java.util.List;

public class BeneficioService {
    private final Dao<Beneficio> beneficioDao = new Dao();

    public void salvar(Beneficio beneficio) {
        beneficioDao.salvar(beneficio);
    }

    public List<Beneficio> buscarTodos() {
        return beneficioDao.buscarTodos(Beneficio.class);
    }

    public Beneficio buscarBeneficioPorTipo(String tipo) {
        return beneficioDao.buscarBeneficioPorTipo(tipo);
    }

    public void atualizar(Beneficio beneficio) {
        beneficioDao.atualizar(beneficio);
    }

    public void deletarPorTipo(String tipo) {
        beneficioDao.deletarPorTipo(tipo);
    }
}
