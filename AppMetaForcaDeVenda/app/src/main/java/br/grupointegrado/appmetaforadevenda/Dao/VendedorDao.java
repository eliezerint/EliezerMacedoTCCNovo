package br.grupointegrado.appmetaforadevenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Vendedor.Vendedor;

/**
 * Created by eli on 06/10/2015.
 */
public class VendedorDao extends AppDao {


    public VendedorDao(Context context) {
        super(context);
    }

    public void saveVendedor(Vendedor vendedor) {
        ContentValues cv = new ContentValues();
        cv.put("idVendedor",vendedor.getIdvendedor() );
        cv.put("Nome", vendedor.getNome());
        cv.put("Max_Desconto", vendedor.getMax_desconto());



        getWritableDatabase().insert("Vendedor", null, cv);

    }
    public List<Vendedor> list() {
        Cursor c = getReadableDatabase().rawQuery("Select idVendedor, nome , Max_Desconto  from Vendedor ",null);

        List<Vendedor> vendedores = new ArrayList<>();


        while (c.moveToNext()) {

            Vendedor vendedor = new Vendedor();
            vendedor.setIdvendedor(c.getInt(0));
            vendedor.setNome(c.getString(1));
            vendedor.setMax_desconto(c.getDouble(2));


            vendedores.add(vendedor);

        }
        c.close();
        return vendedores;
    }
    public List<Vendedor> listId(String id) {
        Cursor c = getReadableDatabase().rawQuery("Select idVendedor, nome , Max_Desconto from Vendedor where idVendedor = ?",new String[]{id});

        List<Vendedor> vendedores = new ArrayList<>();


        while (c.moveToNext()) {

            Vendedor vendedor = new Vendedor();
            vendedor.setIdvendedor(c.getInt(0));
            vendedor.setNome(c.getString(1));
            vendedor.setMax_desconto(c.getDouble(2));


            vendedores.add(vendedor);

        }
        c.close();
        return vendedores;
    }

    public List<Vendedor> listNome(String nome) {
        Cursor c = getReadableDatabase().rawQuery("Select idVendedor, nome, Max_Desconto from Vendedor where nome like ?",new String[]{"%"+nome+"%"});

        List<Vendedor> vendedores = new ArrayList<>();


        while (c.moveToNext()) {

            Vendedor vendedor = new Vendedor();
            vendedor.setIdvendedor(c.getInt(0));
            vendedor.setNome(c.getString(1));
            vendedor.setMax_desconto(c.getDouble(2));


            vendedores.add(vendedor);

        }
        c.close();
        return vendedores;
    }
    public String nomeVendedor(String id){
        Cursor c = getReadableDatabase().rawQuery("Select nome from Vendedor where idVendedor = ?",
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

    public Boolean validaVendedor(String id){
        Cursor c = getReadableDatabase().rawQuery("Select nome from Vendedor where idVendedor = ?",
                new String[]{id});
        if (c.getCount() == 1)
            return true;

        else return false;
    }


    public Integer verificaImportacao(){
        Cursor c = getReadableDatabase().rawQuery("Select * from Vendedor ", null);
        if (c.getCount() > 0)
            return c.getCount();

        else return 0;
    }

    // Consulta Vendedor
    public Vendedor ConsultaVendedorporid(String id){
        Cursor consulta = getReadableDatabase().rawQuery("select idVendedor , Nome , Max_desconto  from Vendedor " +
                        "where idVendedor = ? ",
                new String[]{id});

        if (consulta != null) {
            try {
                if (consulta.moveToFirst()) {
                    return  new Vendedor(consulta.getInt(0),
                            consulta.getString(1),
                            consulta.getDouble(2));
                }
            } finally {
                consulta.close();
            }



        }

        return null;


    }




}
