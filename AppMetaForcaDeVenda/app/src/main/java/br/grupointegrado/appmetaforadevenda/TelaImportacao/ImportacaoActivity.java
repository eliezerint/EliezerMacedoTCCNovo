package br.grupointegrado.appmetaforadevenda.TelaImportacao;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Delayed;

import br.grupointegrado.appmetaforadevenda.Dao.AppDao;
import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.CondicaoPgtoDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.FilialDao;
import br.grupointegrado.appmetaforadevenda.Dao.ImportacaoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Dao.ProdutoDao;
import br.grupointegrado.appmetaforadevenda.Dao.VendedorDao;
import br.grupointegrado.appmetaforadevenda.Importacao.Importacao;
import br.grupointegrado.appmetaforadevenda.Importacao.ImportacaoDados;
import br.grupointegrado.appmetaforadevenda.MainActivity;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;

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
    private Boolean isimport = false;
    private ImportacaoDao importacaodao;
    private AppDao appdao;
    private Processo processo;
    private boolean selecionaImportacao = false;
    private PessoaDao pessodao;


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
        importacaodao = new ImportacaoDao(this);
        appdao = new AppDao(this);
        pessodao = new PessoaDao(this);


        processo = new Processo(this);

        SpnListarArquivos = (MaterialBetterSpinner) findViewById(R.id.SpinnerImportacao);
        btnImportar = (Button) findViewById(R.id.btnImportar);


        if (getIntent().getExtras() != null) {
            fazendoImportcao = getIntent().getExtras().getBoolean("Fazendo_importacao", false);
        }


        Listaimportar();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, Arquivos);
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
                if (Item != null) {
                    MaterialDialogImportacao(Item);


                    importacaoConcluida = true;


                } else {
                    SpnListarArquivos.setError("Seleciona um item");
                }

            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String action = intent.getAction();

        Uri data = getIntent().getData();

        if (action != null) {

            if (action.compareTo(Intent.ACTION_VIEW) == 0) {
                String scheme = intent.getScheme();
                ContentResolver resolver = getContentResolver();


                if (scheme.compareTo(ContentResolver.SCHEME_CONTENT) == 0) {
                    Uri uri = intent.getData();
                    String name = getContentName(resolver, data);

                    selecionaImportacao = true;

                    Log.v("tag", "Arquivo intent detectado: " + action + " : " + intent.getDataString() + " : " + intent.getType() + " : " + name);
                    InputStream input = null;
                    try {
                        input = resolver.openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    String importfilepath = nomeDiretorioImportacao().toString() + "/" + name;

                    InputStreamToFile(input, importfilepath);

                    Listaimportar();
                } else if (scheme.compareTo(ContentResolver.SCHEME_FILE) == 0) {
                    Uri uri = intent.getData();
                    String name = uri.getLastPathSegment();

                    selecionaImportacao = true;

                    Log.v("tag", "Arquivo intent detectado: " + action + " : " + intent.getDataString() + " : " + intent.getType() + " : " + name);
                    InputStream input = null;
                    try {
                        input = resolver.openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String importfilepath = nomeDiretorioImportacao().toString() + "/" + name;
                    InputStreamToFile(input, importfilepath);
                }


            }
        }
    }

    private String getContentName(ContentResolver resolver, Uri uri){
        Cursor cursor = resolver.query(uri, null, null, null, null);
        cursor.moveToFirst();
        int nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
        if (nameIndex >= 0) {
            return cursor.getString(nameIndex);
        } else {
            return null;
        }
    }

    private void InputStreamToFile(InputStream in, String file) {
        try {
            OutputStream out = new FileOutputStream(new File(file));

            int size = 0;
            byte[] buffer = new byte[1024];

            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }

            out.close();
        }
        catch (Exception e) {
            Log.e("MainActivity", "InputStreamToFile exception: " + e.getMessage());
        }
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
                } else if (selecionaImportacao) {
                    onBackPressed();
                }else {
                    finish();
                }
                break;
            case R.id.home:
                 if (selecionaImportacao) {
                     onBackPressed();
                 }
                break;
        }

        return true;
    }


    public void MaterialDialogImportacao(final String nomeArquivo) {
        new MaterialDialog.Builder(this)
                .title("Deseja Fazer a Importação ?")
                .customView(R.layout.layout_dialogs_fazerimportacao, true)
                .positiveText("Sim")
                .negativeText("Não")
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        arquivoTxt = new File(nomeDiretorioImportacao().toString(),nomeArquivo);

                        if (!ImportacaoDados.ValidaArquivo(arquivoTxt)) {
                            Toast.makeText(dialog.getContext(), "Arquivo Inválido", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            processo.execute(1, 1, 1);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public  void ImportararquivoEstado(String nomeArquivo){

        arquivoTxt = new File(nomeDiretorioImportacao().toString(),nomeArquivo);

        appdao.deletaRegistroBanco();



        ImportacaoDados.importarDados(arquivoTxt, estadodao, paisdao, cidadedao, produtodao,pessodao,filialdao,condpgtodao,vendedordao);

           importacaodao.saveImportacao(getImportacao(ConvesorUtil.stringParaDate(getDateTime()), 1000));







    }

    public Importacao getImportacao(Date data,Integer Totalregistro){
        return new Importacao(data,Totalregistro);

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
               new MaterialDialog.Builder(this)
                .title(R.string.progress_bar)
                       .content(R.string.Aguarde)
                       .progress(true, 0)
                       .show();



    }



    private static String getDateTime() {
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return (day +"/" + mes + "/" + ano);
    }

    private class Processo extends AsyncTask<Integer, String, Integer>
    {

        private ProgressDialog progress;
        private Context context;

        public Processo(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            //Cria novo um ProgressDialogo e exibe
            progress = new ProgressDialog(context);
            progress.setMessage("Aguarde...");
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.show();

            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... paramss)
        {

                try {

                    ImportararquivoEstado(Item);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            //Cancela progressDialogo
            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            //Atualiza mensagem
            progress.setMessage(values[0]);
        }
    }


    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}








