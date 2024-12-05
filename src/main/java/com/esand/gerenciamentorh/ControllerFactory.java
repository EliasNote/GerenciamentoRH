package com.esand.gerenciamentorh;

public interface ControllerFactory<T> {
    T cadastro();
    T edicao();
    T listar();
    T deletar();
}
