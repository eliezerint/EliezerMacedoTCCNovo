package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Produtos.Produtos;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 07/12/2015.
 */
public class AdapterConsultaTabelapreco  extends AbstractAdapter<AdapterConsultaTabelapreco.ViewHolder, Produtos>  {

    public AdapterConsultaTabelapreco(Context context) {
        super(context);
    }

    public AdapterConsultaTabelapreco(Context context,List<Produtos> items) {
        super(context, items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.tabelapreco_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Produtos item) {
        holder.tvDescricao.setText(item.getDescricao());
        holder.tvValor.setText(String.format("%.2f",item.getValorUnitario()));
        holder.tvCod.setText(item.getIdproduto()+"");
    }



    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tvDescricao;
        final TextView tvValor;
        final TextView tvCod;

        public ViewHolder(View view) {
            super(view);
            tvCod = (TextView) view.findViewById(R.id.tv_codTabela);
            tvDescricao = (TextView) view.findViewById(R.id.tvDescricaoProduto);
            tvValor = (TextView) view.findViewById(R.id.tvValorUnitario);


        }
    }
}
