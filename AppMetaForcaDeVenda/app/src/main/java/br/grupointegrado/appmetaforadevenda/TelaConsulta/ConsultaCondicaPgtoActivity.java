package br.grupointegrado.appmetaforadevenda.TelaConsulta;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import br.grupointegrado.appmetaforadevenda.Dao.CondicaoPgtoDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterCondPgto;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterFilial;
import br.grupointegrado.appmetaforadevenda.Pedido.CondicaoPagamento;
import br.grupointegrado.appmetaforadevenda.R;

public class ConsultaCondicaPgtoActivity extends AppCompatActivity {

    private Toolbar atoolbar;
    private RecyclerView RecyviewCondPgto;

    private CondicaoPgtoDao condpgtodao;

    private AdapterCondPgto adaptercondpgto;

    private Integer idcondpgto;
    private String nomecondpgto;
    private Boolean selecionandoCondPgto = false;
    private String conteudoSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta__condica_pgto);

        atoolbar = (Toolbar) (findViewById(R.id.tb_main));
        atoolbar.setTitle("Condição de Pagamento");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        condpgtodao = new CondicaoPgtoDao(this);


        RecyviewCondPgto = (RecyclerView)findViewById(R.id.RecyviewCondPgto);





        if (getIntent().getExtras() != null)
            selecionandoCondPgto = getIntent().getExtras().getBoolean("selecionar_condpgto", false);


        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        RecyviewCondPgto.setLayoutManager(llm);

        adaptercondpgto = new AdapterCondPgto(this, new ArrayList<CondicaoPagamento>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples


                CondicaoPagamento condpgto = adaptercondpgto.getItems().get(adapterPosition);
                if (selecionandoCondPgto) {
                    Intent data = new Intent();
                    data.putExtra("condpgto_id", condpgto.getIdcodicaopagamento());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    idcondpgto = condpgto.getIdcodicaopagamento();
                    nomecondpgto = condpgto.getDescricao();

                }

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo
                CondicaoPagamento  condpgto = adaptercondpgto.getItems().get(adapterPosition);
                idcondpgto = condpgto.getIdcodicaopagamento();
                nomecondpgto = condpgto.getDescricao();




                return true;
            }
        };


        RecyviewCondPgto.setAdapter(adaptercondpgto);



        consultaCondicaoPgto();



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
                adaptercondpgto.setItems(condpgtodao.listId(conteudoSearch));
                adaptercondpgto.notifyDataSetChanged();
            }else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adaptercondpgto.setItems(condpgtodao.listNome(conteudoSearch));
                adaptercondpgto.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.menu_consulta__condica_pgto, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultaCondcaoPgto).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(this.getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint_conPgto));


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

            case R.id.ConsultaCondcaoPgto:
                consultaCondicaoPgto();


        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (conteudoSearch != null){
            if (soExisteNumero(conteudoSearch)) {

                adaptercondpgto.setItems(condpgtodao.listId(conteudoSearch));

                adaptercondpgto.notifyDataSetChanged();

            } else {
                adaptercondpgto.setItems(condpgtodao.listNome(conteudoSearch));

                adaptercondpgto.notifyDataSetChanged();
            }

        }else {
            consultaCondicaoPgto();
        }


    }

    public void consultaCondicaoPgto(){
        adaptercondpgto.setItems(condpgtodao.list());
        adaptercondpgto.notifyDataSetChanged();

    }
}
