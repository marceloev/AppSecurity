package com.appsecurity.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "USER")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT U FROM USER U"),
        @NamedQuery(name = "User.findByLogin", query = "SELECT U FROM USER U WHERE U.login = :P_LOGIN")
})
@Table(name = "TUSER")
public class User implements Serializable {

    private static User current;
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODUSU")
    private int codigo;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "NOME")
    private String nome;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "User{" +
                "codigo=" + codigo +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

    public User() {

    }

    public User(String nome, String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    public static User getCurrent() {
        return current;
    }

    public static void setCurrent(User current) {
        User.current = current;
    }
}
