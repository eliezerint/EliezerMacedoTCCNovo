package br.grupointegrado.appmetaforadevenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.AppDao;
import br.grupointegrado.appmetaforadevenda.Dao.CondicaoPgtoDao;
import br.grupointegrado.appmetaforadevenda.Dao.FilialDao;
import br.grupointegrado.appmetaforadevenda.Dao.ProdutoDao;
import br.grupointegrado.appmetaforadevenda.Dao.VendedorDao;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.Produtos.Produtos;


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
    private List<Produtos> lista;


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

        edtcdVendedor = (MaterialEditText)findViewById(R.id.edtcdVendedor);
        btentrar      = (Button)findViewById(R.id.btentrar);




        btentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtcdVendedor.getText().toString().isEmpty()) {
                    if (vendedordao.validaVendedor(edtcdVendedor.getText().toString())) {
                        idvendedortelainicial = Integer.parseInt(edtcdVendedor.getText().toString());

                        entrar();

                    } else
                        edtcdVendedor.setError("Vendedor nao autorizado");

                } else edtcdVendedor.setError("O campo vazio");
            }
        });

       lista = produtodao.list();
        if (lista.isEmpty()){
            produtoteste();
            filialTeste();
            condpgtoteste();
            vendedorteste();
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

    public void produtoteste(){
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

    public void vendedorteste(){
          vendedordao.saveVendedor("Lucas", 20.00);
         vendedordao.saveVendedor("Amanda",  30.00);
         vendedordao.saveVendedor("Eliezer", 45.00 );
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
