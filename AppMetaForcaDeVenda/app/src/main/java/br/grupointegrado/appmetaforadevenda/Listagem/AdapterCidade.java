
package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Fragments.TelefoneFragment;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eliezer on 06/09/2015.
 */
public class AdapterCidade extends AbstractAdapter<AdapterCidade.ViewHolder, Cidade> {


    public AdapterCidade(Context context) {
        super(context);
    }

    public AdapterCidade(Context context, List<Cidade> items) {
        super(context, items);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.cidade_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Cidade item) {
        holder.tvCidade.setText(item.getDescricao());
        holder.tvEstado.setText(item.getIdestado());
        holder.tvIbge.setText(item.getIbge());
        holder.tvCod.setText(item.getIdcidade() + "");
    }

    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tvCidade;
        final TextView tvEstado;
        final TextView tvIbge;
        final TextView tvCod;

        public ViewHolder(View view) {
            super(view);
            tvCod = (TextView) view.findViewById(R.id.tv_cod);
            tvCidade = (TextView) view.findViewById(R.id.tvCidade);
            tvEstado = (TextView) view.findViewById(R.id.tvEstado);
            tvIbge = (TextView) view.findViewById(R.id.tvIbge);
        }
    }
}


