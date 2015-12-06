package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;

import br.grupointegrado.appmetaforadevenda.Importacao.Importacao;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;

/**
 * Created by eli on 06/12/2015.
 */
public class ImportacaoDao extends AppDao {


    public ImportacaoDao(Context context) {
        super(context);

    }

    public void saveImportacao(Importacao importacao) {
        ContentValues cv = new ContentValues();
        cv.put("DataImportacao", ConvesorUtil.dateParaString(importacao.getDataimportacao()));
        cv.put("QuantidadeRegistro", importacao.getQuantidade());



        getWritableDatabase().insert("Importacao", null, cv);

    }

}
