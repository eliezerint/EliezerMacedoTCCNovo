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
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialMultiAutoCompleteTextView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterCliente;

import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.TelaCadastro.CadastroPessoaActivity;
import br.grupointegrado.appmetaforadevenda.R;

public class ConsultaClienteActivity extends AppCompatActivity {

    private Toolbar atoolbar;
    private RecyclerView RecyviewPessoa;
    private List lista;
    private AdapterCliente adaptercliente;
    private List<Telefone> lista_telefone;
    private MaterialBetterSpinner MaterialSpinnerCidade;
    private AutoCompleteTextView autocomplete;


    private String conteudoSearch;
    private Integer idpessoa;
    private String CNPJCPF;

    private PessoaDao clientedao;
    private Pessoa pessoa;
    private CidadeDao cidadedao;


    private Boolean selecionandoPessoa = false;
    private List<Cidade> listaCidade;
    private ArrayAdapter<Cidade> cidade_adapter;
    private String conteudocidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_cliente);

        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle("Consulta de Cliente");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MaterialSpinnerCidade = (MaterialBetterSpinner) findViewById(R.id.SpinnerCidade);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.autocomplete);


        RecyviewPessoa = (RecyclerView) findViewById(R.id.RecyviewPessoa);

        clientedao = new PessoaDao(this);
        cidadedao = new CidadeDao(this);

        if (getIntent().getExtras() != null)
            selecionandoPessoa = getIntent().getExtras().getBoolean("selecionar_pessoa", false);


        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        RecyviewPessoa.setLayoutManager(llm);


        adaptercliente = new AdapterCliente(this, new ArrayList<Pessoa>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples

                Pessoa pessoa = adaptercliente.getItems().get(adapterPosition);
                if (selecionandoPessoa) {
                    Intent data = new Intent();
                    data.putExtra("pessoa_id", pessoa.getIdpessoa());
                    setResult(RESULT_OK, data);
                    finish();
                }else {

                    EditarPessoa(getPessoa(pessoa));


                }


            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo

                Pessoa pessoa = adaptercliente.getItems().get(adapterPosition);

                idpessoa = pessoa.getIdpessoa();
                CNPJCPF = pessoa.getCnpjCpf();

                MaterialDialogCidade(pessoa);

                return true;
            }


        };

        RecyviewPessoa.setAdapter(adaptercliente);


        listaCidade = cidadedao.list();


        cidade_adapter = new ArrayAdapter<Cidade>(this, android.R.layout.simple_list_item_1, listaCidade);

        autocomplete.setAdapter(cidade_adapter);
        MaterialSpinnerCidade.setAdapter(cidade_adapter);



     autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Filter filter = cidade_adapter.getFilter();
             filter = null;
             conteudocidade = autocomplete.getText().toString();
             Consultacliente();
         }
     });


        MaterialSpinnerCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conteudocidade = MaterialSpinnerCidade.getText().toString();
                Consultacliente();

            }

        });



        Consultacliente();

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
            MaterialSpinnerCidade.setText("");
            if (soExisteNumero(conteudoSearch)) {
                conteudoSearch = conteudoSearch.replace(" ","");
                Toast.makeText(this, conteudoSearch , Toast.LENGTH_SHORT).show();
                adaptercliente.setItems(clientedao.listCpfCnpj(conteudoSearch));
                adaptercliente.notifyDataSetChanged();
            } else if (!MaterialSpinnerCidade.getText().toString().isEmpty()){
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adaptercliente.setItems(clientedao.list(conteudoSearch));
                adaptercliente.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adaptercliente.setItems(clientedao.list(conteudoSearch));
                adaptercliente.notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulta_cliente, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultaCliente).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(this.getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint_cliente));


        return true;
    }

    public void CadastrarCliente(View view) {

        Intent i = new Intent(this.getApplication(), CadastroPessoaActivity.class);


        startActivity(i);


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
            case R.id.ConsultaCliente:
                Consultacliente();
                break;

        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (conteudoSearch != null){
            if (soExisteNumero(conteudoSearch)){

                adaptercliente.setItems(clientedao.listCpfCnpj(conteudoSearch));

                adaptercliente.notifyDataSetChanged();

            }else {
                adaptercliente.setItems(clientedao.list(conteudoSearch));

                adaptercliente.notifyDataSetChanged();
            }
        } else if (conteudocidade != null){
            adaptercliente.setItems(clientedao.listCidade(conteudocidade));
            adaptercliente.notifyDataSetChanged();
        }else {
            adaptercliente.setItems(clientedao.list());
            adaptercliente.notifyDataSetChanged();
        }

    }


    public void Consultacliente() {

        if (conteudocidade != null){
            adaptercliente.setItems(clientedao.listCidade(conteudocidade));
            adaptercliente.notifyDataSetChanged();
        }else {
            adaptercliente.setItems(clientedao.list());
            adaptercliente.notifyDataSetChanged();
        }

    }

    public void MaterialDialogCidade(final Pessoa pessoa) {
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title("Cliente")
                .items(R.array.Array_de_alterar)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text.equals("Editar")) {

                            EditarPessoa(getPessoa(pessoa));
                            dialog.dismiss();
                        } else if (text.equals("Excluir")) {
                            DeletarCliente();
                            dialog.dismiss();
                        }


                    }

                })
                .show();

    }

    public void EditarPessoa(Pessoa pessoa) {



        Intent i = new Intent(this.getBaseContext(), CadastroPessoaActivity.class);
        i.putExtra("alterarpessoa", pessoa);

        startActivity(i);

    }

    public Pessoa getPessoa(Pessoa pessoa){
        return new Pessoa(
                pessoa.getIdpessoa(),
                pessoa.getIdCidade(),
                pessoa.getCnpjCpf(),
                pessoa.getRazaoSocialNome(),
                pessoa.getFantasiaApelido(),
                pessoa.getInscriEstadualRG(),
                pessoa.getEndereco(),
                pessoa.getNumero(),
                pessoa.getBairro(),
                pessoa.getComplemento(),
                pessoa.getCep(),
                pessoa.getDataNascimento(),
                pessoa.getEmail(),
                pessoa.getDataUltimacompra(),
                pessoa.getValorUltimacompra(),
                pessoa.getDataCadastro());
    }


    public void DeletarCliente() {
        try {
            clientedao.delete(idpessoa);
            Toast.makeText(this, "Pessoa Excluido", Toast.LENGTH_SHORT).show();
            Consultacliente();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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


}
