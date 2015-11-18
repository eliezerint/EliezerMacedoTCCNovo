package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.ItensPedido;
import br.grupointegrado.appmetaforadevenda.Pedido.Pedido;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;
import br.grupointegrado.appmetaforadevenda.Vendedor.Vendedor;

import static br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil.dateParaString;
import static br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil.stringParaSQLDate;

/**
 * Created by eli on 07/10/2015.
 */
public class PedidoDao extends AppDao {
    public PedidoDao(Context context) {
        super(context);
    }


    // inserção e consulta de pedido


    public void savePedido(Pedido pedido, int idpedido) {
        ContentValues cv = new ContentValues();
        cv.put("idPedido", idpedido);
        cv.put("idPessoa", pedido.getIdpessoa());
        cv.put("idVendedor", pedido.getIdvendedor());
        cv.put("idcondicaopagamento", pedido.getIdCondicaopag());
        cv.put("idFilial", pedido.getIdfilial());
        cv.put("Data_pedido", ConvesorUtil.dateParaString(pedido.getDatapedido()));
        cv.put("Valor_total",pedido.getTotal());




        getWritableDatabase().insert("Pedido", null, cv);

    }

    public void Update(Pedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("idPedido", pedido.getIdpedido());
        cv.put("idPessoa", pedido.getIdpessoa());
        cv.put("idVendedor", pedido.getIdvendedor());
        cv.put("idcondicaopagamento", pedido.getIdCondicaopag());
        cv.put("idFilial", pedido.getIdfilial());
        cv.put("Data_pedido", ConvesorUtil.dateParaString(pedido.getDatapedido()));
        cv.put("Valor_total",pedido.getTotal());


        getWritableDatabase().update("Pedido", cv, "idPedido = ? and idPessoa = ?",
                new String[]{pedido.getIdpedido().toString(), pedido.getIdpessoa().toString(),
                        pedido.getIdvendedor().toString()});



    }

    public void delete(Integer id) {

        getWritableDatabase().delete("Pedido", "idPedido = ?", new String[]{id.toString()});
    }

    public List<Pedido> list() {
        Cursor c = getReadableDatabase().rawQuery("Select  p.idPedido," +
                "   p.idPessoa, p.idVendedor , p.idcondicaopagamento , p.idFilial ,pe.Razao_socialNome , pe.Nome_fantasiaApelido," +
                "   p.Data_pedido , p.Valor_total " +
                "   From Pedido p, Pessoa pe", null);

        List<Pedido> pedidos = new ArrayList<>();


        while (c.moveToNext()) {

            Pedido pedido = new Pedido();
            pedido.setIdpedido(c.getInt(0));
            pedido.setIdpessoa(c.getInt(1));
            pedido.setIdvendedor(c.getInt(2));
            pedido.setIdCondicaopag(c.getInt(3));
            pedido.setIdfilial(c.getInt(4));
            pedido.setNome(c.getString(5));
            pedido.setFantasia(c.getString(6));
            pedido.setDatapedido(ConvesorUtil.stringParaDate(c.getString(7)));
            pedido.setTotal(c.getDouble(8));


            pedidos.add(pedido);

        }
        c.close();
        return pedidos;


    }

