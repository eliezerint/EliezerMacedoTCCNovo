package br.grupointegrado.appmetaforadevenda.Pedido;

/**
 * Created by eli on 27/06/2015.
 */
public class CondicaoPagamento {

    Integer Idcodicaopagamento;
    String descricao;
    Double quantidade;
    Integer intervelo;



    public CondicaoPagamento(String descricao, Double quantidade, Integer intervalo) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.intervelo = intervalo;
    }

    public CondicaoPagamento() {


    }

    public Integer getIntervelo() {
        return intervelo;
    }

    public void setIntervalo(Integer intervelo) {
        this.intervelo = intervelo;
    }

    public Integer getIdcodicaopagamento() {
        return Idcodicaopagamento;
    }

    public void setIdcodicaopagamento(Integer idcodicaopagamento) {
        Idcodicaopagamento = idcodicaopagamento;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
