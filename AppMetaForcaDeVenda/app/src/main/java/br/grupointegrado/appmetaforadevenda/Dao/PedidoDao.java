package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
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
        cv.put("idcondicaopagamento", pedido.getIdCondicaopag());
        cv.put("idFilial", pedido.getIdfilial());
        cv.put("Data_pedido", ConvesorUtil.dateParaString(pedido.getDatapedido()));
        cv.put("Valor_total", pedido.getTotal());


        getWritableDatabase().update("Pedido", cv, "idPedido = ? and idPessoa = ? and idVendedor = ? ",
                new String[]{pedido.getIdpedido().toString(), pedido.getIdpessoa().toString(),
                        pedido.getIdvendedor().toString()});



    }



    public String maiorData(Pedido pedido) {
        Cursor consulta = getReadableDatabase().rawQuery("select max(p.Data_pedido) from Pedido p", null);
        String data = null;
        if (consulta != null) {
            try {
                if (consulta.moveToFirst()) {
                    return data = (consulta.getString(0));
                }
            } finally {
                consulta.close();
            }



        }

        return data;



    }

    public void delete(Integer id) {

        getWritableDatabase().delete("Pedido", "idPedido = ?", new String[]{id.toString()});
    }

    public List<Pedido> list() {
        Cursor c = getReadableDatabase().rawQuery("Select  p.idPedido,   p.idPessoa, p.idVendedor ," +
                " p.idcondicaopagamento , p.idFilial , pe.Razao_socialNome , c.descricao,  " +
                "  p.Data_pedido , p.Valor_total " +
                "  From Pedido p " +
                "  inner join Pessoa pe on (p.idPessoa = pe.idPessoa) " +
                "  inner join Cidade c on (pe.id_Cidade = c.id_Cidade)", null);

        List<Pedido> pedidos = new ArrayList<>();


        while (c.moveToNext()) {

            Pedido pedido = new Pedido();
            pedido.setIdpedido(c.getInt(0));
            pedido.setIdpessoa(c.getInt(1));
            pedido.setIdvendedor(c.getInt(2));
            pedido.setIdCondicaopag(c.getInt(3));
            pedido.setIdfilial(c.getInt(4));
            pedido.setNome(c.getString(5));
            pedido.setCidade(c.getString(6));
            pedido.setDatapedido(ConvesorUtil.stringParaDate(c.getString(7)));
            pedido.setTotal(c.getDouble(8));


            pedidos.add(pedido);

        }
        c.close();
        return pedidos;


    }

    public List<Pedido> listCodigo(String id) {
        Cursor c = getReadableDatabase().rawQuery("Select  p.idPedido,   p.idPessoa, p.idVendedor ," +
                " p.idcondicaopagamento , p.idFilial , pe.Razao_socialNome , c.descricao,  " +
                "  p.Data_pedido , p.Valor_total " +
                "  From Pedido p " +
                "  inner join Pessoa pe on (p.idPessoa = pe.idPessoa) " +
                "  inner join Cidade c on (pe.id_Cidade = c.id_Cidade)" +
                "  where  p.idPedido = ? ", new String[]{id});

        List<Pedido> pedidos = new ArrayList<>();


        while (c.moveToNext()) {

            Pedido pedido = new Pedido();
            pedido.setIdpedido(c.getInt(0));
            pedido.setIdpessoa(c.getInt(1));
            pedido.setIdvendedor(c.getInt(2));
            pedido.setIdCondicaopag(c.getInt(3));
            pedido.setIdfilial(c.getInt(4));
            pedido.setNome(c.getString(5));
            pedido.setCidade(c.getString(6));
            pedido.setDatapedido(ConvesorUtil.stringParaDate(c.getString(7)));
            pedido.setTotal(c.getDouble(8));


            pedidos.add(pedido);

        }
        c.close();
        return pedidos;


    }

    public List<Pedido> listCpfCnpj(String CpfCnpj) {
        Cursor c = getReadableDatabase().rawQuery("Select  p.idPedido, " +
                "   p.idPessoa, p.idVendedor , p.idcondicaopagamento , p.idFilial ,pe.Razao_socialNome ,c.descricao," +
                "p.Data_pedido , p.Valor_total " +
                "  From Pedido p " +
                "  inner join Pessoa pe on (p.idPessoa = pe.idPessoa)" +
                "  inner join Cidade c on (pe.id_Cidade = c.id_Cidade)" +
                "  and  pe.CNPJCPF = ? ", new String[]{CpfCnpj});

        List<Pedido> pedidos = new ArrayList<>();


        while (c.moveToNext()) {

            Pedido pedido = new Pedido();
            pedido.setIdpedido(c.getInt(0));
            pedido.setIdpessoa(c.getInt(1));
            pedido.setIdvendedor(c.getInt(2));
            pedido.setIdCondicaopag(c.getInt(3));
            pedido.setIdfilial(c.getInt(4));
            pedido.setNome(c.getString(5));
            pedido.setCidade(c.getString(6));
            pedido.setDatapedido(ConvesorUtil.stringParaDate(c.getString(7)));
            pedido.setTotal(c.getDouble(8));


            pedidos.add(pedido);

        }
        c.close();
        return pedidos;


    }
    public List<Pedido> list(String nome) {
        Cursor c = getReadableDatabase().rawQuery("Select  p.idPedido, " +
                "  p.idPessoa, p.idVendedor , p.idcondicaopagamento , p.idFilial ,pe.Razao_socialNome , c.descricao, " +
                "  p.Data_pedido , p.Valor_total " +
                "  From Pedido p " +
                "  inner join Pessoa pe on (p.idPessoa = pe.idPessoa) " +
                "  inner join Cidade c on (pe.id_Cidade = c.id_Cidade) " +
                "  and pe.Razao_socialNome like ?", new String[]{"%"+nome+"%"});

        List<Pedido> pedidos = new ArrayList<>();


        while (c.moveToNext()) {

            Pedido pedido = new Pedido();
            pedido.setIdpedido(c.getInt(0));
            pedido.setIdpessoa(c.getInt(1));
            pedido.setIdvendedor(c.getInt(2));
            pedido.setIdCondicaopag(c.getInt(3));
            pedido.setIdfilial(c.getInt(4));
            pedido.setNome(c.getString(5));
            pedido.setCidade(c.getString(6));
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
        cv.put("idPedido", itenspedido.getIdpedido());
        cv.put("idVendedor", itenspedido.getIdvendedor());
        cv.put("idPessoa", itenspedido.getIdpessoa());
        cv.put("idProduto", itenspedido.getIdProduto());
        cv.put("Desconto", (itenspedido.getDesconto()));
        cv.put("Quantidade",itenspedido.getQuantidade());
        cv.put("vl_unitario", itenspedido.getVlunitario());




        getWritableDatabase().insert("ItensPedido", null, cv);

    }

    public void updateItensPedido(ItensPedido itenspedido) {
        ContentValues cv = new ContentValues();
        cv.put("idProduto", itenspedido.getIdProduto());
        cv.put("Desconto", (itenspedido.getDesconto()));
        cv.put("Quantidade",itenspedido.getQuantidade());
        cv.put("vl_unitario", itenspedido.getVlunitario());




        getWritableDatabase().update("ItensPedido", cv, " idItens = ? and idPedido = ? and idPessoa = ? and idVendedor = ? ",
                new String[]{itenspedido.getIdpedido().toString(), itenspedido.getIdpessoa().toString(),
                        itenspedido.getIdvendedor().toString()});

    }



    public void deleteItens(Pedido pedido) {

        getWritableDatabase().delete("ItensPedido", "idPedido = ?  and idVendedor = ? and idPessoa = ?",
                new String[]{pedido.getIdpedido().toString(), pedido.getIdvendedor().toString()
                        , pedido.getIdpessoa().toString()});
    }

    public List<ItensPedido> listitens(String idpedido,String idVendedor,String idpessoa) {
        Cursor c = getReadableDatabase().rawQuery("select  i.idPedido, i.idProduto, p.Descricao, i.idVendedor, i.idPessoa, i.Desconto, " +
                "      i.Quantidade, i.vl_unitario, ((i.vl_unitario-(i.vl_unitario*i.desconto/100)) * i.Quantidade)as total" +
                "       from ItensPedido i, Produto p" +
                "        where i.idPedido = ? and i.idVendedor = ? and i.idPessoa = ? and p.idProduto = i.idProduto", new String[]{idpedido,idVendedor,idpessoa});

        List<ItensPedido> itens = new ArrayList<>();


        while (c.moveToNext()) {

            ItensPedido iten = new ItensPedido();
            iten.setIdpedido(c.getInt(0));
            iten.setIdProduto(c.getInt(1));
            iten.setNomeproduto(c.getString(2));
            iten.setIdpessoa(c.getInt(3));
            iten.setIdvendedor(c.getInt(4));
            iten.setDesconto(c.getDouble(5));
            iten.setQuantidade(c.getDouble(6));
            iten.setVlunitario(c.getDouble(7));
            iten.setTotal(c.getDouble(8));


            itens.add(iten);

        }
        c.close();
        return itens;


    }



    public Integer exitePedidoDoCliente(String idcliente){
        Cursor consulta = getReadableDatabase().rawQuery("select idPessoa from Pedido " +
                        "where idPessoa = ? ", new String[]{idcliente});
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

