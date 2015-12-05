package br.grupointegrado.appmetaforadevenda;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;


import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.AppDao;
import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.CondicaoPgtoDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.FilialDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Dao.ProdutoDao;
import br.grupointegrado.appmetaforadevenda.Dao.VendedorDao;
import br.grupointegrado.appmetaforadevenda.TelaImportacao.ImportacaoActivity;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.Vendedor.Vendedor;


public class MainActivity extends AppCompatActivity {

    private AppDao DAO;
     private VendedorDao vendedordao;
     private Estado estado;
     private Button btentrar;
     public static Integer idvendedortelainicial;
     private MaterialEditText edtcdVendedor;
     private Toolbar  atoolbar;
    private long tempopresionadovoltar;
    private FilialDao filialdao;
    private ProdutoDao produtodao;
    private CondicaoPgtoDao condpgtodao;
    private List<Vendedor> lista;
    private Uri FileUtils;
    private ArrayList<String> Arquivos = new ArrayList<>();
    private MaterialBetterSpinner SpnListarArquivos ;
    private CidadeDao cidadedao;
    private Cidade cidade;
    private File arquivoTxt;
    private EstadoDao estadodao;
    private PaisDao paisdao;
    private Boolean isImportacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle(" Login ");
        atoolbar.setLogo(R.drawable.ic_logo);
        setSupportActionBar(atoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DAO = new AppDao(this);
        vendedordao = new VendedorDao(this);
        filialdao = new FilialDao(this);
        condpgtodao = new CondicaoPgtoDao(this);
        produtodao = new ProdutoDao(this);
        cidadedao = new CidadeDao(this);
        estadodao = new EstadoDao(this);
        paisdao = new PaisDao(this);

        edtcdVendedor = (MaterialEditText)findViewById(R.id.edtcdVendedor);
        btentrar      = (Button)findViewById(R.id.btentrar);
        SpnListarArquivos = (MaterialBetterSpinner)findViewById(R.id.SpinnerImportacao);



        btentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtcdVendedor.getText().toString().isEmpty()) {
                    if (vendedordao.validaVendedor(edtcdVendedor.getText().toString())) {
                        idvendedortelainicial = Integer.parseInt(edtcdVendedor.getText().toString());

                        entrar();

                    } else
                        edtcdVendedor.setError("Vendedor nao autorizado");

                } else {
                    edtcdVendedor.setError("O campo vazio");
                    DialogsDadosImpor();
                }
            }
        });



        criaDiretorio(new String[]{"/Importacao", "/Exportacao"});


       lista = vendedordao.list();
        if (lista.isEmpty()){
            vendedorteste();
            condpgtoteste();
            filialTeste();
            precoTeste();
        }






    }



    public  void entrar(){


        Intent i = new Intent(this.getApplication(),MenuActivity.class);


        startActivity(i);



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                onBackPressed();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }



    @Override
    public void onBackPressed() {

        long t = System.currentTimeMillis();
        if (t - tempopresionadovoltar > 4000) {
            tempopresionadovoltar = t;
            Toast.makeText(this.getBaseContext(), "Pressiona de volta para sair da aplicação", Toast.LENGTH_SHORT).show();
        } else {

            super.onBackPressed();
            finish();
        }

    }
    private static final int REQUEST_TELA_IMPORTACAO = 1001;

    private void fazerImportacao() {
        Intent intent = new Intent(this, ImportacaoActivity.class);
        intent.putExtra("Fazendo_importacao", true);
        startActivityForResult(intent, REQUEST_TELA_IMPORTACAO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_TELA_IMPORTACAO == requestCode && resultCode == this.RESULT_OK) {
            isImportacao = data.getBooleanExtra("importacao", false);

        }
    }







