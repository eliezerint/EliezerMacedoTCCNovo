package br.grupointegrado.appmetaforadevenda.TelaImportacao;

import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.util.ArrayList;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.CondicaoPgtoDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.FilialDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Dao.ProdutoDao;
import br.grupointegrado.appmetaforadevenda.Dao.VendedorDao;
import br.grupointegrado.appmetaforadevenda.Importacao.ImportacaoDados;
import br.grupointegrado.appmetaforadevenda.R;

public class ImportacaoActivity extends AppCompatActivity {

    private ArrayList<String> Arquivos = new ArrayList<>();

    private Toolbar atoolbar;
    private VendedorDao vendedordao;
    private FilialDao filialdao;
    private CondicaoPgtoDao condpgtodao;
    private ProdutoDao produtodao;
    private CidadeDao cidadedao;
    private EstadoDao estadodao;
    private PaisDao paisdao;


    private MaterialBetterSpinner SpnListarArquivos;

    private Button btnImportar;
    private File arquivoTxt;
    private String Item;
    private boolean importacaoConcluida = false;
    private boolean fazendoImportcao = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_importacao);


        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle(" Importação ");
        setSupportActionBar(atoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vendedordao = new VendedorDao(this);
        filialdao = new FilialDao(this);
        condpgtodao = new CondicaoPgtoDao(this);
        produtodao = new ProdutoDao(this);
        cidadedao = new CidadeDao(this);
        estadodao = new EstadoDao(this);
        paisdao = new PaisDao(this);



        SpnListarArquivos = (MaterialBetterSpinner)findViewById(R.id.SpinnerImportacao);
        btnImportar = (Button)findViewById(R.id.btnImportar);


        if (getIntent().getExtras() != null){
            fazendoImportcao = getIntent().getExtras().getBoolean("Fazendo_importacao", false);
        }



        Listaimportar();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_dropdown_item_1line, Arquivos);
        SpnListarArquivos.setAdapter(arrayAdapter);

        SpnListarArquivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item = SpnListarArquivos.getAdapter().getItem(position).toString();



            }
        });



        btnImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Item != null){
                    ImportararquivoEstado(Item);
                    importacaoConcluida = true;
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_importacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (fazendoImportcao){
                        Intent data = new Intent();
                        data.putExtra("importacao", importacaoConcluida);
                        setResult(RESULT_OK, data);
                        finish();
                }else {
                    finish();
                }
                break;
        }

        return true;
    }


    public  void ImportararquivoCidade(String nomeArquivo){

        arquivoTxt = new File(nomeDiretorioImportacao().toString(),nomeArquivo);


        ImportacaoDados.importarCidade(arquivoTxt, cidadedao);
    }

    public  void ImportararquivoEstado(String nomeArquivo){

        arquivoTxt = new File(nomeDiretorioImportacao().toString(),nomeArquivo);

       // ImportacaoDados.importarDados(arquivoTxt, estadodao, paisdao, cidadedao, produtodao);

        progressBar();

    }

    public File nomeDiretorioImportacao() {

            File diretorio = android.os.Environment.getExternalStorageDirectory();
             diretorio = new File(diretorio, "Meta/Importacao/");
        diretorio.mkdirs();
            return diretorio;
        }


    public void Listaimportar (){

        File diretorio = new File(nomeDiretorioImportacao().toString());
        File[] arquivos = diretorio.listFiles();



        if(arquivos != null)
        {
            int length = arquivos.length;
            for(int i = 0; i < length; ++i)
            {
                File f = arquivos[i];
                if (f.isFile())
                {
                    Arquivos.add(f.getName());
                }
            }


        }

    }

   public void progressBar(){

       MaterialDialog app =
               new MaterialDialog.Builder(this)
                .title(R.string.progress_bar)
                .content(R.string.Aguarde)
                .progress(true, 0)
                       .show();
    }







}
