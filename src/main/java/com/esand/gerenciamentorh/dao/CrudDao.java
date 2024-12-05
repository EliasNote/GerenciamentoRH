package com.esand.gerenciamentorh.dao;

import java.util.List;

public interface CrudDao<T> {
    T salvar(T t);
    List<T> buscarTodos();
    T buscarPorId(Long id);
    T buscarGenerico(String t);
    T atualizar(T t);
    T deletar(String t);
}
