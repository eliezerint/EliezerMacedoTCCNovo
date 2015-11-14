package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Produtos.Produtos;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 12/10/2015.
 */
public class AdapterProduto extends AbstractAdapter<AdapterProduto.ViewHolder, Produtos>  {

    public AdapterProduto(Context context) {
        super(context);
    }

    public AdapterProduto(Context context, List<Produtos> items) {
        super(context, items);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.produto_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Produtos item) {
        holder.tv_Produto.setText(item.getDescricao());
        holder.tv_Unidade_Medida.setText(item.getDescricaoUnidademedida());
        holder.tv_Grupode_Produto.setText(item.getDescricaoGrupoProduto());
        holder.tv_Cod.setText(item.getIdproduto() + "");
    }

    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tv_Produto;
        final TextView tv_Unidade_Medida;
        final TextView tv_Grupode_Produto;
        final TextView tv_Cod;

        public ViewHolder(View view) {
            super(view);
            tv_Cod = (TextView) view.findViewById(R.id.tv_cod);
            tv_Produto = (TextView) view.findViewById(R.id.tv_Produto);
            tv_Unidade_Medida = (TextView) view.findViewById(R.id.tv_Unidade_Medida);
            tv_Grupode_Produto = (TextView) view.findViewById(R.id.tv_GrupoProduto);
        }
    }
}
