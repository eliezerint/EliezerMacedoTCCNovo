package br.grupointegrado.appmetaforadevenda.Pedido;

/**
 * Created by eli on 27/07/2015.
 */
public class Filial {

    Integer Idfilial;
    String descricao;

    public Filial(Integer idfilial, String descricao) {
        Idfilial = idfilial;
        this.descricao = descricao;
    }

    public Filial(String descricao){
        this.descricao = descricao;

    }
    public Filial(){

    }

    public Integer getIdfilial() {
        return Idfilial;
    }

    public void setIdfilial(Integer idfilial) {
        this.Idfilial = idfilial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Filial :" + descricao.toString();
    }
}
