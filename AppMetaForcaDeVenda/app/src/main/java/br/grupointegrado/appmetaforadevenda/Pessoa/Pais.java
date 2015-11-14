package br.grupointegrado.appmetaforadevenda.Pessoa;

/**
 * Created by eli on 10/10/2015.
 */
public class Pais {
    String idpais ;
    String nome;

    public Pais(String idpais, String nome) {
        this.idpais = idpais;
        this.nome = nome;
    }

    public Pais() {

    }

    public String getIdpais() {
        return idpais;
    }

    public void setIdpais(String idpais) {
        this.idpais = idpais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return idpais.toString();
    }
}
