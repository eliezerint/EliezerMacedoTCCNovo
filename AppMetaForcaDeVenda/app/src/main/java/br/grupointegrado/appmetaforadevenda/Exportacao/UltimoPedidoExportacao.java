package br.grupointegrado.appmetaforadevenda.Exportacao;

/**
 * Created by eli on 08/12/2015.
 */
public class UltimoPedidoExportacao {

    private Integer idUltimopedidoexportado;
    private Integer idpedido;
    private Integer idVendedor;
    private Integer idPessoa;


    public UltimoPedidoExportacao() {

    }

    public Integer getIdUltimopedidoexportado() {
        return idUltimopedidoexportado;
    }

    public void setIdUltimopedidoexportado(Integer idUltimopedidoexportado) {
        this.idUltimopedidoexportado = idUltimopedidoexportado;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }
}
