package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.CondicaoPagamento;

/**
 * Created by eli on 06/10/2015.
 */
public class CondicaoPgtoDao extends  AppDao {


    public CondicaoPgtoDao(Context context) {
        super(context);
    }

    public void saveCondPgto(String condpgto,Double qtde,Integer intervalos) {
        ContentValues cv = new ContentValues();
        cv.put("Descricao", condpgto);
        cv.put("Nr_parcelas", qtde);
        cv.put("Intervalos", intervalos);


        getWritableDatabase().insert("Condicao_pagamento", null, cv);

    }

    public List<CondicaoPagamento> list() {
        Cursor c = getReadableDatabase().rawQuery("Select idcondicaopagamento, Descricao, " +
                "Nr_parcelas, Intervalos from Condicao_pagamento ", null);

        List<CondicaoPagamento> condpgtos = new ArrayList<>();


        while (c.moveToNext()) {

            CondicaoPagamento condpgto = new CondicaoPagamento();
            condpgto.setIdcodicaopagamento(c.getInt(0));
            condpgto.setDescricao(c.getString(1));
            condpgto.setQuantidade(c.getDouble(2));
            condpgto.setIntervalo(c.getInt(3));



            condpgtos.add(condpgto);

        }
        c.close();
        return condpgtos;
    }

    public String nomeCondPgto(String id){
        Cursor c = getReadableDatabase().rawQuery("Select Descricao from Condicao_pagamento where idcondicaopagamento = ?",
                new String[]{id});
        String nome = " ";
        if (c != null){
            try{
                c.moveToFirst();
                return c.getString(0);

            }finally {
                c.close();
            }

        }


        return nome;
    }
}
