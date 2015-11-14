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

import br.grupointegrado.appmetaforadevenda.Dao.VendedorDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterVendedor;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Vendedor.Vendedor;

public class ConsultaVendedorActivity extends AppCompatActivity {

    private Toolbar atoolbar;
    private RecyclerView recyclerviewvendedor;
    private AdapterVendedor adaptervendedor;


    private VendedorDao vendedordao;

    private String nome;
    private Integer idvendedor;

    private Boolean selecionandoVendedor = false;
    private String conteudoSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta__vendedor);


        atoolbar = (Toolbar)findViewById(R.id.tb_main);
        atoolbar.setTitle("Consulta de Vendedor");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        vendedordao = new VendedorDao(this);

        /*insercao de dados de teste

        */


        /* vendedordao.saveVendedor("Amanda", 10.00 , 10.00);
         vendedordao.saveVendedor("Tereza", 40.00 , 40.00);
         vendedordao.saveVendedor("Onofre", 50.00 , 50.00);*/



        recyclerviewvendedor = (RecyclerView)findViewById(R.id.RecyviewVendedor);

        if (getIntent().getExtras() != null)
            selecionandoVendedor = getIntent().getExtras().getBoolean("selecionar_vendedor", false);




        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerviewvendedor.setLayoutManager(llm);


        adaptervendedor =  new AdapterVendedor(this, new  ArrayList<Vendedor>()){

                @Override
                protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                    // evento de click simples

                    Vendedor vendedor = adaptervendedor.getItems().get(adapterPosition);
                    if (selecionandoVendedor) {
                        Intent data = new Intent();
                        data.putExtra("vendedor_id", vendedor.getIdvendedor());
                        setResult(RESULT_OK, data);
                        finish();
                    }


                }

                @Override
                protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                    // evento e click longo

                    Vendedor vendedor = adaptervendedor.getItems().get(adapterPosition);

                    idvendedor = vendedor.getIdvendedor();
                    nome = vendedor.getNome();


                    return true;
                }
        };

        recyclerviewvendedor.setAdapter(adaptervendedor);

        consultaVendedor();



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
                adaptervendedor.setItems(vendedordao.listId(conteudoSearch));
                adaptervendedor.notifyDataSetChanged();
            }else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adaptervendedor.setItems(vendedordao.listNome(conteudoSearch));
                adaptervendedor.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.menu_consulta__vendedor, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultaVendedor).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(this.getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint_Vendedor));


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


        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (conteudoSearch != null){
            if (soExisteNumero(conteudoSearch)) {

                adaptervendedor.setItems(vendedordao.listId(conteudoSearch));

                adaptervendedor.notifyDataSetChanged();

            } else {
                adaptervendedor.setItems(vendedordao.listNome(conteudoSearch));

                adaptervendedor.notifyDataSetChanged();
            }

        }else {
            consultaVendedor();
        }


    }

    public void consultaVendedor(){
        adaptervendedor.setItems(vendedordao.list());
        adaptervendedor.notifyDataSetChanged();

    }
}
