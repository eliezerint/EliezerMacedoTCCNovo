package br.grupointegrado.appmetaforadevenda.Pessoa;

/**
 * Created by eli on 23/07/2015.
 */
public class Estado {

    String idestado  = "PR";
    String descricao = "Campo Mourao";
    String idpais  =" BR";

    public void Estado (String id_estado, String id_pais, String descricao) {
        this.idestado = id_estado;
        this.idpais = id_pais;
        this.descricao = descricao;
    }

    public void Estado () {

    }


    public String getIdestado() {
        return idestado;
    }

    public void setIdestado(String idestado) {
        this.idestado = idestado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdpais() {
        return idpais;
    }

    public void setIdpais(String idpais) {
        this.idpais = idpais;
    }

    @Override
    public String toString() {
        return idestado.toString();

    }
}