    public List<Pedido> listCpfCnpj(String CpfCnpj) {
        Cursor c = getReadableDatabase().rawQuery("Select  p.idPedido,"
                + "  p.idPessoa, p.idVendedor , p.idcondicaopagamento , p.idFilial ,pe.Razao_socialNome , pe.Nome_fantasiaApelido," +
                "    p.Data_pedido , p.Valor_total "+
                "    From Pedido p ,Pessoa pe where p.idPessoa = pe.idPessoa and pe.CNPJCPF = ?", new String[]{CpfCnpj});

        List<Pedido> pedidos = new ArrayList<>();


        while (c.moveToNext()) {

            Pedido pedido = new Pedido();
            pedido.setIdpedido(c.getInt(0));
            pedido.setIdpessoa(c.getInt(1));
            pedido.setIdvendedor(c.getInt(2));
            pedido.setIdCondicaopag(c.getInt(3));
            pedido.setIdfilial(c.getInt(4));
            pedido.setNome(c.getString(5));
            pedido.setFantasia(c.getString(6));
            pedido.setDatapedido(ConvesorUtil.stringParaDate(c.getString(7)));
            pedido.setTotal(c.getDouble(8));


            pedidos.add(pedido);

        }
        c.close();
        return pedidos;


    }
    public List<Pedido> list(String nome) {
        Cursor c = getReadableDatabase().rawQuery("Select  p.idPedido,"
                + "  p.idPessoa, p.idVendedor , p.idcondicaopagamento , p.idFilial ,pe.Razao_socialNome , pe.Nome_fantasiaApelido," +
                "    p.Data_pedido , p.Valor_total "+
                "    From Pedido p, Pessoa pe where p.idPessoa = pe.idPessoa and pe.Razao_socialNome like ?", new String[]{"%"+nome+"%"});

        List<Pedido> pedidos = new ArrayList<>();


        while (c.moveToNext()) {

            Pedido pedido = new Pedido();
            pedido.setIdpedido(c.getInt(0));
            pedido.setIdpessoa(c.getInt(1));
            pedido.setIdvendedor(c.getInt(2));
            pedido.setIdCondicaopag(c.getInt(3));
            pedido.setIdfilial(c.getInt(4));
            pedido.setNome(c.getString(5));
            pedido.setFantasia(c.getString(6));
            pedido.setDatapedido(ConvesorUtil.stringParaDate(c.getString(7)));
            pedido.setTotal(c.getDouble(8));


            pedidos.add(pedido);

        }
        c.close();
        return pedidos;


    }


    public Integer CosultaPedido(){
        Cursor consulta = getReadableDatabase().rawQuery("Select COALESCE(Max(idPedido),0) + 1 AS MAX from Pedido ",
                null);
        Integer id = 0;

        if (consulta != null) {
            try {
                if (consulta.moveToFirst()) {
                    return id = consulta.getInt(0);
                }
            } finally {
                consulta.close();
            }



        }

        return id;


    }

    //insercao e consulta do Pedido

    public void saveItensPedido(ItensPedido itenspedido) {
        ContentValues cv = new ContentValues();
        cv.put("idItens", itenspedido.getIdItens());
        cv.put("idProduto", itenspedido.getIdProduto());
        cv.put("idPedido", itenspedido.getIdpedido());
        cv.put("idVendedor", itenspedido.getIdvendedor());
        cv.put("idPessoa", itenspedido.getIdpessoa());
        cv.put("Desconto", (itenspedido.getDesconto()));
        cv.put("Quantidade",itenspedido.getQuantidade());
        cv.put("vl_unitario", itenspedido.getVlunitario());




        getWritableDatabase().insert("ItensPedido", null, cv);

    }

    public List<ItensPedido> listitens(String idpedido,String idVendedor,String idpessoa) {
        Cursor c = getReadableDatabase().rawQuery("select i.idItens, i.idPedido, i.idProduto, p.Descricao, i.idVendedor, i.idPessoa, i.Desconto, " +
                "      i.Quantidade, i.vl_unitario, ((i.vl_unitario-(i.vl_unitario*i.desconto/100)) * i.Quantidade)as total" +
                "       from ItensPedido i, Produto p" +
                "        where i.idPedido = ? and i.idVendedor = ? and i.idPessoa = ? and p.idProduto = i.idProduto", new String[]{idpedido,idVendedor,idpessoa});

        List<ItensPedido> itens = new ArrayList<>();


        while (c.moveToNext()) {

            ItensPedido iten = new ItensPedido();
            iten.setIdItens(c.getInt(0));
            iten.setIdpedido(c.getInt(1));
            iten.setIdProduto(c.getInt(2));
            iten.setNomeproduto(c.getString(3));
            iten.setIdpessoa(c.getInt(4));
            iten.setIdvendedor(c.getInt(5));
            iten.setDesconto(c.getDouble(6));
            iten.setQuantidade(c.getDouble(7));
            iten.setVlunitario(c.getDouble(8));
            iten.setTotal(c.getDouble(9));


            itens.add(iten);

        }
        c.close();
        return itens;


    }

    public Integer CosultaItensPedido(){
        Cursor consulta = getReadableDatabase().rawQuery("Select COALESCE(Max(idItens),0) + 1 AS MAX from ItensPedido ",
                null);
        Integer id = 0;

        if (consulta != null) {
            try {
                if (consulta.moveToFirst()) {
                    return id = consulta.getInt(0);
                }
            } finally {
                consulta.close();
            }



        }

        return id;


    }

}

