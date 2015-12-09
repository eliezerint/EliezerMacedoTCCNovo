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
        cv.put("id_Cidade", cidade.getIdcidade());
        cv.put("id_estado", cidade.getIdestado());
        cv.put("Descricao", cidade.getDescricao());
        cv.put("IBGE", cidade.getIbge());
        cv.put("flag", cidade.getFlag());


        getWritableDatabase().insert("Cidade", null, cv);

    }

    public Integer CodigodaCidade(){
        Cursor consulta = getReadableDatabase().rawQuery("Select COALESCE(Max(id_Cidade),0) + 1 AS MAX from Cidade ",
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


    public void Update(Cidade cidade) {
        ContentValues cv = new ContentValues();
        cv.put("Descricao", cidade.getDescricao());
        cv.put("IBGE", cidade.getIbge());


        getWritableDatabase().update("Cidade", cv, "id_Cidade = ?", new String[]{cidade.getIdcidade().toString()});



    }

    //consulta de cidade
    public List<Cidade> list() {
        Cursor c = getReadableDatabase().rawQuery("Select c.id_Cidade, p.Nome as pais, c.id_estado, " +
                " c.descricao,c.IBGE  from Cidade c " +
                " inner join estado es  on(es.id_estado = c.id_estado ) " +
                " inner join Pais p on (p.idPais = es.idPais)",null);

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setIdcidade(c.getInt(0));
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
        Cursor c = getReadableDatabase().rawQuery("Select c.id_Cidade, p.Nome as pais,c.id_estado, " +
                "  c.descricao,c.IBGE  from Cidade c " +
                "  inner join estado es  on(es.id_estado = c.id_estado ) " +
                "  inner join Pais p on (p.idPais = es.idPais) where " +
                "  pais like ? and c.id_estado like ? ",new String[]{Pais,estado});

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setIdcidade(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;

    }

    public List<Cidade> listCod(String idcidade) {
        Cursor c = getReadableDatabase().rawQuery("Select c.id_Cidade, p.Nome as pais, c.id_estado, " +
                " c.descricao,c.IBGE  from Cidade c " +
                " inner join estado es  on(es.id_estado = c.id_estado )" +
                " inner join Pais p on (p.idPais = es.idPais) where" +
                " c.id_Cidade = ? ",new String[]{idcidade});

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setIdcidade(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;
    }

    public List<Cidade> listExportacaoPDF() {
        Cursor c = getReadableDatabase().rawQuery("Select c.id_Cidade, p.Nome as pais, c.id_estado, " +
                "  c.descricao,c.IBGE  from Cidade c " +
                "  inner join estado es  on(es.id_estado = c.id_estado ) " +
                "  inner join Pais p on (p.idPais = es.idPais) " +
                "   where c.flag = 'F' " +
                "   ",null);

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setIdcidade(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;
    }





    public List<Cidade> list(String nomecidade) {
        Cursor c = getReadableDatabase().rawQuery("Select c.id_Cidade, p.Nome as pais, c.id_estado, " +
                "  c.descricao,c.IBGE  from Cidade c " +
                "  inner join estado es  on(es.id_estado = c.id_estado ) " +
                "  inner join Pais p on (p.idPais = es.idPais) where" +
                "  c.descricao like ? ",new String[]{"%"+nomecidade+"%"});

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setIdcidade(c.getInt(0));
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
        Cursor c = getReadableDatabase().rawQuery("Select c.id_Cidade, p.Nome as pais, c.id_estado, " +
                " c.descricao,c.IBGE  from Cidade c " +
                " inner join estado es  on(es.id_estado = c.id_estado )" +
                " inner join Pais p on (p.idPais = es.idPais) where" +
                " c.IBGE like ? ",new String[]{CodIbge});

        List<Cidade> cidades = new ArrayList<>();


        while (c.moveToNext()) {

            Cidade cidade = new Cidade();
            cidade.setIdcidade(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));

            cidades.add(cidade);

        }
        c.close();
        return cidades;
    }


    public Cidade retornaCidade(String idcidade) {
        Cursor c = getReadableDatabase().rawQuery("Select c.id_Cidade, p.Nome as pais, c.id_estado, " +
                " c.descricao,c.IBGE  from Cidade c " +
                " inner join estado es  on(es.id_estado = c.id_estado ) " +
                " inner join Pais p on (p.idPais = es.idPais) where c.id_Cidade = ? ", new String[]{idcidade});

        Cidade cidade = new Cidade();


        if (c.moveToFirst()) {


            cidade.setIdcidade(c.getInt(0));
            cidade.setPais(c.getString(1));
            cidade.setIdestado(c.getString(2));
            cidade.setDescricao(c.getString(3));
            cidade.setIbge(c.getString(4));


        }
        c.close();
        return cidade;
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
    public String cidadeflag(String idcidade){
        Cursor consulta = getReadableDatabase().rawQuery("Select flag from Cidade " +
                        "where id_Cidade = ? ",
                new String[]{idcidade});
        String flag = "" ;

        if (consulta != null) {
            try {
                if (consulta.moveToFirst()) {
                    return flag = consulta.getString(0);
                }
            } finally {
                consulta.close();
            }



        }

        return flag;


    }
}
