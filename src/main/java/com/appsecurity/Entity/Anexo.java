package com.appsecurity.Entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity(name = "ANEXO")
@NamedQueries({
        @NamedQuery(name = "Anexo.findByUser",
                query = "SELECT A FROM ANEXO A WHERE A.user = :P_CODUSU ORDER BY 1")
})
@Table(name = "TANX")
public class Anexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODANX")
    private int codigo;

    @Column(name = "CODUSU")
    private int user;

    @Column(name = "DESCRANX")
    private String descricao;

    @Column(name = "ARQANX")
    private byte[] anexo;

    @Column(name = "TIPOANX")
    private String tipo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Anexo{" +
                "codigo=" + codigo +
                ", user=" + user +
                ", descricao='" + descricao + '\'' +
                ", anexo=" + Arrays.toString(anexo) +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
