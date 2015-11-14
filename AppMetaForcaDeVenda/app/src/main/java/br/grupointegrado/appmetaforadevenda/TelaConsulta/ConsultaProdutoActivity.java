package br.grupointegrado.appmetaforadevenda.TelaConsulta;


import android.app.Dialog;
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
import java.util.Locale;

import br.grupointegrado.appmetaforadevenda.Dao.PedidoDao;
import br.grupointegrado.appmetaforadevenda.Dao.ProdutoDao;
import br.grupointegrado.appmetaforadevenda.Dao.VendedorDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterProduto;
import br.grupointegrado.appmetaforadevenda.MainActivity;
import br.grupointegrado.appmetaforadevenda.Pedido.ItensPedido;
import br.grupointegrado.appmetaforadevenda.Produtos.Grupos_Produtos;
import br.grupointegrado.appmetaforadevenda.Produtos.Produtos;
import br.grupointegrado.appmetaforadevenda.Produtos.TabelaItenPreco;
import br.grupointegrado.appmetaforadevenda.Produtos.Tabelapreco;
import br.grupointegrado.appmetaforadevenda.Produtos.UnidadeMedida;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Vendedor.Vendedor;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

public class ConsultaProdutoActivity extends AppCompatActivity {

    private RecyclerView recyclerviewProdutos;


    private MaterialEditText edit_produto;
    private MaterialEditText edit_vlunitario;
    private MaterialEditText edit_quantidade;
    private MaterialEditText edit_descontovalor;
    private MaterialEditText edit_descontopercentual;
    private MaterialEditText edit_valorTotal;
    private MaterialEditText edit_dialogvalorTotalcomDesconto;
    private MaterialBetterSpinner spinnerTp_tabela;
    private MaterialBetterSpinner spinnerTabela_preco;

    private Toolbar atoolbar;
    private AdapterProduto adapterproduto;
    private List<Produtos> lista_telefone;
    private List<Tabelapreco> listTpTabela;
    private List<TabelaItenPreco> listTpTabelaiten;
    private ArrayAdapter<TabelaItenPreco> tabelaiten_adapter;
    private ArrayAdapter<Tabelapreco> tptabela_adapter;
    private Double max_desconto;
    private Double max_acrescimo;
    private Integer posicaoTabelaPreco = 0;
    private Integer posicaoTabelaItenPreco = 0;
    private Integer idproduto;

    private ProdutoDao produtodao;
    private VendedorDao vendedordao;
    private Produtos produto;
    private PedidoDao pedidodao;

    private ItensPedido itens;


