package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;

/**
 * Created by eli on 06/09/2015.
 */
public class EstadoDao extends AppDao {

    public EstadoDao(Context context) {
        super(context);
    }

    //    teste de inserção e consulta de estado

    public void saveEstado(Estado estado) {
        ContentValues cv = new ContentValues();
        cv.put("id_estado", estado.getIdestado());
        cv.put("Descricao", estado.getDescricao());
        cv.put("idPais", estado.getIdpais());


        getWritableDatabase().insert("estado", null, cv);

    }

    public List<Estado> list(String Pais) {
        Cursor c = getReadableDatabase().rawQuery("Select e.id_estado,e.idPais, e.Descricao from estado e " +
                " inner join Pais p on (p.idPais = e.idPais) " +
                " where p.Nome = ? ",new String[]{Pais});



        List<Estado> estados = new ArrayList<>();


        while (c.moveToNext()) {

            Estado estado = new Estado();
            estado.setIdestado(c.getString(0));
            estado.setDescricao(c.getString(2));
           /* estado.setIdpais(c.getString(1));

*/
            estados.add(estado);

        }
        c.close();
        return estados;
    }

    public Estado retornaEstado( String idestado){
        Cursor c = getReadableDatabase().rawQuery("Select e.id_estado,e.idPais, e.Descricao from estado e " +
                " inner join Pais p on (p.idPais = e.idPais) " +
                " where e.id_estado = ? ",new String[]{idestado});



       Estado estado = new Estado();


        if (c.moveToFirst()) {


            estado.setIdestado(c.getString(0));
            estado.setIdpais(c.getString(1));
            estado.setDescricao(c.getString(2));






        }
        c.close();
        return estado;
    }

}
