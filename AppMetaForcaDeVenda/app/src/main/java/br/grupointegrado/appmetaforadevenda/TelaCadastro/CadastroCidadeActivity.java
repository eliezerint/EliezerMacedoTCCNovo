package br.grupointegrado.appmetaforadevenda.TelaCadastro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterSpinnerEstado;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterSpinnerPais;
import br.grupointegrado.appmetaforadevenda.Listagem.SpinnerArrayAdapter;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pais;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Util.Mask;
import eu.inmite.android.lib.validations.form.FormValidator;


import eu.inmite.android.lib.validations.form.annotations.MaxLength;
import eu.inmite.android.lib.validations.form.annotations.MinLength;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class CadastroCidadeActivity extends AppCompatActivity {

    private String conteudopais;
    private String conteudoestado;

    private AdapterSpinnerEstado adapter_estado;
    private SpinnerArrayAdapter spinneradapter;
    private ArrayAdapter<Pais> adapter_pais;

    private List<Estado> lista;
    private List<Pais> lista_pais;


    Toolbar toolbar;

    @NotEmpty(messageId =  R.string.Campo_vazio, order = 1)
    private MaterialBetterSpinner SpinnerPais;


    @NotEmpty(messageId =  R.string.Campo_vazio, order = 2)
    private MaterialBetterSpinner SpinnerEstado;

    @NotEmpty(messageId =  R.string.Campo_vazio, order = 3)
    @MaxLength(value = 60, messageId = R.string.max_cidade, order = 3)
    private MaterialEditText EditCidade;

    @MinLength(value = 7, messageId = R.string.min_ibge, order = 5)
    private MaterialEditText EditIbge;


    private CidadeDao cidadedao;
    private EstadoDao esatdodao;
    private PaisDao paisdao;
    private Cidade cidadealt;
    private Cidade cidade;
    private Integer idcidade;
    private TextWatcher ibgeMask;


    private Intent cidadeIntent;
    private long tempopresionadovoltar = 0;
    private TextWatcher Ibgemask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cidade);
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle("Cadastro de Cidade ");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SpinnerEstado = (MaterialBetterSpinner) findViewById(R.id.SpinnerEstado);
        SpinnerPais = (MaterialBetterSpinner) findViewById(R.id.SpinnerPais);
        EditCidade = (MaterialEditText) findViewById(R.id.EditCidade);
        EditIbge = (MaterialEditText) findViewById(R.id.EditIbge);

        cidadedao = new CidadeDao(this);
        esatdodao = new EstadoDao(this);
        paisdao = new PaisDao(this);

        if (getIntent().getExtras() != null)
        cidadealt = (Cidade) getIntent().getSerializableExtra("alterarcidade");

        if (cidadealt != null){
           setCidadealt(cidadealt);
        }

        ibgeMask = (Mask.insert("#######",EditIbge));
        EditIbge.addTextChangedListener(ibgeMask);




        lista_pais = paisdao.list();

        adapter_pais = new AdapterSpinnerPais(this, lista_pais);

        SpinnerPais.setAdapter(adapter_pais);

        SpinnerPais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conteudopais = SpinnerPais.getText().toString();
                if (conteudopais.equals("BR")) {
                    EditIbge.setVisibility(View.VISIBLE);
                } else {
                    EditIbge.setVisibility(View.GONE);
                }
                consultaestado();
            }

        });

        consultaestado();




        SpinnerEstado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conteudoestado = SpinnerEstado.getText().toString();

            }
        });



        EditIbge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                   if (!verificaEditIbge()){
                       EditIbge.setError("O campo IBGE não pode ser vazio");
                   }
                }
            }
        });


        FormValidator.startLiveValidation(this, findViewById(R.id.cad_cid_container),new SimpleErrorPopupCallback(getBaseContext()));

    }

    private Boolean verificaEditIbge() {
        if (SpinnerPais.getText().toString().isEmpty()){
            if(EditIbge.getText().toString().isEmpty()){
               return false;
            }
        }else if(SpinnerPais.getText().toString().equals("BR")){
            if(EditIbge.getText().toString().isEmpty()){
               return false;
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FormValidator.startLiveValidation(this, findViewById(R.id.cad_cid_container), new SimpleErrorPopupCallback(getBaseContext()));

    }

    @Override
    protected void onStop() {
        super.onStop();
        FormValidator.stopLiveValidation(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro_cidade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.Salvarcidade:
                //  esatdodao.saveEstado();


                save(cidadealt);
                break;
        }


        return true;

    }
    public void consultaestado(){

        if (SpinnerPais.getText().toString().isEmpty()){
            SpinnerEstado.setText("");
            lista = esatdodao.list("BR");

        }else  if (conteudopais != null) {
            SpinnerEstado.setText("");
            lista = esatdodao.list(conteudopais);

        }else {
            SpinnerEstado.setText(cidadealt.getIdestado());
            lista = esatdodao.list(cidadealt.getPais());
        }


        adapter_estado = new AdapterSpinnerEstado(this, lista);

        SpinnerEstado.setAdapter(adapter_estado);

        adapter_estado.notifyDataSetChanged();

    }







    //pegar dados da tela e passar para o modelo
    public Cidade getCidadealt() {
        return new Cidade(
                conteudopais,
                conteudoestado,
                idcidade,
                EditCidade.getText().toString(),
                Mask.unmask(EditIbge.getText().toString()));


    }
    public void setCidadealt(Cidade cidadealt){

        SpinnerPais.setText(cidadealt.getPais());
        SpinnerEstado.setText(cidadealt.getIdestado());
        idcidade = cidadealt.getIdcidade();
        EditCidade.setText(cidadealt.getDescricao());
        EditIbge.setText(cidadealt.getIbge());
    }


    public void save(Cidade cidadealt) {

        if (Validate() == true) {
            if (cidadealt == null) {
                try {
                    cidadedao.saveCidade(getCidadealt());
                    Toast.makeText(this, " salvo com sucesso ", Toast.LENGTH_SHORT).show();
                    LimparCampos();

                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }else {

                try {
                    cidadedao.Update(getCidadealt());
                    Toast.makeText(this, " alterado com sucesso ", Toast.LENGTH_SHORT).show();
                    finish();


                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }

        }


    }
    public boolean Validate(){
        final boolean isValid = FormValidator.validate(this, new SimpleErrorPopupCallback(getBaseContext(), true));
        if (isValid){
            if (verificaEditIbge()){
                   return  true;
            }else {
                EditIbge.setError("O campo IBGE não pode ser vazio");
            }
        }



        return false;
    }

    public void LimparCampos(){
        SpinnerPais.setText("");
        SpinnerEstado.setText("");
        EditCidade.setText("");
        EditIbge.setText("");

    }
    @Override
    public void onBackPressed() {

        long t = System.currentTimeMillis();
        if (t - tempopresionadovoltar > 4000) {
            tempopresionadovoltar = t;
            Toast.makeText(this.getBaseContext(), "Pressiona de volta para sair", Toast.LENGTH_SHORT).show();
        } else {

            super.onBackPressed();
        }
    }


}
