
package br.grupointegrado.appmetaforadevenda.Importacao;

import java.util.Date;

/**
 * Created by eli on 06/12/2015.
 */
public class Importacao {

    private Integer idimportacao;
    private Date dataimportacao;
    private Integer quantidade;

    public Importacao() {
    }

    public Importacao(Integer idimportacao, Date dataimportacao, Integer quantidade) {
        this.idimportacao = idimportacao;
        this.dataimportacao = dataimportacao;
        this.quantidade = quantidade;
    }
    public Importacao( Date dataimportacao, Integer quantidade) {
        this.dataimportacao = dataimportacao;
        this.quantidade = quantidade;
    }

    public Integer getIdimportacao() {
        return idimportacao;
    }

    public void setIdimportacao(Integer idimportacao) {
        this.idimportacao = idimportacao;
    }

    public Date getDataimportacao() {
        return dataimportacao;
    }

    public void setDataimportacao(Date dataimportacao) {
        this.dataimportacao = dataimportacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
