package br.grupointegrado.appmetaforadevenda.Listagem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.grupointegrado.appmetaforadevenda.Fragments.ItensFragment;
import br.grupointegrado.appmetaforadevenda.Fragments.PedidoFragment;
import br.grupointegrado.appmetaforadevenda.Fragments.PessoaFragment;
import br.grupointegrado.appmetaforadevenda.Fragments.TelefoneFragment;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Util.FragmentTab;

/**
 * Created by eli on 18/09/2015.
 */
public class AdapterTabsViewPedido extends FragmentPagerAdapter {

    private Context mContext;
    private String[] titles = {"PEDIDO", "ITENS"};
    private Fragment[] fragments = {new PedidoFragment(), new ItensFragment()};


    public AdapterTabsViewPedido(FragmentManager fm, Context c) {
        super(fm);

        mContext = c;
        double scale = c.getResources().getDisplayMetrics().density;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = fragments[position];

        FragmentTab tab = (FragmentTab) frag;
        tab.atualizar();

        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);

        return frag;


    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (titles[position]);

    }

    public Fragment[] getFragments() {
        return fragments;
    }
}


