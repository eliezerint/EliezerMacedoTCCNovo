package br.grupointegrado.appmetaforadevenda.Vendedor;

/**
 * Created by eli on 27/07/2015.
 */
public class Vendedor {

    private Integer Idvendedor;
    private String Nome;
    private Double max_desconto;
    private Double max_acrescimo;

    public Vendedor(String nome) {
        Nome = nome;
    }

    public  Vendedor(){

    }

    public Vendedor(Integer idvendedor, String nome, Double max_desconto) {
        this.Idvendedor = idvendedor;
        this.Nome = nome;
        this.max_desconto = max_desconto;
    }

    public Double getMax_desconto() {
        return max_desconto;
    }

    public void setMax_desconto(Double max_desconto) {
        this.max_desconto = max_desconto;
    }

    public Double getMax_acrescimo() {
        return max_acrescimo;
    }

    public void setMax_acrescimo(Double max_acrescimo) {
        this.max_acrescimo = max_acrescimo;
    }

    public Integer getIdvendedor() {
        return Idvendedor;
    }

    public void setIdvendedor(Integer idvendedor) {
        Idvendedor = idvendedor;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
}
