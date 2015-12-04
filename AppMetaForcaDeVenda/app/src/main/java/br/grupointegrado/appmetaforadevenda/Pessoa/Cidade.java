package br.grupointegrado.appmetaforadevenda.Pessoa;

import java.io.Serializable;
import java.io.SerializablePermission;

/**
 * Created by eli on 23/07/2015.
 */
public class Cidade  implements Serializable {

    private Integer idcidade;
    private String idestado;
    private String descricao;
    private String ibge;
    private String Pais;
    private String nomeestado;


    public Cidade( String pais, String idestado,String descricao, String ibge) {
        this.Pais = pais;
        this.idestado = idestado;
        this.descricao = descricao;
        this.ibge = ibge;
    }
    public Cidade( String pais, String idestado,Integer idcidade, String descricao, String ibge) {
        this.Pais = pais;
        this.idestado = idestado;
        this.idcidade = idcidade;
        this.descricao = descricao;
        this.ibge = ibge;
    }

    public Cidade(String idestado,Integer idcidade, String descricao, String ibge) {

        this.idestado = idestado;
        this.idcidade = idcidade;
        this.descricao = descricao;
        this.ibge = ibge;
    }


    public Cidade() {

    }

    public Cidade(int idcidade, String descricao , String idEstado) {

        this.idestado = idEstado;
        this.descricao = descricao;
        this.idcidade = idcidade;
    }


    public Integer getIdcidade() {
        return idcidade;
    }

    public void setIdcidade(Integer idcidade) {
        this.idcidade = idcidade;
    }

    public String getNomeestado() {
        return nomeestado;
    }

    public void setNomeestado(String nomeestado) {
        this.nomeestado = nomeestado;
    }



    public String getIdestado() {
        return idestado;
    }

    public void setIdestado(String idestado) {
        this.idestado = idestado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    @Override
    public String toString() {
        return  descricao.toString() ;
    }
}
