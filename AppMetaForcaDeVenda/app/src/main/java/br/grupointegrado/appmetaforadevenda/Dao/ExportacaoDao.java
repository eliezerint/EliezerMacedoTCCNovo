package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.grupointegrado.appmetaforadevenda.Exportacao.Exportacao;
import br.grupointegrado.appmetaforadevenda.Exportacao.UltimoPedidoExportacao;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;

/**
 * Created by eli on 06/12/2015.
 */
public class ExportacaoDao extends AppDao {

    public ExportacaoDao(Context context) {
        super(context);
    }


    public void saveExportacao(Exportacao exportacao) {
        ContentValues cv = new ContentValues();
        cv.put("idUltimaPedidoExportado",exportacao.getIdUltimaExportacao());
        cv.put("DataExportacao", exportacao.getDataultimaExportacao().toString());
        cv.put("QuantidadeRegistro", exportacao.getQuantidadeRegistro());



        getWritableDatabase().insert("Exportacao", null, cv);

    }

    public void UltimoPedidoExportado(UltimoPedidoExportacao ultimo) {
        ContentValues cv = new ContentValues();
        cv.put("idPedido",ultimo.getIdpedido());
        cv.put("idVendedor",ultimo.getIdVendedor());
        cv.put("idPessoa",ultimo.getIdPessoa());



        getWritableDatabase().insert("UltimoPedidoExportado", null, cv);

    }

    public Integer idUltimopedido(){
        Cursor consulta = getWritableDatabase().rawQuery("select COALESCE(Max(idUltimaPedidoExportado),0) AS MAX from UltimoPedidoExportado ",null);
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


}
