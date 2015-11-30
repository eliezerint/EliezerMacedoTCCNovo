
package br.grupointegrado.appmetaforadevenda.Pedido;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by eli on 27/07/2015.
 */
public class Pedido implements Serializable {

    private Integer Idpedido;
    private Integer Idpessoa;
    private Integer Idvendedor;
    private Integer IdCondicaopag;
    private Integer Idfilial;
    private Date datapedido;
    private Double total;
    private String nome;
    private String Cidade;
    private String nomevendedor;

    public Pedido() {

    }

    public Pedido(Integer idpedido, Integer idpessoa, Integer idvendedor, Integer idCondicaopag, Integer idfilial, Date datapedido, Double total) {
        this.Idpedido = idpedido;
        this.Idpessoa = idpessoa;
        this.Idvendedor = idvendedor;
        this.IdCondicaopag = idCondicaopag;
        this.Idfilial = idfilial;
        this.datapedido = datapedido;
        this.total = total;
    }

    public Pedido(Integer idpessoa, Integer idvendedor, Integer idCondicaopag, Integer idfilial, Date datapedido, Double total) {
        this. Idpessoa = idpessoa;
        this.Idvendedor = idvendedor;
        this. IdCondicaopag = idCondicaopag;
        this.Idfilial = idfilial;
        this.datapedido = datapedido;
        this.total = total;
    }

    public String getNomevendedor() {
        return nomevendedor;
    }

    public void setNomevendedor(String nomevendedor) {
        this.nomevendedor = nomevendedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String fantasia) {
        this.Cidade = fantasia;
    }

    public Integer getIdpedido() {
        return Idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        Idpedido = idpedido;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDatapedido() {
        return datapedido;
    }

    public void setDatapedido(Date datapedido) {
        this.datapedido = datapedido;
    }

    public Integer getIdfilial() {
        return Idfilial;
    }

    public void setIdfilial(Integer idfilial) {
        Idfilial = idfilial;
    }

    public Integer getIdCondicaopag() {
        return IdCondicaopag;
    }

    public void setIdCondicaopag(Integer idCondicaopag) {
        IdCondicaopag = idCondicaopag;
    }

    public Integer getIdvendedor() {
        return Idvendedor;
    }

    public void setIdvendedor(Integer idvendedor) {
        Idvendedor = idvendedor;
    }

    public Integer getIdpessoa() {
        return Idpessoa;
    }

    public void setIdpessoa(Integer idpessoa) {
        Idpessoa = idpessoa;
    }
}
