package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pessoa.Pais;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 23/11/2015.
 */
public class AdapterSpinnerPais extends ArrayAdapter<Pais>{
    public AdapterSpinnerPais(Context context, List<Pais> lista) {
        super(context, android.R.layout.simple_list_item_1, lista);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.adapter_spinner_pais, null);

        TextView tv_Pais = (TextView) v.findViewById(R.id.tv_Pais);
        TextView tv_SiglaPais = (TextView) v.findViewById(R.id.tv_SiglaPais);


        tv_Pais.setText(getItem(position).getNome().toString());
        tv_SiglaPais.setText(getItem(position).getIdpais().toString() );


        return v;
    }
}
