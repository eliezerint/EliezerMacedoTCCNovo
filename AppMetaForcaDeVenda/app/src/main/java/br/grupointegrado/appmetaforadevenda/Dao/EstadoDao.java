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

    public void saveEstado(String idestado, String descricao,String pais) {
        ContentValues cv = new ContentValues();
        cv.put("id_estado", idestado);
        cv.put("Descricao", descricao);
        cv.put("idPais", pais);


        getWritableDatabase().insert("estado", null, cv);

    }

    public List<Estado> list(String Pais) {
        Cursor c = getReadableDatabase().rawQuery("Select id_estado, idPais ,Descricao from estado where " +
                "idPais like ?",new String[]{Pais});



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
}
