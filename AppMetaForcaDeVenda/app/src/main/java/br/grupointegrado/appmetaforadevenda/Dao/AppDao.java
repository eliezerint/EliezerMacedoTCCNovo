package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.Pedido;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 26/07/2015.
 */

public class AppDao extends SQLiteOpenHelper {
    public static final String BD_NAME = "ForcaVenda";
    public static final int BD_Version = 1;
    private Resources res;
    private Pedido pedido;
    private Estado estado;

    public AppDao(Context context) {
        super(context, AppDao.BD_NAME, null, AppDao.BD_Version);

        res = context.getResources();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(res.getString(R.string.SQL_CREATE_PAIS));
        db.execSQL(res.getString(R.string.SQL_CREATE_ESTADO));
        db.execSQL(res.getString(R.string.SQL_CREATE_CIDADE));
        db.execSQL(res.getString(R.string.SQL_CREATE_TELEFONE));
        db.execSQL(res.getString(R.string.SQL_CREATE_PESSOA));
        db.execSQL(res.getString(R.string.SQL_CREATE_FILIAL));
        db.execSQL(res.getString(R.string.SQL_CREATE_CONDPGTO));
        db.execSQL(res.getString(R.string.SQL_CREATE_VENDEDOR));
        db.execSQL(res.getString(R.string.SQL_CREATE_PARAMETRO));
        db.execSQL(res.getString(R.string.SQL_CREATE_GRUPO_PRODUTO));
        db.execSQL(res.getString(R.string.SQL_CREATE_UNIDADE_MEDIDA));
        db.execSQL(res.getString(R.string.SQL_CREATE_PRODUTO));
        db.execSQL(res.getString(R.string.SQL_CREATE_TABELA_PRECO));
        db.execSQL(res.getString(R.string.SQL_CREATE_TABELA_ITEN_PRECO));
        db.execSQL(res.getString(R.string.SQL_CREATE_TABELA_PEDIDO));
        db.execSQL(res.getString(R.string.SQL_CREATE_TABELA_ITENS_PEDIDO));
        db.execSQL(res.getString(R.string.SQL_CREATE_TABELA_IMPORTACAO));
        db.execSQL(res.getString(R.string.SQL_CREATE_TABELA_ULTIMOPEDIDOEXPORTADO));
        db.execSQL(res.getString(R.string.SQL_CREATE_TABELA_EXPORTACAO));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Pais");
        db.execSQL("DROP TABLE IF EXISTS " + "Estado");
        db.execSQL("DROP TABLE IF EXISTS " + "Cidade");
        db.execSQL("DROP TABLE IF EXISTS " + "Telefone");
        db.execSQL("DROP TABLE IF EXISTS " + "Pessoa");
        db.execSQL("DROP TABLE IF EXISTS " + "Filial");
        db.execSQL("DROP TABLE IF EXISTS " + "Condicao_pagamento");
        db.execSQL("DROP TABLE IF EXISTS " + "Vendedor");
        db.execSQL("DROP TABLE IF EXISTS " + "Parametro");
        db.execSQL("DROP TABLE IF EXISTS " + "GrupoProduto");
        db.execSQL("DROP TABLE IF EXISTS " + "UnidadeMedida");
        db.execSQL("DROP TABLE IF EXISTS " + "Produto");
        db.execSQL("DROP TABLE IF EXISTS " + "TabelaPreco");
        db.execSQL("DROP TABLE IF EXISTS " + "TabelaItenPreco");
        db.execSQL("DROP TABLE IF EXISTS " + "Pedido");
        db.execSQL("DROP TABLE IF EXISTS " + "ItensPedido");
        db.execSQL("DROP TABLE IF EXISTS " + "Importacao");
        db.execSQL("DROP TABLE IF EXISTS " + "Exportacao");
        db.execSQL("DROP TABLE IF EXISTS " + "UltimaPedidoExportado");

        onCreate(db);

    }


    public void deletaRegistroBanco() {
        getWritableDatabase().execSQL("DELETE FROM " + "Pais");
        getWritableDatabase().execSQL("DELETE FROM " + "Estado");
        getWritableDatabase().execSQL("DELETE FROM " + "Cidade");
        getWritableDatabase().execSQL("DELETE FROM " + "Telefone");
        getWritableDatabase().execSQL("DELETE FROM " + "Pessoa");
       /* getWritableDatabase().execSQL("DELETE FROM " + "Filial");
        getWritableDatabase().execSQL("DELETE FROM " + "Condicao_pagamento");
        getWritableDatabase().execSQL("DELETE FROM " + "Vendedor");
        getWritableDatabase().execSQL("DELETE FROM " + "Parametro");*/
        getWritableDatabase().execSQL("DELETE FROM " + "GrupoProduto");
        getWritableDatabase().execSQL("DELETE FROM " + "UnidadeMedida");
        getWritableDatabase().execSQL("DELETE FROM " + "Produto");
        /*getWritableDatabase().execSQL("DELETE FROM " + "TabelaPreco");
        getWritableDatabase().execSQL("DELETE FROM " + "TabelaItenPreco");
        getWritableDatabase().execSQL("DELETE FROM " + "Pedido");
        getWritableDatabase().execSQL("DELETE FROM " + "ItensPedido");
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + "Importacao");
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + "Exportacao");
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + "UltimaPedidoExportado");*/

    }



}


