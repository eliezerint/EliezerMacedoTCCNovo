package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.Filial;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 05/10/2015.
 */
public class AdapterFilial extends AbstractAdapter<AdapterFilial.ViewHolder, Filial> {

    public AdapterFilial(Context context) {
        super(context);
    }

    public AdapterFilial(Context context,List<Filial> items) {
        super(context, items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.filial_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Filial item) {
        holder.tvFilial.setText(item.getDescricao());
        holder.tvCod.setText(item.getIdfilial()+"");
    }



    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tvFilial;
        final TextView tvCod;

        public ViewHolder(View view) {
            super(view);
            tvCod = (TextView) view.findViewById(R.id.tv_cod);
            tvFilial = (TextView) view.findViewById(R.id.tv_filial);


        }
    }
}
