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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterCidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pais;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.TelaCadastro.CadastroCidadeActivity;

public class ConsultaCidadeActivity extends AppCompatActivity {

    private Toolbar atoolbar;
    private RecyclerView RecyviewCidade;
    private List listacidade;
    public MaterialEditText MaterialEditCidade;
    private MaterialBetterSpinner MaterialSpinnerPais;
    private MaterialBetterSpinner MaterialSpinnerEstado;


    private String nomecidade;
    private String conteudopais;
    private String conteudoestado;
    private String conteudoSearch;
    private Integer idCidade;
    private String IBGE;
    private List<Pais> lista_pais;
    private List<Estado> lista_estado;
    private ArrayAdapter<Pais> pais_adapter;
    private ArrayAdapter<Estado> estado_adapter;


    private Pessoa pessoa;
    private Cidade cidade;
    private CidadeDao cidadedao;
    private EstadoDao estadodao;
    private PaisDao paisdao;
    private AdapterCidade adaptercidade;
    private boolean selecionandoCidade = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_cidade);
        atoolbar = (Toolbar) (findViewById(R.id.tb_main));
        atoolbar.setTitle("Consulta Cidade");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MaterialSpinnerPais = (MaterialBetterSpinner) findViewById(R.id.SpinnerPais);
        MaterialSpinnerEstado = (MaterialBetterSpinner) findViewById(R.id.SpinnerEstado);

        RecyviewCidade = (RecyclerView) findViewById(R.id.RecyviewCidade);


        cidadedao = new CidadeDao(this);
        paisdao = new PaisDao(this);
        estadodao = new EstadoDao(this);


        if (getIntent().getExtras() != null)
            selecionandoCidade = getIntent().getExtras().getBoolean("selecionar_cidade", false);




        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        RecyviewCidade.setLayoutManager(llm);

        adaptercidade = new AdapterCidade(this, new ArrayList<Cidade>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples
                //  MaterialDialogCidade();


                Cidade cidade = adaptercidade.getItems().get(adapterPosition);
                if (selecionandoCidade) {
                    Intent data = new Intent();
                    data.putExtra("cidade_id", cidade.getId());
                    setResult(RESULT_OK, data);
                    finish();
                } else {

                    EditarCidade(getCidade(cidade));

                }

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo
                Cidade cidade = adaptercidade.getItems().get(adapterPosition);

                MaterialDialogCidade(cidade);
                return true;
            }
        };


        RecyviewCidade.setAdapter(adaptercidade);


        lista_pais = paisdao.list();

        pais_adapter = new ArrayAdapter<Pais>(this, android.R.layout.simple_list_item_1, lista_pais);


        MaterialSpinnerPais.setAdapter(pais_adapter);

        MaterialSpinnerPais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conteudopais = MaterialSpinnerPais.getText().toString();
                consultaestado();
            }

        });


        consultaestado();




        MaterialSpinnerEstado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conteudoestado = MaterialSpinnerEstado.getText().toString();

            }
        });



        if(estadodao.list("BR").isEmpty() && paisdao.list().isEmpty()){
            cadastrarPais();
            cadastrarEstado();
        }


        Consultacidade();
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
            if (soExisteNumero(conteudoSearch)){
                conteudoSearch = conteudoSearch.replace(" ","");
                Toast.makeText(this, conteudoSearch , Toast.LENGTH_SHORT).show();
                conteudopais = null;
                conteudoestado = null;
                adaptercidade.setItems(cidadedao.listCodIbge(conteudoSearch));
                adaptercidade.notifyDataSetChanged();
            }else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                conteudopais = null;
                conteudoestado = null;
                adaptercidade.setItems(cidadedao.list(conteudoSearch));
                adaptercidade.notifyDataSetChanged();
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulta_cidade, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultarCidade).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.ConsultarCidade:
                LimparSpinner();
                Consultacidade();

                break;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (conteudoSearch != null) {
            if (soExisteNumero(conteudoSearch)){
                conteudoSearch  = conteudoSearch.replace(" ","");
                adaptercidade.setItems(cidadedao.listCodIbge(conteudoSearch));
                adaptercidade.notifyDataSetChanged();

            }else {
                adaptercidade.setItems(cidadedao.list(conteudoSearch));
                adaptercidade.notifyDataSetChanged();
            }
        } else if (conteudopais != null && conteudoestado != null) {
            adaptercidade.setItems(cidadedao.list(conteudopais, conteudoestado));
            adaptercidade.notifyDataSetChanged();

        } else {


            adaptercidade.setItems(cidadedao.list());
            adaptercidade.notifyDataSetChanged();

        }


    }

    private void Consultacidade() {


        if (conteudopais != null && conteudoestado != null) {
            adaptercidade.setItems(cidadedao.list(conteudopais, conteudoestado));
            adaptercidade.notifyDataSetChanged();

        } else {


            adaptercidade.setItems(cidadedao.list());
            adaptercidade.notifyDataSetChanged();
        }

    }


    public void CadastrarCidade(View view) {
        Intent i = new Intent(this.getApplication(), CadastroCidadeActivity.class);


        startActivity(i);

    }


    public void MaterialDialogCidade(final Cidade cidade) {
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title("Cidade")
                .items(R.array.Array_de_alterar)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text.equals("Editar")) {

                            EditarCidade(getCidade(cidade));
                            dialog.dismiss();
                        } else if (text.equals("Excluir")) {
                            DeletarCidade(cidade);
                            dialog.dismiss();
                        }


                    }

                })
                .show();
    }


    public void EditarCidade(Cidade cidade) {
        Intent i = new Intent(this.getBaseContext(), CadastroCidadeActivity.class);
        i.putExtra("alterarcidade", cidade);


        startActivity(i);

    }

    public void DeletarCidade(Cidade cidade) {
        try {
            cidadedao.delete(cidade.getId());
            Toast.makeText(this, "Cidade Excluida", Toast.LENGTH_SHORT).show();
            Consultacidade();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }


    //pegar dados da tela e passar para o modelo
    public Cidade getCidade(Cidade cidade) {
        return new Cidade(
                cidade.getPais(),
                cidade.getIdestado(),
                cidade.getIdcidade(),
                cidade.getDescricao(),
                cidade.getIbge());


    }

    public void cadastrarPais(){

        paisdao.savePais("BR", "Brasil");
        paisdao.savePais("UY","Uruguai");
        paisdao.savePais("AR","Argetina");
        paisdao.savePais("PY","Paraguay");

    }
    public  void cadastrarEstado(){

        estadodao.saveEstado("AC", "Acre","BR");
        estadodao.saveEstado("AL", "Alagoas","BR");
        estadodao.saveEstado("AP", "Amapa","BR");
        estadodao.saveEstado("AM", "Amazonas","BR");
        estadodao.saveEstado("BH", "Bahia","BR");
        estadodao.saveEstado("CE", "Ceará","BR");
        estadodao.saveEstado("DF", "Distrito Federal","BR");
        estadodao.saveEstado("ES", "Espirito Santo","BR");
        estadodao.saveEstado("GO", "Goais","BR");
        estadodao.saveEstado("MT", "Mato Grossso ","BR");
        estadodao.saveEstado("MS", "Mato Grossso do Sul","BR");
        estadodao.saveEstado("MA", "Maranhão","BR");
        estadodao.saveEstado("MG", "Minas Gerais","BR");
        estadodao.saveEstado("PA", "Pará","BR");
        estadodao.saveEstado("PR", "Parana","BR");
        estadodao.saveEstado("PB", "Paraiba","BR");
        estadodao.saveEstado("PI", "Piaui","BR");
        estadodao.saveEstado("PE", "Pernambuco","BR");
        estadodao.saveEstado("RJ", "Rio de Janeiro","BR");
        estadodao.saveEstado("RN", "Rio Grande do Norte","BR");
        estadodao.saveEstado("RS", "Rio Grande Sul","BR");
        estadodao.saveEstado("RO", "Rondonia","BR");
        estadodao.saveEstado("RR", "Roraima","BR");
        estadodao.saveEstado("SC", "Santa Catarina","BR");
        estadodao.saveEstado("SP", "São Paulo","BR");
        estadodao.saveEstado("SE", "Sergipe","BR");
        estadodao.saveEstado("TO", "Tocantins","BR");

        estadodao.saveEstado("GA", "Guaira ","PY");
        estadodao.saveEstado("BU", "Buenos Aires","AR");
        estadodao.saveEstado("MO", "Montevidéu","UY");


    }
    public void consultaestado(){


        if (MaterialSpinnerPais.getText().toString().isEmpty()){
            MaterialSpinnerEstado.setText("");
            lista_estado = estadodao.list("BR");

        }else  if (conteudopais != null) {
            MaterialSpinnerEstado.setText("");
            lista_estado = estadodao.list(conteudopais);

        }


        estado_adapter = new ArrayAdapter<Estado>(this, android.R.layout.simple_list_item_1, lista_estado);


        MaterialSpinnerEstado.setAdapter(estado_adapter);



        estado_adapter.notifyDataSetChanged();

    }


    public void LimparSpinner() {
        MaterialSpinnerPais.setText("");
        MaterialSpinnerEstado.setText("");
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





}
