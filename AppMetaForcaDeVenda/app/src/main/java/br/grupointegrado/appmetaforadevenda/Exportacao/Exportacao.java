package br.grupointegrado.appmetaforadevenda.Exportacao;

import java.util.Date;

/**
 * Created by eli on 08/12/2015.
 */
public class Exportacao {
    private Integer idExportacao;
    private Integer idUltimaExportacao;
    private Date dataultimaExportacao;
    private Integer QuantidadeRegistro;

    public Exportacao() {
    }

    public Integer getIdExportacao() {
        return idExportacao;
    }

    public void setIdExportacao(Integer idExportacao) {
        this.idExportacao = idExportacao;
    }

    public Integer getIdUltimaExportacao() {
        return idUltimaExportacao;
    }

    public void setIdUltimaExportacao(Integer idUltimaExportacao) {
        this.idUltimaExportacao = idUltimaExportacao;
    }

    public Date getDataultimaExportacao() {
        return dataultimaExportacao;
    }

    public void setDataultimaExportacao(Date dataultimaExportacao) {
        this.dataultimaExportacao = dataultimaExportacao;
    }

    public Integer getQuantidadeRegistro() {
        return QuantidadeRegistro;
    }

    public void setQuantidadeRegistro(Integer quantidadeRegistro) {
        QuantidadeRegistro = quantidadeRegistro;
    }
}
