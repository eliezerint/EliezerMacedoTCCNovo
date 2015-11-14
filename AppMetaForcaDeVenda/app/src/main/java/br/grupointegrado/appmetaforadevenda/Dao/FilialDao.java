
package br.grupointegrado.appmetaforadevenda.Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.Filial;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;

/**
 * Created by eli on 05/10/2015.
 */
public class FilialDao extends AppDao {


    public FilialDao(Context context) {
        super(context);
    }

    public void saveFilial(String filial) {
        ContentValues cv = new ContentValues();
        cv.put("Descricao", filial);



        getWritableDatabase().insert("Filial", null, cv);

    }

    public List<Filial> list() {
        Cursor c = getReadableDatabase().rawQuery("Select idFilial, Descricao from Filial", null);

        List<Filial> filials = new ArrayList<>();


        while (c.moveToNext()) {

            Filial filial = new Filial();
            filial.setIdfilial(c.getInt(0));
            filial.setDescricao(c.getString(1));


            filials.add(filial);

        }
        c.close();
        return filials;
    }

    public String nomeFilial(String id){
        Cursor c = getReadableDatabase().rawQuery("Select Descricao from Filial where idFilial = ?",
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
