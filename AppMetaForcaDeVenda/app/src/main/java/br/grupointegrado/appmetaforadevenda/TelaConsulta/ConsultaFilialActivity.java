package br.grupointegrado.appmetaforadevenda.TelaConsulta;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import br.grupointegrado.appmetaforadevenda.Dao.FilialDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterFilial;
import br.grupointegrado.appmetaforadevenda.Pedido.Filial;
import br.grupointegrado.appmetaforadevenda.R;

public class ConsultaFilialActivity extends AppCompatActivity {



    private Toolbar atoolbar;
    private RecyclerView  RecyviewFilial;



    private FilialDao filialdao;
    private Filial filial;
    private AdapterFilial adapterfilial;


    private Integer idfilial;
    private String nomefilial;

    private boolean selecionandoFilial = false;
    private String conteudoSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta__filial);

        atoolbar = (Toolbar) (findViewById(R.id.tb_main));
        atoolbar.setTitle("Consulta Filial");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filialdao = new FilialDao(this);


        if (getIntent().getExtras() != null)
            selecionandoFilial = getIntent().getExtras().getBoolean("selecionar_filial", false);




        RecyviewFilial = (RecyclerView)findViewById(R.id.RecyviewFilial);

        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        RecyviewFilial.setLayoutManager(llm);

        adapterfilial = new AdapterFilial(this, new ArrayList<Filial>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples


                 Filial filial = adapterfilial.getItems().get(adapterPosition);
                if (selecionandoFilial) {
                    Intent data = new Intent();
                    data.putExtra("filial_id", filial.getIdfilial());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    idfilial = filial.getIdfilial();
                    nomefilial = filial.getDescricao();

                }

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo
               Filial  filial = adapterfilial.getItems().get(adapterPosition);
                idfilial = filial.getIdfilial();
                nomefilial = filial.getDescricao();




                return true;
            }
        };


        RecyviewFilial.setAdapter(adapterfilial);

        consultaFilial();



        getDadosSearch(this.getIntent());




    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        getDadosSearch(intent);

    }

    public void getDadosSearch(Intent intent) {

        String conteudoQuery = intent.getStringExtra(SearchManager.QUERY);
        conteudoSearch = conteudoQuery;

        if (conteudoSearch != null) {
            if (soExisteNumero(conteudoSearch)) {
                conteudoSearch = conteudoSearch.replace(" ","");
                Toast.makeText(this, conteudoSearch, Toast.LENGTH_SHORT).show();
                adapterfilial.setItems(filialdao.listaid(conteudoSearch));
                adapterfilial.notifyDataSetChanged();
            }else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adapterfilial.setItems(filialdao.listNome(conteudoSearch));
                adapterfilial.notifyDataSetChanged();
            }
        }

    }
    public Boolean soExisteNumero(String conteudo){

        conteudo = conteudo.replace(" ","");

        char[] c = conteudo.toCharArray();
        boolean retorno = false;
        int soma = 0;


        for ( int i = 0; i < c.length; i++ ){
            if ( Character.isDigit( c[ i ] ) ) {
                soma++;
            }

        }

        if (soma == c.length ) retorno  = true;


        return retorno;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulta__filial, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultaFilial).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(this.getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint_filial));


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                finish();
                break;
            case android.R.id.home:
                finish();
                break;

            case R.id.ConsultaFilial:
                consultaFilial();


        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (conteudoSearch != null){
            if (soExisteNumero(conteudoSearch)) {

                adapterfilial.setItems(filialdao.listaid(conteudoSearch));

                adapterfilial.notifyDataSetChanged();

            } else {
                adapterfilial.setItems(filialdao.listNome(conteudoSearch));

                adapterfilial.notifyDataSetChanged();
            }

        }else {
            consultaFilial();
        }


    }

    public void consultaFilial(){
        adapterfilial.setItems(filialdao.list());
        adapterfilial.notifyDataSetChanged();

    }
}
