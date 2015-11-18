package br.grupointegrado.appmetaforadevenda.Pedido;

import java.io.Serializable;

/**
 * Created by eli on 27/07/2015.
 */
public class ItensPedido implements Serializable{

    private Integer idItens;
    private Integer idpedido;
    private Integer idpessoa;
    private Integer idvendedor;
    private Integer IdProduto;
    private String produto;
    private Double desconto;
    private Double quantidade;
    private Double vlunitario;
    private Double total;
    private Double totalCdesconto;
    private String nomeproduto;



    public ItensPedido(String produto, Double desconto, Double quantidade, Double vlunitario, Double total) {
        this.produto = produto;
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.vlunitario = vlunitario;
        this.total = total;
    }
    public ItensPedido(Integer idProduto, String produto, Double vlunitario, Double quantidade, Double desconto, Double total,Double totalCdesconto) {
        this.IdProduto = idProduto;
        this.produto = produto;
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.vlunitario = vlunitario;
        this.total = total;
        this.totalCdesconto = totalCdesconto;
    }
    public ItensPedido(Integer idItens,Integer idpedido,Integer idpessoa,Integer idvendedor,Integer idProduto,  Double desconto, Double quantidade, Double vlunitario) {
        this.idItens    = idItens;
        this.idpedido   = idpedido;
        this.idpessoa   = idpessoa;
        this.idvendedor = idvendedor;
        this.IdProduto  = idProduto;
        this.desconto   = desconto;
        this.quantidade = quantidade;
        this.vlunitario = vlunitario;
    }


    public ItensPedido() {

    }

    public String getNomeproduto() {
        return nomeproduto;
    }

    public void setNomeproduto(String nomeproduto) {
        this.nomeproduto = nomeproduto;
    }

    public Integer getIdItens() {
        return idItens;
    }

    public void setIdItens(Integer idItens) {
        this.idItens = idItens;
    }

    public Integer getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(Integer idpessoa) {
        this.idpessoa = idpessoa;
    }

    public Integer getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Integer idvendedor) {
        this.idvendedor = idvendedor;
    }

    public Double getTotalCdesconto() {
        return totalCdesconto;
    }

    public void setTotalCdesconto(Double totalCdesconto) {
        this.totalCdesconto = totalCdesconto;
    }

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(Integer idProduto) {
        IdProduto = idProduto;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Double getVlunitario() {
        return vlunitario;
    }

    public void setVlunitario(Double vlunitario) {
        this.vlunitario = vlunitario;
    }
}
