package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Produtos.Grupos_Produtos;
import br.grupointegrado.appmetaforadevenda.Produtos.Produtos;
import br.grupointegrado.appmetaforadevenda.Produtos.TabelaItenPreco;
import br.grupointegrado.appmetaforadevenda.Produtos.Tabelapreco;
import br.grupointegrado.appmetaforadevenda.Produtos.UnidadeMedida;

/**
 * Created by eli on 12/10/2015.
 */
public class ProdutoDao extends AppDao {
    public ProdutoDao(Context context) {
        super(context);
    }





    public void saveProduto(String idprodutogrupo, String idunidade, String produto){
        ContentValues cv = new ContentValues();
        cv.put("idGrupo_produto", idprodutogrupo);
        cv.put("idUnidadeMedida", idunidade);
        cv.put("Descricao", produto);


        getWritableDatabase().insert("Produto", null, cv);

    }

    public Produtos cosultaIdproduto(String idproduto) {
        Cursor c = getReadableDatabase().rawQuery("Select idProduto, grp.idGrupo_produto, um.idUnidadeMedida  ,p.Descricao , um.Descricao, " +
                "grp.Descricao from Produto p, UnidadeMedida um , GrupoProduto grp " +
                "where um.idUnidadeMedida = p.idUnidadeMedida and grp.idGrupo_produto = p.idGrupo_produto" +
                " and idProduto = ? ", new String[]{idproduto});
        Produtos produto = new Produtos();

        if (c != null) {
            try {
                if (c.moveToFirst()) {

                    produto.setIdproduto(c.getInt(0));
                    produto.setIdgrupopoduto(c.getInt(1));
                    produto.setIdUnidademedida(c.getInt(2));
                    produto.setDescricao(c.getString(3));
                    produto.setDescricaoUnidademedida(c.getString(4));
                    produto.setDescricaoGrupoProduto(c.getString(5));

                    return produto;

                }
            } finally {
                c.close();
            }

        }

        return produto;

    }

    public List<Produtos> list() {
        Cursor c = getReadableDatabase().rawQuery("Select idProduto, grp.idGrupo_produto, um.idUnidadeMedida  ,p.Descricao ," +
                " um.Descricao, " +
                "   grp.Descricao from Produto p, UnidadeMedida um , GrupoProduto grp " +
                "    where um.idUnidadeMedida = p.idUnidadeMedida and grp.idGrupo_produto = p.idGrupo_produto" , null);

        List<Produtos> produtos = new ArrayList<>();


        while (c.moveToNext()) {

            Produtos produto = new Produtos();
            produto.setIdproduto(c.getInt(0));
            produto.setIdgrupopoduto(c.getInt(1));
            produto.setIdUnidademedida(c.getInt(2));
            produto.setDescricao(c.getString(3));
            produto.setDescricaoUnidademedida(c.getString(4));
            produto.setDescricaoGrupoProduto(c.getString(5));


            produtos.add(produto);

        }
        c.close();
        return produtos;

    }

    public List<Produtos> listIdproduto(String idproduto) {
        Cursor c = getReadableDatabase().rawQuery("Select idProduto, grp.idGrupo_produto, um.idUnidadeMedida  ,p.Descricao , um.Descricao, " +
                "grp.Descricao from Produto p, UnidadeMedida um , GrupoProduto grp " +
                "where um.idUnidadeMedida = p.idUnidadeMedida and grp.idGrupo_produto = p.idGrupo_produto" +
                " and idProduto = ? ", new String[]{idproduto});

        List<Produtos> produtos = new ArrayList<>();


        while (c.moveToNext()) {

            Produtos produto = new Produtos();
            produto.setIdproduto(c.getInt(0));
            produto.setIdgrupopoduto(c.getInt(1));
            produto.setIdUnidademedida(c.getInt(2));
            produto.setDescricao(c.getString(3));
            produto.setDescricaoUnidademedida(c.getString(4));
            produto.setDescricaoGrupoProduto(c.getString(5));


            produtos.add(produto);

        }
        c.close();
        return produtos;

    }
    public List<Produtos> listNomeProduto(String nome) {
        Cursor c = getReadableDatabase().rawQuery("Select idProduto, grp.idGrupo_produto, um.idUnidadeMedida  ,p.Descricao , um.Sigla, " +
                "grp.Descricao " +
                "from Produto p, UnidadeMedida um , GrupoProduto grp " +
                "where um.idUnidadeMedida = p.idUnidadeMedida and grp.idGrupo_produto = p.idGrupo_produto " +
                "and p.descricao  like ?", new String[]{"%"+nome+"%"});

        List<Produtos> produtos = new ArrayList<>();


        while (c.moveToNext()) {

            Produtos produto = new Produtos();
            produto.setIdproduto(c.getInt(0));
            produto.setIdgrupopoduto(c.getInt(1));
            produto.setIdUnidademedida(c.getInt(2));
            produto.setDescricao(c.getString(3));
            produto.setDescricaoUnidademedida(c.getString(4));
            produto.setDescricaoGrupoProduto(c.getString(5));


            produtos.add(produto);

        }
        c.close();
        return produtos;

    }