public void DialogsDadosImpor() {
        boolean wrapInScrollView = true;
        MaterialDialog app = new MaterialDialog.Builder(this)
                .title("Importação ")
                .items(R.array.Carregar_Dados_De_Importacao)
                .positiveText("Sair")
                .autoDismiss(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Intent i;
                        if (text.equals("Importação")) {
                            fazerImportacao();
                            dialog.dismiss();
                        }
                    }
                })
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        dialog.dismiss();
                    }
                })
                .show();


    }
    public  void criaDiretorio (String[] subdiretorio){

        File diretorio = new File(Environment.getExternalStorageDirectory(), "Meta/");
        diretorio.mkdirs();
        for (int x = 0; x < 2; x++){

                String caminhoimportacao = diretorio.toString() + subdiretorio[x];

                new File(caminhoimportacao).mkdirs();


        }

        diretorio.canRead();





    }

    public  void criaDiretorioExportacao (){

        File diretorio = new File(Environment.getExternalStorageDirectory(), "Meta/");
        diretorio = new File(diretorio, "Exportacao/");

        diretorio.mkdirs();


    }



    public void vendedorteste(){
        vendedordao.saveVendedor("Lucas", 20.00);
        vendedordao.saveVendedor("Amanda",  30.00);
        vendedordao.saveVendedor("Eliezer", 45.00);
    }

     //Teste para inserir produtos
   /* public void produtoteste(){
        produtodao.saveGrupoProduto("Higiene pessoal");
        produtodao.saveGrupoProduto("Limpeza");

        produtodao.saveUnidadeMedida("metros", "M");
        produtodao.saveUnidadeMedida("Litros", "L");
        produtodao.saveUnidadeMedida("Kilo","Kg");
        produtodao.saveProduto("1", "1", "Papel Higienico leve 30m");
        produtodao.saveProduto("2", "3", "Sabão em Pó omo");
        produtodao.saveProduto("2","2","Detergente Ipe");

        produtodao.savePreco("1", "Atacado");
        produtodao.saveItemtabelaPreco("1", "Avista", 1.75);
        produtodao.saveItemtabelaPreco("1","Aprazo",2.00);

        produtodao.savePreco("1", "Varejo");
        produtodao.saveItemtabelaPreco("2", "Avista", 2.00);
        produtodao.saveItemtabelaPreco("2","Aprazo", 2.50);

        produtodao.savePreco("2", "Atacado");
        produtodao.saveItemtabelaPreco("3", "Avista", 3.75);
        produtodao.saveItemtabelaPreco("3","Aprazo",4.00);

        produtodao.savePreco("2", "Varejo");
        produtodao.saveItemtabelaPreco("4", "Avista", 5.00);
        produtodao.saveItemtabelaPreco("4","Aprazo", 6.20);

        produtodao.savePreco("3", "Atacado");
        produtodao.saveItemtabelaPreco("5", "Avista", 1.75);
        produtodao.saveItemtabelaPreco("5","Aprazo",2.50);

        produtodao.savePreco("3", "Varejo");
        produtodao.saveItemtabelaPreco("6", "Avista", 3.00);
        produtodao.saveItemtabelaPreco("6","Aprazo", 4.20);
    }


   */
    public void precoTeste(){
        produtodao.savePreco("1", "Atacado");
        produtodao.saveItemtabelaPreco("1", "Avista", 1.75);
        produtodao.saveItemtabelaPreco("1","Aprazo",2.00);

        produtodao.savePreco("1", "Varejo");
        produtodao.saveItemtabelaPreco("2", "Avista", 2.00);
        produtodao.saveItemtabelaPreco("2","Aprazo", 2.50);

        produtodao.savePreco("2", "Atacado");
        produtodao.saveItemtabelaPreco("3", "Avista", 3.75);
        produtodao.saveItemtabelaPreco("3","Aprazo",4.00);

        produtodao.savePreco("2", "Varejo");
        produtodao.saveItemtabelaPreco("4", "Avista", 5.00);
        produtodao.saveItemtabelaPreco("4","Aprazo", 6.20);

        produtodao.savePreco("3", "Atacado");
        produtodao.saveItemtabelaPreco("5", "Avista", 1.75);
        produtodao.saveItemtabelaPreco("5","Aprazo",2.50);

        produtodao.savePreco("3", "Varejo");
        produtodao.saveItemtabelaPreco("6", "Avista", 3.00);
        produtodao.saveItemtabelaPreco("6","Aprazo", 4.20);
    }




    public void filialTeste(){
        filialdao.saveFilial("501");
        filialdao.saveFilial("502");
    }

    public void condpgtoteste(){
        condpgtodao.saveCondPgto("30/60",2.0,30);
        condpgtodao.saveCondPgto("15/30/45",3.0,15);
        condpgtodao.saveCondPgto("30/60/90",3.0,30);
        condpgtodao.saveCondPgto("A vista",1.0,0);
        condpgtodao.saveCondPgto("15 dias",1.0,15);
        condpgtodao.saveCondPgto("A prazo",1.0,15);
    }





}
