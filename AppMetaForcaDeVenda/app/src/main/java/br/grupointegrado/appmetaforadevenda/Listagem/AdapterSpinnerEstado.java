package br.grupointegrado.appmetaforadevenda.Listagem;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 23/11/2015.
 */
public class AdapterSpinnerEstado extends ArrayAdapter<Estado>{

    public AdapterSpinnerEstado(Context context, List<Estado> lista) {
        super(context, android.R.layout.simple_list_item_1, lista);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.adapter_spinner_estado, null);

        TextView tv_Estado = (TextView) v.findViewById(R.id.tv_Estado);
        TextView tv_Sigla = (TextView) v.findViewById(R.id.tv_Sigla);


        tv_Estado.setText(getItem(position).getDescricao().toString());
        tv_Sigla.setText(getItem(position).getIdestado().toString() );


        return v;
    }

}