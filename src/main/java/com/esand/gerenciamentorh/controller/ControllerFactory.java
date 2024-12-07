package com.esand.gerenciamentorh.controller;

public interface ControllerFactory<T> {
    T cadastro();
    T edicao();
    T listar();
    T deletar();
}
