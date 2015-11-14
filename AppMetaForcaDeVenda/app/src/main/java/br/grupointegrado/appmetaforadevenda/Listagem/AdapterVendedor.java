package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Vendedor.Vendedor;

/**
 * Created by eli on 06/10/2015.
 */
public class AdapterVendedor extends AbstractAdapter<AdapterVendedor.ViewHolder, Vendedor> {

    public AdapterVendedor(Context context) {
        super(context);
    }

    public AdapterVendedor(Context context, List<Vendedor> items) {
        super(context, items);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.vendedor_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Vendedor item) {
        holder.tvNome.setText(item.getNome());
        holder.tvCod.setText(item.getIdvendedor() + "");
    }

    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tvNome;
        final TextView tvCod;

        public ViewHolder(View view) {
            super(view);
            tvCod = (TextView) view.findViewById(R.id.tv_cod);
            tvNome = (TextView) view.findViewById(R.id.tv_nome);

        }
    }
}
