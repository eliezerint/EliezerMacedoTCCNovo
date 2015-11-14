package br.grupointegrado.appmetaforadevenda.Produtos;

/**
 * Created by eli on 27/07/2015.
 */
public class UnidadeMedida {

    Integer Idunidademedida;
    String descricao;
    String sigla;

    public Integer getIdunidademedida() {
        return Idunidademedida;
    }

    public void setIdunidademedida(Integer idunidademedida) {
        Idunidademedida = idunidademedida;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
