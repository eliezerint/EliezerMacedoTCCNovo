package br.grupointegrado.appmetaforadevenda.Produtos;

/**
 * Created by eli on 27/07/2015.
 */
public class Tabelapreco {

    private Integer idTabelapreco;
    private Integer idProduto;
    private String descricao;
    private String descricaoProduto;
    private String descricaotabelaItem;
    private Double vlUnitario;

    public Tabelapreco() {

    }

    public Integer getIdTabelapreco() {
        return idTabelapreco;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
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

    public String getDescricaotabelaItem() {
        return descricaotabelaItem;
    }

    public void setDescricaotabelaItem(String descricaotabelaItem) {
        this.descricaotabelaItem = descricaotabelaItem;
    }

    public Double getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(Double vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    @Override
    public String toString() {
        return (this.getDescricao());
    }
}