    //Tabela de Grupos de Produto
    public void saveGrupoProduto(String grupos_produtos){
        ContentValues cv = new ContentValues();
        cv.put("Descricao", grupos_produtos);

        getWritableDatabase().insert("GrupoProduto", null, cv);

    }

    public List<Grupos_Produtos> listGruposProduto() {
        Cursor c = getReadableDatabase().rawQuery("Select idProduto, Descricao from GrupoProduto ", null);

        List<Grupos_Produtos> grupos_produtos = new ArrayList<>();


        while (c.moveToNext()) {

            Grupos_Produtos grupos_produto = new Grupos_Produtos();
            grupos_produto.setIdproduto(c.getInt(0));
            grupos_produto.setDescricao(c.getString(1));

            grupos_produtos.add(grupos_produto);

        }
        c.close();
        return grupos_produtos;

    }

    //Tabela de Unidade de Medida
    public void saveUnidadeMedida(String nomedaunidademedida, String unidademedida){
        ContentValues cv = new ContentValues();
        cv.put("Descricao", nomedaunidademedida);
        cv.put("Sigla", unidademedida);

        getWritableDatabase().insert("UnidadeMedida", null, cv);

    }

    public List<UnidadeMedida> listUnidadeMedida() {
        Cursor c = getReadableDatabase().rawQuery("Select idUnidadeMedida, Descricao, Sigla, from UnidadeMedida ", null);

        List<UnidadeMedida> unidademedidas = new ArrayList<>();


        while (c.moveToNext()) {

            UnidadeMedida unidademedida = new UnidadeMedida();
            unidademedida.setIdunidademedida(c.getInt(0));
            unidademedida.setDescricao(c.getString(1));
            unidademedida.setSigla(c.getString(2));

            unidademedidas.add(unidademedida);

        }
        c.close();
        return unidademedidas;

    }

    //Tabela de Preço
    public void savePreco(String idproduto, String tabela_preco ){
        ContentValues cv = new ContentValues();
        cv.put("idProduto", idproduto);
        cv.put("descricao", tabela_preco);

        getWritableDatabase().insert("TabelaPreco", null, cv);

    }


    public List<Tabelapreco> listPrecoVEnda(String id) {
        Cursor c = getReadableDatabase().rawQuery("Select idTabelapreco, idProduto, descricao" +
                " from TabelaPreco where  idProduto = ? ", new String []{id});

        List<Tabelapreco> tabela_precos = new ArrayList<>();


        while (c.moveToNext()) {

            Tabelapreco tabela_preco = new Tabelapreco();
            tabela_preco.setIdTabelapreco(c.getInt(0));
            tabela_preco.setIdProduto(c.getInt(1));
            tabela_preco.setDescricao(c.getString(2));

            tabela_precos.add(tabela_preco);

        }
        c.close();
        return tabela_precos;

    }

    //Tabela de Preço iten
    public void saveItemtabelaPreco(String idtabela_preco, String nometabela, double vlunitario){
        ContentValues cv = new ContentValues();
        cv.put("idTabelapreco", idtabela_preco);
        cv.put("descricao", nometabela);
        cv.put("vlunitario", vlunitario );

        getWritableDatabase().insert("TabelaItenPreco", null, cv);

    }


    public List<TabelaItenPreco> listPrecoVEndaIten(String id) {
        Cursor c = getReadableDatabase().rawQuery("Select idTabelaItenpreco, idTabelapreco, descricao, vlunitario" +
                " from TabelaItenPreco where idTabelapreco = ? ", new String []{id});

        List<TabelaItenPreco> tabela_precos = new ArrayList<>();


        while (c.moveToNext()) {

            TabelaItenPreco  itentabela_preco = new TabelaItenPreco();
            itentabela_preco.setIdtabelaItenpreco(c.getInt(0));
            itentabela_preco.setIdtabelapreco(c.getInt(1));
            itentabela_preco.setDescricao(c.getString(2));
            itentabela_preco.setVlunitario(Double.valueOf(c.getString(3)));

            tabela_precos.add(itentabela_preco);

        }
        c.close();

        return tabela_precos;

    }





}
