
package br.grupointegrado.appmetaforadevenda.Produtos;

/**
 * Created by eli on 03/11/2015.
 */
public class TabelaItenPreco {

    private Integer idtabelaItenpreco;
    private Integer idtabelapreco;
    private String descricao;
    private Double  vlunitario;

    public TabelaItenPreco() {
    }

    public Integer getIdtabelaItenpreco() {
        return idtabelaItenpreco;
    }

    public void setIdtabelaItenpreco(Integer idtabelaItenpreco) {
        this.idtabelaItenpreco = idtabelaItenpreco;
    }

    public Double getVlunitario() {
        return vlunitario;
    }

    public void setVlunitario(Double vlunitario) {
        this.vlunitario = vlunitario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdtabelapreco() {
        return idtabelapreco;
    }

    public void setIdtabelapreco(Integer idtabelapreco) {
        this.idtabelapreco = idtabelapreco;
    }

    @Override
    public String toString() {
        return (this.getDescricao());
    }
}
