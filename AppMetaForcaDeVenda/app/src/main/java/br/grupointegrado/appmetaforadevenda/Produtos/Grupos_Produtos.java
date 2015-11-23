
package br.grupointegrado.appmetaforadevenda.Produtos;

/**
 * Created by eli on 27/07/2015.
 */
public class Grupos_Produtos {

    private Integer Idproduto;
    private String Descricao;
    private Integer IdgrupoProduto;

    public Grupos_Produtos(Integer idproduto, String descricao) {
        Idproduto = idproduto;
        Descricao = descricao;
    }


    public Grupos_Produtos() {
    }

    public Integer getIdgrupoProduto() {
        return IdgrupoProduto;
    }

    public void setIdgrupoProduto(Integer idgrupoProduto) {
        IdgrupoProduto = idgrupoProduto;
    }

    public Integer getIdproduto() {
        return Idproduto;
    }

    public void setIdproduto(Integer idproduto) {
        Idproduto = idproduto;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    @Override
    public String toString() {
        return  this.Descricao;
    }
}


