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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.ProdutoDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterConsultaTabelapreco;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterTabelaprecoSpinner;
import br.grupointegrado.appmetaforadevenda.Produtos.Produtos;
import br.grupointegrado.appmetaforadevenda.Produtos.TabelaItenPreco;
import br.grupointegrado.appmetaforadevenda.Produtos.Tabelapreco;
import br.grupointegrado.appmetaforadevenda.R;

public class ConsultaTabelaPrecoActivity extends AppCompatActivity {

    private Toolbar atoolbar;
    private RecyclerView RecyviewTabelapreco;
    private MaterialBetterSpinner Sptabelapreco;



    private ProdutoDao produtodao;
    private Produtos produto;
    private AdapterConsultaTabelapreco adapterTabelaitempreco;

    private Integer idTabela;
    private String nomeProduto;

    private boolean selecionandoTabela = false;
    private String conteudoSearch;
    private Integer idProduto;
    private ArrayAdapter<Produtos> produto_adapter;
    private List<Produtos> listaproduto;
    private AutoCompleteTextView autocomplete;
    private String conteudoproduto;
    private List<Tabelapreco> listatabela = null;
    private AdapterTabelaprecoSpinner Tabela_adapter;
    private ListView listviewtabelaPreco;
    private List lista ;
    private AdapterTabelaprecoSpinner arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_tabela_preco);

        atoolbar = (Toolbar) (findViewById(R.id.tb_main));
        atoolbar.setTitle("Consulta Tabela de Preço");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        RecyviewTabelapreco = (RecyclerView)findViewById(R.id.RecyviewTabelaItempreco);


        produtodao = new ProdutoDao(this);




        if (getIntent().getExtras() != null)
            selecionandoTabela = getIntent().getExtras().getBoolean("selecionar_TabelaItemPreco", false);



        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        RecyviewTabelapreco.setLayoutManager(llm);

        adapterTabelaitempreco = new AdapterConsultaTabelapreco(this, new ArrayList<Produtos>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples


                Produtos produto = adapterTabelaitempreco.getItems().get(adapterPosition);
                if (selecionandoTabela) {
                    Intent data = new Intent();
                    data.putExtra("Produto_id", produto.getIdproduto());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    idProduto = produto.getIdproduto();
                    nomeProduto = produto.getDescricao();

                    consultaTabelaPreco(produto);

                }

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo
                Produtos produto = adapterTabelaitempreco.getItems().get(adapterPosition);
                idProduto = produto.getIdproduto();
                nomeProduto = produto.getDescricao();

                consultaTabelaPreco(produto);




                return true;
            }
        };


        RecyviewTabelapreco.setAdapter(adapterTabelaitempreco);


       consultaTabelaitem();


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
                adapterTabelaitempreco.setItems(produtodao.listIdprodutoParaTelaTabelaPreco(conteudoSearch));
                adapterTabelaitempreco.notifyDataSetChanged();

            }else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adapterTabelaitempreco.setItems(produtodao.listnomeParaTelaTabelaPreco(conteudoSearch));
                adapterTabelaitempreco.notifyDataSetChanged();

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
        getMenuInflater().inflate(R.menu.menu_consulta_tabela_preco, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultaTabela).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(this.getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint_Tabelapreco));


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

            case R.id.ConsultaTabela:
                conteudoSearch = null;
                consultaTabelaitem();

                break;


        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (conteudoSearch != null){
            if (soExisteNumero(conteudoSearch)) {

                adapterTabelaitempreco.setItems(produtodao.listIdprodutoParaTelaTabelaPreco(conteudoSearch));

                adapterTabelaitempreco.notifyDataSetChanged();


            } else {
                adapterTabelaitempreco.setItems(produtodao.listnomeParaTelaTabelaPreco(conteudoSearch));

                adapterTabelaitempreco.notifyDataSetChanged();


            }

        }else if (conteudoSearch == null){
            adapterTabelaitempreco.setItems(produtodao.listGeralParaTelaTabelaPreco());
            adapterTabelaitempreco.notifyDataSetChanged();
        }


    }

    public void consultaTabelaitem(){
        if (conteudoSearch != null){
            if (soExisteNumero(conteudoSearch)) {

                adapterTabelaitempreco.setItems(produtodao.listIdprodutoParaTelaTabelaPreco(conteudoSearch));

                adapterTabelaitempreco.notifyDataSetChanged();



            } else {
                adapterTabelaitempreco.setItems(produtodao.listnomeParaTelaTabelaPreco(conteudoSearch));

                adapterTabelaitempreco.notifyDataSetChanged();

            }

        }else if (conteudoSearch == null){
            adapterTabelaitempreco.setItems(produtodao.listGeralParaTelaTabelaPreco());
            adapterTabelaitempreco.notifyDataSetChanged();

        }
    }

    private void consultaTabelaPreco(final Produtos produto) {
        final Integer divisao = 100;


        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Tabela Preço")
                .customView(R.layout.layout_dialogs_tabelapreco, true)
                .negativeText("Sair")
                .autoDismiss(true)
                .build();

        listviewtabelaPreco = (ListView)dialog.findViewById(R.id.listviewtabelaPreco);


        lista = produtodao.listPrecoidprodutoComTabelaItem(produto.getIdproduto());


        arrayAdapter = new AdapterTabelaprecoSpinner(this, lista);

        listviewtabelaPreco.setAdapter(arrayAdapter);


          dialog.show();
    }

    }
