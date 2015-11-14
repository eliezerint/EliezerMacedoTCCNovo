package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Fragments.TelefoneFragment;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Util.Mask;

/**
 * Created by eli on 22/09/2015.
 */
public class AdapterTelefone extends AbstractAdapter<AdapterTelefone.ViewHolder, Telefone> {


    public AdapterTelefone(Context context) {
        super(context);
    }

    public AdapterTelefone(Context context, List<Telefone> items) {
        super(context, items);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.telefone_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Telefone item) {
        holder.tv_telefone.setText(item.getNumero());
        holder.tv_tipo.setText(item.getTipo());
        //holder.tvCod.setText(item.getIdPessoa() + "");
    }

    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tv_telefone;
        final TextView tv_tipo;
        final TextView tvCod;



        public ViewHolder(View view) {
            super(view);
            tvCod = (TextView) view.findViewById(R.id.tv_cod);
            tv_telefone = (TextView) view.findViewById(R.id.tv_telefone);
            tv_tipo = (TextView) view.findViewById(R.id.tv_Tipo);


        }
    }
}

