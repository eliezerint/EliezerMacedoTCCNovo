package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pessoa.Pais;

/**
 * Created by eli on 06/09/2015.
 */
public class PaisDao extends AppDao {
    public PaisDao(Context context) {
        super(context);

    }

    public void savePais(Pais pais) {
        ContentValues cv = new ContentValues();
        cv.put("idPais", pais.getIdpais());
        cv.put("Nome", pais.getNome());


        getWritableDatabase().insert("Pais", null, cv);

    }

    public List<Pais> list() {
        Cursor c = getReadableDatabase().rawQuery("Select  idPais ,Nome from Pais ",null);

        List<Pais> paises = new ArrayList<>();


        while (c.moveToNext()) {

            Pais pais = new Pais();
            pais.setIdpais(c.getString(0));
            pais.setNome(c.getString(1));
           /* estado.setIdpais(c.getString(1));

*/
            paises.add(pais);

        }
        c.close();
        return paises;
    }

    public Pais retornaPais(String id) {
        Cursor c = getReadableDatabase().rawQuery("Select  idPais ,Nome from Pais  where idPais = ?" +
                " ",new String[]{id});

        if (c.moveToFirst()){
            return new Pais (
            c.getString(0),
            c.getString(1));

        }
        c.close();
        return  null;
    }



}
