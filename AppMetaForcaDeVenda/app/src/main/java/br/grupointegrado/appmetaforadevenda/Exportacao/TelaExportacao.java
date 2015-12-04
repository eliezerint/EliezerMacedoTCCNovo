package br.grupointegrado.appmetaforadevenda.Exportacao;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

import br.grupointegrado.appmetaforadevenda.R;

public class TelaExportacao extends AppCompatActivity {

    private boolean fazendoExportacao = false;
    private boolean exportacaoConcluida = false;
    private Toolbar atoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_esportacao);



        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle(" Exportação ");
        setSupportActionBar(atoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras() != null){
            fazendoExportacao = getIntent().getExtras().getBoolean("fazendo_exportacao",false);
        }

         criarPasta();
         criarPdf();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_esportacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (fazendoExportacao){
                    Intent data = new Intent();
                    data.putExtra("Exportacao", exportacaoConcluida);
                    setResult(RESULT_OK, data);
                    finish();
                }else {
                    finish();
                }
                break;
        }

        return true;
    }


    public File criarPasta() {


        File diretorio = new File(Environment.getExternalStorageDirectory(), "Meta/");
        diretorio = new File(diretorio, "Exportacao/");

        diretorio.mkdirs();

        return  diretorio;
    }



    //cria arquivo pdf
    public void criarPdf(){

        Document documento = new Document();
        try {
            File pdffile = new File(criarPasta(), "/RelatorioTeste.pdf");

            pdffile.mkdirs();

            PdfWriter.getInstance(documento, new FileOutputStream(pdffile));

            documento.open();

            documento.addCreator("sdaf");
            documento.addTitle("Meta");
            documento.addLanguage("Portugues");

            documento.close();

            Toast.makeText(this, "Arquivo criado com sucesso", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