    private boolean selecionandoProduto = false;
    private String conteudoSearch;
    private List<Vendedor> listaVendedor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_produto);

        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle("Consulta de Produtos");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        produtodao = new ProdutoDao(this);
        vendedordao = new VendedorDao(this);

        final ItensPedido itenpedido = new ItensPedido();

      recyclerviewProdutos = (RecyclerView) findViewById(R.id.RecyviewProduto);

        if (getIntent().getExtras() != null)
            selecionandoProduto = getIntent().getExtras().getBoolean("selecionando_produto", false);


        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerviewProdutos.setLayoutManager(llm);

        adapterproduto = new AdapterProduto(this, new ArrayList<Produtos>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples

                Produtos produto = adapterproduto.getItems().get(adapterPosition);


                if (selecionandoProduto) {

                    idproduto = produto.getIdproduto();
                    insertItens(produto, itenpedido);


                } else {
                }
            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo

                Produtos produto = adapterproduto.getItems().get(adapterPosition);


                return true;
            }


        };

        recyclerviewProdutos.setAdapter(adapterproduto);

        consultaProduto();

        getDadosSearch(this.getIntent());
    }

    public void telItens(ItensPedido itens) {
        Intent data = new Intent();
        data.putExtra("itens_object", itens);
        setResult(RESULT_OK, data);
        finish();
    }

    private void insertItens(final Produtos produto, final ItensPedido itenpedido) {
        final Integer divisao = 100;


        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Itens")
                .customView(R.layout.layout_dialogs_itens, true)
                .positiveText("Salvar")
                .negativeText("Sair")
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        if(Validate()) {
                            if (Double.parseDouble(edit_dialogvalorTotalcomDesconto.getText().toString()) > 0){
                                edit_valorTotal.setText(edit_dialogvalorTotalcomDesconto.getText().toString());
                            }
                            itenpedido.setIdProduto(produto.getIdproduto());
                            itenpedido.setProduto(produto.getDescricao());

                            itenpedido.setVlunitario(Double.parseDouble(edit_vlunitario.getText().toString()));
                            itenpedido.setQuantidade(Double.parseDouble(edit_quantidade.getText().toString()));
                            itenpedido.setDesconto(Double.parseDouble(edit_descontovalor.getText().toString()));
                            itenpedido.setTotal(Double.parseDouble(edit_valorTotal.getText().toString()));
                            itenpedido.setTotalCdesconto(Double.parseDouble(edit_dialogvalorTotalcomDesconto.getText().toString()));


                            telItens(itenpedido);

                            idproduto = 0;

                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {

                        idproduto = 0;
                        dialog.dismiss();

                    }

                }).build();

        edit_vlunitario = (MaterialEditText) dialog.findViewById(R.id.edit_dialogvlunitario);
        edit_quantidade = (MaterialEditText) dialog.findViewById(R.id.edit_dialogquantidade);
        edit_descontopercentual = (MaterialEditText) dialog.findViewById(R.id.edit_dialogdescontoperc);
        edit_descontovalor = (MaterialEditText) dialog.findViewById(R.id.edit_dialogdescontovalor);
        edit_valorTotal = (MaterialEditText) dialog.findViewById(R.id.edit_dialogvalorTotal);
        edit_dialogvalorTotalcomDesconto = (MaterialEditText) dialog.findViewById(R.id.edit_dialogvalorTotalcomDesconto);
        spinnerTp_tabela  = (MaterialBetterSpinner) dialog.findViewById(R.id.SpinnerTpTabela);
        spinnerTabela_preco  = (MaterialBetterSpinner) dialog.findViewById(R.id.SpinnerPreco);



        listTpTabela =  produtodao.listPrecoVEnda(idproduto.toString());


        tptabela_adapter = new ArrayAdapter<Tabelapreco>(this, android.R.layout.simple_list_item_1, listTpTabela);


        spinnerTp_tabela.setAdapter(tptabela_adapter);

        if (listTpTabela != null ){
            spinnerTp_tabela.setText(listTpTabela.get(0).getDescricao());
            consultaTabelaPreco(listTpTabela.get(0).getIdTabelapreco(),0);
        }

        spinnerTp_tabela.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tabelapreco tabela = tptabela_adapter.getItem(position);
                consultaTabelaPreco(tabela.getIdTabelapreco(),0);
                if (!edit_quantidade.getText().toString().isEmpty())
                somaQuantidade();


            }
        });



        spinnerTabela_preco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TabelaItenPreco itenPreco = tabelaiten_adapter.getItem(position);
                consultaTabelaPreco(itenPreco.getIdtabelapreco(), position);
                if (!edit_quantidade.getText().toString().isEmpty())
                    somaQuantidade();
            }
        });


        listaVendedor = vendedordao.listId(MainActivity.idvendedortelainicial.toString());
        max_desconto = listaVendedor.get(0).getMax_desconto();


         if (listTpTabelaiten != null){
             //  edit_vlunitario.setText(listTpTabelaiten.get(0).getVlunitario().toString());
         }else if (listTpTabelaiten != null && !spinnerTabela_preco.getText().toString().isEmpty()){
             //edit_vlunitario.setText(listTpTabelaiten.get(posicaoTabelaItenPreco).getVlunitario().toString());
         }


        edit_quantidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    if (edit_quantidade.getText().toString().isEmpty()) {
                        edit_quantidade.setError("quantidade não pode ser vazio");
                    } else if (edit_quantidade.getText().toString().equals("0")) {
                        edit_quantidade.setError("no minimo 1 produto");
                    }else{
                        somaQuantidade();
                    }


            }
        });


        edit_descontopercentual.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus)
                    calculeDescontoPerc(dialog);
            }

        });


        edit_descontovalor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    calculeDescontoValor(dialog);
            }
        });


        dialog.show();




}

    private void calculeDescontoValor(MaterialDialog dialog) {
        double soma = 0.00, unitario = 0.00, descontoperc = 0, descontovalor = 0.00,
                total = 0.00, unitarionovo = 0.000, maximodesconto =0.00;

        if (!edit_descontovalor.getText().toString().isEmpty()) {
            unitario = Double.parseDouble(edit_vlunitario.getText().toString());
            descontovalor = Double.parseDouble(edit_descontovalor.getText().toString());

            soma = (unitario - descontovalor);

            descontoperc = ((unitario - soma) * 100) / unitario;
            maximodesconto = (max_desconto * unitario )/ 100;

            if (descontovalor > unitario) {
                edit_descontopercentual.setText("");
                edit_descontovalor.setText("");
                edit_descontovalor.setError("Desconto não pode ser maior " + maximodesconto);
                total = 0.00;
            } else if (descontovalor > maximodesconto) {
                edit_descontopercentual.setText("");
                edit_descontovalor.setText("");
                edit_descontovalor.setError("Desconto não pode ser maior " + maximodesconto);;
                total = 0.00;
            } else {

                unitarionovo = soma;

                edit_descontopercentual.setText(String.format(Locale.US, "%.2f", descontoperc));
                if (edit_quantidade.getText().toString().isEmpty()) {
                    total = unitarionovo * 1;
                    edit_quantidade.setText("1");
                } else {
                    total = unitarionovo * Double.parseDouble(edit_quantidade.getText().toString());
                }
            }
            edit_dialogvalorTotalcomDesconto.setText(String.format(Locale.US, "%.2f", total));

        }
    }

    private void somaQuantidade() {
        double soma = 0.00, valorUni = 0.00, qtde = 0.00, desconto = 0.00;

        if (edit_quantidade.getText().toString().isEmpty()) {
            valorUni = Double.parseDouble(edit_vlunitario.getText().toString());
            qtde = 1;
            soma = valorUni * qtde;
            edit_valorTotal.setText(String.format(Locale.US, "%.2f", soma));

        } else  if (!edit_quantidade.getText().toString().isEmpty()) {
            valorUni = Double.parseDouble(edit_vlunitario.getText().toString());
            qtde = Double.parseDouble(edit_quantidade.getText().toString());
            soma = valorUni * qtde;
            edit_valorTotal.setText(String.format(Locale.US, "%.2f", soma));
            if (!edit_descontovalor.getText().toString().isEmpty()){
                desconto = Double.parseDouble(edit_descontovalor.getText().toString());
                soma = (valorUni - desconto)*qtde;
                edit_dialogvalorTotalcomDesconto.setText(String.format(Locale.US, "%.2f", soma));
            }
        }



    }

    private void calculeDescontoPerc(MaterialDialog dialog) {
        double soma = 0.00, unitario = 0.00, descontoperc = 0.00, descontovalor = 0.00,
                total = 0.00, unitarionovo = 0.00, maxdesconto = 0.00;
        int divisao = 100;

        if (!edit_descontopercentual.getText().toString().isEmpty()) {
            descontoperc = Double.parseDouble(edit_descontopercentual.getText().toString());
            unitario = Double.parseDouble(edit_vlunitario.getText().toString());

            descontovalor = (unitario * descontoperc / divisao);
            soma = unitario - (unitario * descontoperc / divisao);

            maxdesconto = (max_desconto );

            if (descontoperc > 100) {
                edit_descontopercentual.setText("");
                edit_descontovalor.setText("");
                edit_descontopercentual.setError("Percentual não pode ser maior " + 100 + " %");
                total = 0.00;


            } else if (descontoperc > maxdesconto) {
                edit_descontopercentual.setText("");
                edit_descontovalor.setText("");
                edit_descontopercentual.setError("Percentual não pode ser maior " + maxdesconto);
                total = 0.00;
            }  else {

                unitarionovo = soma;
                edit_descontovalor.setText(String.format(Locale.US, "%.2f", descontovalor));
                if (edit_quantidade.getText().toString().isEmpty()) {
                    total = unitarionovo * 1;
                    edit_quantidade.setText("1");
                } else {
                    total = unitarionovo * Double.parseDouble(edit_quantidade.getText().toString());
                }

            }
            edit_dialogvalorTotalcomDesconto.setText(String.format(Locale.US, "%.2f", total));


        }
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
                Toast.makeText(this, conteudoSearch , Toast.LENGTH_SHORT).show();
                adapterproduto.setItems(produtodao.listIdproduto(conteudoSearch));
                adapterproduto.notifyDataSetChanged();
            }else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adapterproduto.setItems(produtodao.listNomeProduto(conteudoSearch));
                adapterproduto.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.menu_consulta__produto, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultaProduto).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(this.getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint));


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
            case R.id.ConsultaProduto:
                consultaProduto();
                break;

        }


        return true;
    }

    private void consultaProduto() {

        adapterproduto.setItems(produtodao.list());
        adapterproduto.notifyDataSetChanged();

    }

    public void consultaTabelaPreco(Integer idtabelapreco, Integer  position){

        if (spinnerTabela_preco.getText().toString().isEmpty()){
            listTpTabelaiten =  produtodao.listPrecoVEndaIten
                    (idtabelapreco.toString());
            spinnerTabela_preco.setText(listTpTabelaiten.get(0).getDescricao());
            edit_vlunitario.setText(listTpTabelaiten.get(0).getVlunitario().toString());

        }else {
            listTpTabelaiten =  produtodao.listPrecoVEndaIten
                    (idtabelapreco.toString());
            spinnerTabela_preco.setText(listTpTabelaiten.get(position).getDescricao());
            edit_vlunitario.setText(listTpTabelaiten.get(position).getVlunitario().toString());

        }




         tabelaiten_adapter = new ArrayAdapter<TabelaItenPreco>
                (this, android.R.layout.simple_list_item_1, listTpTabelaiten);


        spinnerTabela_preco.setAdapter(tabelaiten_adapter);

        tabelaiten_adapter.notifyDataSetChanged();

    }


    public ItensPedido getitens(ItensPedido itens) {
        return new ItensPedido(itens.getIdProduto(), itens.getProduto(),
                itens.getVlunitario(), itens.getQuantidade(), itens.getDesconto(), itens.getTotal(), itens.getTotalCdesconto());
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (conteudoSearch != null) {
            if (soExisteNumero(conteudoSearch)) {

                adapterproduto.setItems(produtodao.listIdproduto(conteudoSearch));

                adapterproduto.notifyDataSetChanged();

            } else {
                adapterproduto.setItems(produtodao.listNomeProduto(conteudoSearch));

                adapterproduto.notifyDataSetChanged();
            }
        }
        else {

            adapterproduto.setItems(produtodao.list());

            adapterproduto.notifyDataSetChanged();
        }

        }



    public boolean Validate() {
        if (edit_quantidade.getText().toString().isEmpty()) {
            edit_quantidade.setError("quantidade não pode ser vazia");

        }else if (edit_descontovalor.getText().toString().isEmpty()){
            edit_descontovalor.setText("0");
            edit_dialogvalorTotalcomDesconto.setText("0");
            edit_quantidade.setText("1");
            return true;
        }else if (!edit_descontovalor.getText().toString().isEmpty()) {

            return true;
        }


        return false;
    }





}













