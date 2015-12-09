
package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Produtos.Tabelapreco;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.TelaConsulta.ConsultaTabelaPrecoActivity;

/**
 * Created by eli on 07/12/2015.
 */
public class AdapterTabelaprecoSpinner  extends ArrayAdapter<Tabelapreco> {

    public AdapterTabelaprecoSpinner(Context context, List<Tabelapreco> lista) {
        super(context, android.R.layout.simple_list_item_1, lista);
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.adapter_spinner_tabelapreco, null);

        TextView tv_destabela = (TextView) v.findViewById(R.id.tv_destabela);
        TextView tv_descricaoTabalaiten = (TextView) v.findViewById(R.id.tv_desItemTabela);
        TextView tv_valor = (TextView) v.findViewById(R.id.tv_valor);



        tv_destabela.setText(getItem(position).getDescricao());
        tv_descricaoTabalaiten.setText(getItem(position).getDescricaotabelaItem());
        tv_valor.setText(String.format("%.2f",getItem(position).getVlUnitario()));


        return v;
    }
}

