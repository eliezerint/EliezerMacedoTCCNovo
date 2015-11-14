package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;

/**
 * Created by eli on 06/09/2015.
 */
public class CidadeDao extends AppDao {


    public CidadeDao(Context context) {
        super(context);
    }
   // teste de inserção e consulta de Cidade

    public void saveCidade(Cidade cidade) {
        ContentValues cv = new ContentValues();
        cv.put("Pais", cidade.getPais());
        cv.put("id_estado", cidade.getIdestado());
        cv.put("Descricao", cidade.getDescricao());
        cv.put("IBGE", cidade.getIbge());


        getWritableDatabase().insert("Cidade", null, cv);

    }

    public void Update(Cidade cidade) {
        ContentValues cv = new ContentValues();
        cv.put("Descricao", cidade.getDescricao());
        cv.put("IBGE", cidade.getIbge());


        getWritableDatabase().update("Cidade", cv, "id_Cidade = ?", new String[]{cidade.getIdcidade().toString()});



    }

    //consulta de cidade
    public List<Cidade> list() {
        Cursor c = getReadableDatabase().rawQuery("Select id_Cidade, Pais,id_estado ,descricao,IBGE from Cidade ",null);

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setId(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;
    }
    public List<Cidade> list(String Pais,String estado) {
        Cursor c = getReadableDatabase().rawQuery("Select id_cidade, Pais,id_estado ,descricao,IBGE from Cidade where" +
                " Pais like ? and id_estado like ? ",new String[]{Pais,estado});

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setId(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;

    }public List<Cidade> list(String nomecidade) {
        Cursor c = getReadableDatabase().rawQuery("Select id_cidade, Pais,id_estado ,descricao,IBGE from Cidade where" +
                " descricao like ? ",new String[]{"%"+nomecidade+"%"});

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setId(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;
    }

    public List<Cidade> listCodIbge(String CodIbge) {
        Cursor c = getReadableDatabase().rawQuery("Select id_cidade, Pais, id_estado , descricao, IBGE from Cidade where" +
                " IBGE like ? ",new String[]{CodIbge});

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setId(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;
    }



    public void delete(Integer id) {

        getWritableDatabase().delete("Cidade", "id_Cidade = ?", new String[]{id.toString()});
    }
    public Integer ConsultaCidade(String nome){
        Cursor consulta = getReadableDatabase().rawQuery("Select id_Cidade from Cidade " +
                        "where Descricao = ? ",
                new String[]{nome});
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
    public String ConsultaCidadeporid(String id){
        Cursor consulta = getReadableDatabase().rawQuery("Select Descricao from Cidade " +
                        "where id_Cidade = ? ",
                new String[]{id});
        String nome = "" ;

        if (consulta != null) {
            try {
                if (consulta.moveToFirst()) {
                    return nome = consulta.getString(0);
                }
            } finally {
                consulta.close();
            }



        }

        return nome;


    }
}
