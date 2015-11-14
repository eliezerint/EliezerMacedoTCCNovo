package br.grupointegrado.appmetaforadevenda.Vendedor;

/**
 * Created by eli on 27/07/2015.
 */
public class Parametros {
    Integer Idparam;
    Integer Idvendedor;
    Integer faixaIincial;
    Integer faixafinal;
    Integer ultimocadastro;

    public Integer getIdparam() {
        return Idparam;
    }

    public void setIdparam(Integer idparam) {
        Idparam = idparam;
    }

    public Integer getIdvendedor() {
        return Idvendedor;
    }

    public void setIdvendedor(Integer idvendedor) {
        Idvendedor = idvendedor;
    }

    public Integer getFaixaIincial() {
        return faixaIincial;
    }

    public void setFaixaIincial(Integer faixaIincial) {
        this.faixaIincial = faixaIincial;
    }

    public Integer getFaixafinal() {
        return faixafinal;
    }

    public void setFaixafinal(Integer faixafinal) {
        this.faixafinal = faixafinal;
    }

    public Integer getUltimocadastro() {
        return ultimocadastro;
    }

    public void setUltimocadastro(Integer ultimocadastro) {
        this.ultimocadastro = ultimocadastro;
    }
}
