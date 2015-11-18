
package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.ItensPedido;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 07/10/2015.
 */
public class AdapterItensPedido extends AbstractAdapter<AdapterItensPedido.ViewHolder, ItensPedido>{
    public AdapterItensPedido(Context context) {
        super(context);
    }

    public AdapterItensPedido(Context context, List<ItensPedido> items) {
        super(context, items);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.itenspedido_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, ItensPedido item) {
        holder.tv_nomeproduto.setText(item.getNomeproduto());
        holder.tv_quantidade.setText(item.getQuantidade().toString());
        holder.tv_vlunitario.setText(" X  "+item.getVlunitario().toString());
        holder.tv_desconto.setText(" - " +item.getDesconto().toString());
        holder.tv_valortotal.setText(" R$  "+item.getTotal().toString());

    }

    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {

        final TextView tv_nomeproduto;
        final TextView tv_quantidade;
        final TextView tv_vlunitario;
        final TextView tv_desconto;
        final TextView tv_valortotal;


        public ViewHolder(View view) {
            super(view);
            tv_nomeproduto = (TextView) view.findViewById(R.id.tv_nomeproduto);
            tv_quantidade = (TextView) view.findViewById(R.id.tv_quantidade);
            tv_vlunitario = (TextView) view.findViewById(R.id.tv_vlunitario);
            tv_desconto = (TextView) view.findViewById(R.id.tv_desconto);
            tv_valortotal = (TextView) view.findViewById(R.id.tv_valortotal);
        }
    }
}
