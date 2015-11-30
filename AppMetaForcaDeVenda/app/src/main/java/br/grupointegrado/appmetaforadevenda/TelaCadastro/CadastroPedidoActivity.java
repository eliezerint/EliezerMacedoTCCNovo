package br.grupointegrado.appmetaforadevenda.TelaCadastro;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

import br.grupointegrado.appmetaforadevenda.Dao.PedidoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Extras.SlidingTabLayout;
import br.grupointegrado.appmetaforadevenda.Fragments.ItensFragment;
import br.grupointegrado.appmetaforadevenda.Fragments.PedidoFragment;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterTabsViewPedido;
import br.grupointegrado.appmetaforadevenda.Pedido.ItensPedido;
import br.grupointegrado.appmetaforadevenda.Pedido.Pedido;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;


/**
 * Created by eli on 18/09/2015.
 */
public class CadastroPedidoActivity extends AppCompatActivity {
    private EditText idvendedorpedido;
    private Toolbar atoolbar;

    private AdapterTabsViewPedido tabsAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private long tempopresionadovoltar;

    private  PedidoFragment fragPedido ;
    private  ItensFragment fragItens ;

    private PedidoDao pedidodao;
    private PessoaDao pessoadao;
    private Integer idItens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle(" Pedido ");
        atoolbar.setLogo(R.drawable.ic_description_black_18dp);
        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pedidodao = new PedidoDao(this);
        pessoadao = new PessoaDao(this);


        //pageView

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        tabsAdapter = new AdapterTabsViewPedido(getSupportFragmentManager(), this);
        mViewPager.setAdapter(tabsAdapter);

        fragPedido = (PedidoFragment) tabsAdapter.getFragments()[0];
        fragItens = (ItensFragment) tabsAdapter.getFragments()[1];


        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        //mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorWhite));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (fragItens.somaitens > 0){
                    fragPedido.edit_valor_Total.setText(String.format(Locale.US, "%.2f",fragItens.somaitens));
                }else {
                    fragPedido.edit_valor_Total.setText("0.00");
                }
            }

            @Override
            public void onPageSelected(int position) {
                fragPedido.edit_valor_Total.setText(fragItens.somaitens.toString());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });






        mSlidingTabLayout.setViewPager(mViewPager);
        //mSlidingTabLayout.setHorizontalFadingEdgeEnabled(true);
        //mSlidingTabLayout.setHorizontalScrollBarEnabled(true);





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastar_pedido, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.Salvarpedido:
                fragPedido = (PedidoFragment) tabsAdapter.getFragments()[0];
                fragItens = (ItensFragment) tabsAdapter.getFragments()[1];

                if (fragPedido.Validate() == true) {

                    if (fragPedido.ispedidoAlt() == false) {

                        try {
                            int idpedido = pedidodao.CosultaPedido();
                            pedidodao.savePedido(fragPedido.getPedido(), idpedido);
                            pessoadao.UpdateUltimaCompra(fragPedido.getPedido());


                            int tamanho = fragItens.tamanhoLista();
                          if (tamanho > 0) {
                                    for (int x = 0; x < tamanho; x++) {
                                        try {

                                        pedidodao.saveItensPedido(fragItens.getTItens(idpedido,
                                                fragPedido.getPedido().getIdpessoa(),
                                                fragPedido.getPedido().getIdvendedor(), x));

                                    }catch(Exception e){
                                        Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
                                    }



                                    }

                                }


                            fragPedido.LimparTela();
                            fragItens.LimparLista();

                            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //alterar

                        try {


                            fragPedido.edit_valor_Total.setText(fragItens.somaitens.toString());

                            pedidodao.Update(fragPedido.getPedido());


                            int tamanho = fragItens.tamanhoLista();


                            ItensPedido itens;

                            if (tamanho > 0) {
                                try {
                                    pedidodao.deleteItens(fragPedido.getPedido());
                                }catch (Exception e) {
                                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                                for (int x = 0; x < tamanho; x++) {

                                  try {
                                      itens = fragItens.getTItens(fragPedido.getPedido().getIdpedido(),
                                              fragPedido.getPedido().getIdpessoa(),
                                              fragPedido.getPedido().getIdvendedor(), x);

                                      pedidodao.saveItensPedido(itens);
                                  }catch (Exception e) {
                                      Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                                  }

                                }

                            }
                            fragItens.LimparLista();
                            fragPedido.LimparTela();

                            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();

                        } catch (Exception e) {
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
                }



            }
        return true;

    }


    @Override
    public void onBackPressed() {

        long t = System.currentTimeMillis();
        if (t - tempopresionadovoltar > 4000) {
            tempopresionadovoltar = t;
            Toast.makeText(this.getBaseContext(), "Pressiona de volta para sair", Toast.LENGTH_SHORT).show();
        } else {

            super.onBackPressed();
            finish();
        }
    }

}
