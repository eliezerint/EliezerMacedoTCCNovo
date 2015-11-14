package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.Pedido;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 12/11/2015.
 */
public class AdapterPedido  extends AbstractAdapter<AdapterPedido.ViewHolder, Pedido>  {

    public AdapterPedido(Context context) {
        super(context);
    }

    public AdapterPedido(Context context, List<Pedido> items) {
        super(context, items);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.pedido_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Pedido item) {
        holder.tv_Cliente.setText(item.getNome());
        holder.tv_Apelido.setText(item.getFantasia());
        holder.tv_Vendedor.setText(item.getNomevendedor());
        holder.tv_Total.setText(" R$ " +item.getTotal()+"");
        holder.tv_Cod.setText(item.getIdpedido()+ "");
    }

    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tv_Cliente;
        final TextView tv_Apelido;
        final TextView tv_Vendedor;
        final TextView tv_Total;
        final TextView tv_Cod;

        public ViewHolder(View view) {
            super(view);
            tv_Cod = (TextView) view.findViewById(R.id.tv_cod);
            tv_Cliente = (TextView) view.findViewById(R.id.tv_Cliente);
            tv_Apelido = (TextView) view.findViewById(R.id.tv_Apelido);
            tv_Vendedor = (TextView) view.findViewById(R.id.tv_Vendedor);
            tv_Total = (TextView) view.findViewById(R.id.tv_TotalPedido);
        }
    }
}


