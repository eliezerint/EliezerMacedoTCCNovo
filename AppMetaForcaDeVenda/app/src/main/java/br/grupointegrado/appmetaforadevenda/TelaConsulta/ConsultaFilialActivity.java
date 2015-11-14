package br.grupointegrado.appmetaforadevenda.TelaConsulta;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import br.grupointegrado.appmetaforadevenda.Dao.FilialDao;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterFilial;
import br.grupointegrado.appmetaforadevenda.Pedido.Filial;
import br.grupointegrado.appmetaforadevenda.R;

public class ConsultaFilialActivity extends AppCompatActivity {



    private Toolbar atoolbar;
    private RecyclerView  RecyviewFilial;



    private FilialDao filialdao;
    private Filial filial;
    private AdapterFilial adapterfilial;


    private Integer idfilial;
    private String nomefilial;

    private boolean selecionandoFilial = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta__filial);

        atoolbar = (Toolbar) (findViewById(R.id.tb_main));
        atoolbar.setTitle("Consulta Filial");

        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filialdao = new FilialDao(this);





        RecyviewFilial = (RecyclerView)findViewById(R.id.RecyviewFilial);

        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        RecyviewFilial.setLayoutManager(llm);

        adapterfilial = new AdapterFilial(this, new ArrayList<Filial>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples


                 Filial filial = adapterfilial.getItems().get(adapterPosition);
                if (selecionandoFilial) {
                    Intent data = new Intent();
                    data.putExtra("filial_id", filial.getIdfilial());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    idfilial = filial.getIdfilial();
                    nomefilial = filial.getDescricao();

                }

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo
               Filial  filial = adapterfilial.getItems().get(adapterPosition);
                idfilial = filial.getIdfilial();
                nomefilial = filial.getDescricao();




                return true;
            }
        };


        RecyviewFilial.setAdapter(adapterfilial);

        RecyclerViewFilial();








    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consulta__filial, menu);
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
        }



        return true;
    }


    public void  RecyclerViewFilial(){


            adapterfilial.setItems(filialdao.list());
            adapterfilial.notifyDataSetChanged();





    }
}
