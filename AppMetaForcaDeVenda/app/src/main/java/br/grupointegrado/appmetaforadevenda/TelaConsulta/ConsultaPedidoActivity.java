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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.grupointegrado.appmetaforadevenda.Dao.PedidoDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterPedido;
import br.grupointegrado.appmetaforadevenda.Pedido.Pedido;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.TelaCadastro.CadastroPedidoActivity;

public class ConsultaPedidoActivity extends AppCompatActivity {


    private RecyclerView recyclerviewpedido;

    private Toolbar atoolbar;
    private FloatingActionButton PedidofloatButton;

    private Pedido pedido;
    private PedidoDao pedidodao;
    private AdapterPedido adapterpedido;

    private boolean selecionandoPedido = false;


    private Integer idpedido;
    private String nomepedido;
    private String conteudoSearch;
    private Integer numeroVerificaCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_pedido);


        atoolbar = (Toolbar) (findViewById(R.id.tb_main));
        atoolbar.setTitle("Consulta Pedido");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pedidodao = new PedidoDao(this);


        if (getIntent().getExtras() != null)
            selecionandoPedido = getIntent().getExtras().getBoolean("selecionar_pedido", false);


        PedidofloatButton = (FloatingActionButton) findViewById(R.id.PedidofloatButton);
        recyclerviewpedido = (RecyclerView) findViewById(R.id.RecyviewPedido);

        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerviewpedido.setLayoutManager(llm);

        adapterpedido = new AdapterPedido(this, new ArrayList<Pedido>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples


                Pedido pedido = adapterpedido.getItems().get(adapterPosition);
                if (selecionandoPedido) {
                    Intent data = new Intent();
                    data.putExtra("pedido_id", pedido.getIdpedido());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    idpedido = pedido.getIdpedido();
                    nomepedido = pedido.getNome();
                    EditarPedido(getPedido(pedido));

                }

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo
                Pedido  pedido = adapterpedido.getItems().get(adapterPosition);
                idpedido = pedido.getIdpedido();
                nomepedido = pedido.getNome();

                MaterialDialogPedido(pedido);




                return true;
            }
        };


        recyclerviewpedido.setAdapter(adapterpedido);

        PedidofloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarPedido(v);
            }
        });


        ConsultaPedido();
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
                Toast.makeText(this, conteudoSearch , Toast.LENGTH_SHORT).show();
                adapterpedido.setItems(pedidodao.listCpfCnpj(conteudoSearch));
                adapterpedido.notifyDataSetChanged();
            }else {
                Toast.makeText(this, conteudoQuery, Toast.LENGTH_SHORT).show();
                adapterpedido.setItems(pedidodao.list(conteudoSearch));
                adapterpedido.notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulta_pedido, menu);

        SearchManager searchmanager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.ConsultaPedido).getActionView();

        search.setSearchableInfo(searchmanager.getSearchableInfo(this.getComponentName()));

        search.setQueryHint(getResources().getString(R.string.search_hint_pedido));


        return true;
    }

    public void CadastrarPedido(View view) {

        Intent i = new Intent(this.getApplication(), CadastroPedidoActivity.class);


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
            case R.id.ConsultaPedido:
                conteudoSearch = null;
                ConsultaPedido();
                break;

        }

        return true;


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (conteudoSearch != null){
            if (soExisteNumero(conteudoSearch)){
                if (conteudoSearch.toString().length() > 10) {

                    adapterpedido.setItems(pedidodao.listCpfCnpj(conteudoSearch));

                    adapterpedido.notifyDataSetChanged();
                }else {
                    adapterpedido.setItems(pedidodao.listCodigo(conteudoSearch));

                    adapterpedido.notifyDataSetChanged();

                }

            }else {
                adapterpedido.setItems(pedidodao.list(conteudoSearch));

                adapterpedido.notifyDataSetChanged();
            }
        }else{
            adapterpedido.setItems(pedidodao.list());
            adapterpedido.notifyDataSetChanged();
        }
    }

    private void ConsultaPedido() {

        adapterpedido.setItems(pedidodao.list());
        adapterpedido.notifyDataSetChanged();


    }



    public void MaterialDialogPedido(final Pedido pedido) {
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title("Pedido")
                .items(R.array.Array_de_alterar)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text.equals("Editar")) {

                            EditarPedido(getPedido(pedido));
                            dialog.dismiss();
                        } else if (text.equals("Excluir")) {
                            DeletarPedido();
                            dialog.dismiss();
                        }


                    }

                })
                .show();

    }

    public void EditarPedido(Pedido pedido) {

        Intent i = new Intent(this.getBaseContext(), CadastroPedidoActivity.class);
        i.putExtra("alterarpedido", pedido);

        startActivity(i);

    }

    public Pedido getPedido(Pedido pedido){
        return new Pedido(
                pedido.getIdpedido(),
                pedido.getIdpessoa(),
                pedido.getIdvendedor(),
                pedido.getIdCondicaopag(),
                pedido.getIdfilial(),
                pedido.getDatapedido(),
                pedido.getTotal());

    }


    public void DeletarPedido() {
        try {
            pedidodao.delete(idpedido);
            Toast.makeText(this, "Pedido Excluido", Toast.LENGTH_SHORT).show();
            ConsultaPedido();
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
