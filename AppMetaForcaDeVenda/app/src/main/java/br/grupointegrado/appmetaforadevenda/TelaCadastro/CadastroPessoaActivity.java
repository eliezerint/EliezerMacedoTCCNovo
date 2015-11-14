package br.grupointegrado.appmetaforadevenda.TelaCadastro;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Extras.SlidingTabLayout;
import br.grupointegrado.appmetaforadevenda.Fragments.PessoaFragment;
import br.grupointegrado.appmetaforadevenda.Fragments.TelefoneFragment;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterTabsViewPessoa;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.R;

/**
 * Created by eli on 18/09/2015.
 */
public class CadastroPessoaActivity extends AppCompatActivity {


    private Toolbar atoolbar;
    private String titulo;
    private Integer positiontab;

    private AdapterTabsViewPessoa tabsAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    private Integer posicaoPageView;
    private long tempopresionadovoltar = 0;

    private Pessoa pessoa;
    private Pessoa pessoalt;


    private PessoaDao pessoadao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);


        pessoadao = new PessoaDao(this);

        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle("Cadastrar Cliente");
        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        tabsAdapter = new AdapterTabsViewPessoa(getSupportFragmentManager(), this);
        mViewPager.setAdapter(tabsAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        //mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorWhite));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                positiontab = position;


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
        getMenuInflater().inflate(R.menu.menu_cadastro_pessoa, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.Salvarpessoa:

                PessoaFragment fragPes = (PessoaFragment) tabsAdapter.getFragments()[0];
                TelefoneFragment fragTel = (TelefoneFragment) tabsAdapter.getFragments()[1];
                if (fragPes.Validate() == true) {
                    if (fragPes.ispessoAlt() == false) {

                        try {
                            pessoadao.savePessoa(fragPes.getPessoa());
                            int idpessoa = pessoadao.CosultaClienteCNPJCPF(fragPes.getPessoa().getCnpjCpf());
                            String CPFCNPJ = fragPes.getPessoa().getCnpjCpf();
                            int tamanho = fragTel.tamanhoLista();


                            if (tamanho > 0) {
                                for (int x = 0; x < tamanho; x++) {
                                    pessoadao.saveTelefone(fragTel.getTelefone(idpessoa, fragPes.getPessoa().getCnpjCpf(), x));

                                }

                            }
                            fragTel.LimparLista();
                            fragPes.LimparCampos();
                            fragPes.editDataCadastro.setText(fragPes.getDateTime());
                            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {

                            String CPFCNPJ = fragPes.getPessoa().getCnpjCpf();
                            int idpessoa = pessoadao.CosultaClienteCNPJCPF(CPFCNPJ);
                            int tamanho = fragTel.tamanhoLista();

                            pessoadao.Update(fragPes.getPessoa());
                            pessoadao.deleteTelefone(idpessoa);
                            if (tamanho > 0) {

                                for (int x = 0; x < tamanho; x++) {
                                     pessoadao.saveTelefone(fragTel.getTelefone(idpessoa, fragPes.getPessoa().getCnpjCpf(), x));

                                }

                            }
                            fragTel.LimparLista();
                            fragPes.LimparCampos();
                            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();

                        } catch (Exception e) {
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }


                break;

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



