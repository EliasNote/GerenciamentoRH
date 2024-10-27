package com.esand.gerenciamentorh.entidades;

import jakarta.persistence.*;

@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String senha;

    public Login() {
    }

    public Login(long id, String cpf, String senha) {
        this.id = id;
        this.cpf = cpf;
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
