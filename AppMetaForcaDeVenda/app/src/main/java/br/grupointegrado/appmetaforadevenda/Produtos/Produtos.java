package br.grupointegrado.appmetaforadevenda.Produtos;

/**
 * Created by eli on 27/07/2015.
 */
public class Produtos {
    private Integer Idproduto;
    private Integer Idgrupopoduto;
    private Integer IdUnidademedida;
    private String descricao;
    private String descricaoUnidademedida;
    private  String descricaoGrupoProduto;
    private  Double valorUnitario;


    public Produtos() {
    }

    public Produtos(Integer idgrupopoduto, Integer idUnidademedida, String descricaoUnidademedida, String descricao, String descricaoGrupoProduto) {
        this.Idgrupopoduto = idgrupopoduto;
        this.IdUnidademedida = idUnidademedida;
        this.descricao = descricao;
        this.descricaoUnidademedida = descricaoUnidademedida;
        this.descricaoGrupoProduto = descricaoGrupoProduto;
    }

    public Produtos(Integer idproduto, Integer idgrupopoduto, Integer idUnidademedida, String descricaoUnidademedida, String descricao, String descricaoGrupoProduto) {
        this.Idproduto = idproduto;
        this.Idgrupopoduto = idgrupopoduto;
        this.IdUnidademedida = idUnidademedida;
        this.descricao = descricao;
        this.descricaoUnidademedida = descricaoUnidademedida;
        this.descricaoGrupoProduto = descricaoGrupoProduto;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoGrupoProduto() {
        return descricaoGrupoProduto;
    }

    public void setDescricaoGrupoProduto(String descricaoGrupoProduto) {
        this.descricaoGrupoProduto = descricaoGrupoProduto;
    }

    public Integer getIdproduto() {
        return Idproduto;
    }

    public void setIdproduto(Integer idproduto) {
        Idproduto = idproduto;
    }
    

    public String getDescricaoUnidademedida() {
        return descricaoUnidademedida;
    }

    public void setDescricaoUnidademedida(String descricaoUnidademedida) {
        this.descricaoUnidademedida = descricaoUnidademedida;
    }

    public Integer getIdUnidademedida() {
        return IdUnidademedida;
    }

    public void setIdUnidademedida(Integer idUnidademedida) {
        IdUnidademedida = idUnidademedida;
    }

    public Integer getIdgrupopoduto() {
        return Idgrupopoduto;
    }

    public void setIdgrupopoduto(Integer idgrupopoduto) {
        Idgrupopoduto = idgrupopoduto;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return  this.descricao;
    }
}
