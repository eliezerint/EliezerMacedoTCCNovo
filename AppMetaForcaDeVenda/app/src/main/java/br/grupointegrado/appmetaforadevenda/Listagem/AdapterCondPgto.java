package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.CondicaoPagamento;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 06/10/2015.
 */
public class AdapterCondPgto extends AbstractAdapter<AdapterCondPgto.ViewHolder, CondicaoPagamento> {


    public AdapterCondPgto(Context context) {
        super(context);
    }

    public AdapterCondPgto(Context context, List<CondicaoPagamento> items) {
        super(context, items);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.condpgto_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position, CondicaoPagamento item) {
        holder.tvCondPgto.setText(item.getDescricao());
        holder.tvNrParcela.setText(item.getQuantidade().toString());
        holder.tvIntervalo.setText(item.getIntervelo().toString());
        holder.tvCod.setText(item.getIdcodicaopagamento() + "");
    }


    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tvCondPgto;
        final TextView tvNrParcela;
        final TextView tvIntervalo;
        final TextView tvCod;

        public ViewHolder(View view) {
            super(view);
            tvCod = (TextView) view.findViewById(R.id.tv_cod);
            tvCondPgto = (TextView) view.findViewById(R.id.tv_condpgto);
            tvNrParcela = (TextView) view.findViewById(R.id.tv_nr_parcelas);
            tvIntervalo = (TextView) view.findViewById(R.id.tv_intervalos);

        }
    }


}
