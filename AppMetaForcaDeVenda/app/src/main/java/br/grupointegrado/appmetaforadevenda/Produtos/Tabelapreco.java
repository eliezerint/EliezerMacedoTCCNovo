package br.grupointegrado.appmetaforadevenda.Produtos;

/**
 * Created by eli on 27/07/2015.
 */
public class Tabelapreco {

    private Integer idTabelapreco;
    private Integer idProduto;
    private String descricao;
    private String descricaoiten;

    public Tabelapreco() {

    }

    public Integer getIdTabelapreco() {
        return idTabelapreco;
    }

    public String getDescricaoiten() {
        return descricaoiten;
    }

    public void setDescricaoiten(String descricaoiten) {
        this.descricaoiten = descricaoiten;
    }

    public void setIdTabelapreco(Integer idTabelapreco) {
        this.idTabelapreco = idTabelapreco;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return (this.getDescricao());
    }
}

