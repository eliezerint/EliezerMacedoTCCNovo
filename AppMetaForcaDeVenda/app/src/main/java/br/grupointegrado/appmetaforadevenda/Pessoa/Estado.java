package br.grupointegrado.appmetaforadevenda.Pessoa;

/**
 * Created by eli on 23/07/2015.
 */
public class Estado {

    private String idestado  ;
    private String descricao ;
    private String idpais  ;

    public void Estado ( String id_pais,String id_estado, String descricao) {
        this.idpais = id_pais;
        this.idestado = id_estado;
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
