package br.grupointegrado.appmetaforadevenda.Pessoa;

import android.text.Editable;

/**
 * Created by eli on 02/09/2015.
 */
public class Telefone {

    Integer idTelefone;
    Integer idPessoa;
    String Numero;
    String CPF;
    String tipo;

    public Telefone() {

    }

    public Telefone(Integer idTelefone, Integer idPessoa, String numero, String tipo ,String cpf) {
        this.idTelefone= idTelefone;
        this.idPessoa = idPessoa;
        this.CPF = cpf;
        this.Numero = numero;
        this.tipo = tipo;
    }


    public Telefone(String numero ,String tipo) {
        this.Numero = numero;
        this.tipo = tipo;
    }

    public Telefone(Integer idPessoa, String numero, String tipo ,String cpf) {
        this.idPessoa = idPessoa;
        this.CPF = cpf;
        this.Numero = numero;
        this.tipo = tipo;
    }




    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public Integer getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(Integer idTelefone) {
        this.idTelefone = idTelefone;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }
}
