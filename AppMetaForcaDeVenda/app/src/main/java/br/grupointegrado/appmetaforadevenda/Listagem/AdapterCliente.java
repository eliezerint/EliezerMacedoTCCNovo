package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 15/09/2015.
 */
public class AdapterCliente extends AbstractAdapter<AdapterCliente.ViewHolder, Pessoa> {


    public AdapterCliente(Context context) {
        super(context);
    }

    public AdapterCliente(Context context,List<Pessoa> items) {
        super(context, items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.cliente_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Pessoa item) {

        holder.tvCod.setText(item.getIdpessoa()+"");
        holder.tvNome.setText(item.getRazaoSocialNome());
        holder.tvApelido.setText(item.getFantasiaApelido());
        holder.tvTelefone.setText(item.getTelefone());
        holder.tvEmail.setText(item.getEmail());

    }



    public class ViewHolder extends AbstractAdapter.AbstractViewHolder {
        final TextView tvNome;
        final TextView tvApelido;
        final TextView tvTelefone;
        final TextView tvCod;
        final TextView tvEmail;


        public ViewHolder(View view) {
            super(view);
            tvCod = (TextView) view.findViewById(R.id.tv_cod);
            tvNome = (TextView) view.findViewById(R.id.tvnome);
            tvApelido = (TextView) view.findViewById(R.id.tvApelido);
            tvTelefone = (TextView) view.findViewById(R.id.tvTelefone);
            tvEmail = (TextView) view.findViewById(R.id.tvEmail);

        }
    }
}


