package br.grupointegrado.appmetaforadevenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import br.grupointegrado.appmetaforadevenda.Dao.AppDao;
import br.grupointegrado.appmetaforadevenda.TelaExportacao.ExportacaoActivity;
import br.grupointegrado.appmetaforadevenda.TelaImportacao.ImportacaoActivity;
import br.grupointegrado.appmetaforadevenda.TelaConsulta.*;

public class MenuActivity extends AppCompatActivity {


    private AppDao dao;
    private TextView tvEmpty;
    private Toolbar atoolbar;
    private Toolbar atoolbarBotton;
    private FloatingActionButton Cliente;
    private TextView tvCadastrarCliente;
    private FloatingActionButton Pedido;
    private FloatingActionButton Exportacao;
    private FloatingActionButton relatoriobutton;
    private FloatingActionButton dadospedidobutton;
    private long tempopresionadovoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle("Meta");
        atoolbar.setSubtitle("Força  De Venda");


        setSupportActionBar(atoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // voltar no toolbar


        tvEmpty = (TextView) findViewById(R.id.tvEmpty);

        Cliente = (FloatingActionButton) findViewById(R.id.Cliente);
        Pedido = (FloatingActionButton) findViewById(R.id.Pedido);
        Exportacao = (FloatingActionButton) findViewById(R.id.Exportacao);
        relatoriobutton = (FloatingActionButton) findViewById(R.id.Relatorio);
        dadospedidobutton = (FloatingActionButton) findViewById(R.id.DadosPedido);



        Cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogsCliente();
            }
        });

        Pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FazerPedido();
            }
        });

        Exportacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogsDadosImporExpor();
            }
        });


        relatoriobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogsDadosRelatorio();

            }
        });
        dadospedidobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaDadosPedido();

            }
        });

    }

    public void DialogsDadosImporExpor() {
        boolean wrapInScrollView = true;
        MaterialDialog app = new MaterialDialog.Builder(this)
                .title("Importação | Exportação")
                .items(R.array.Array_tp_Importação_consulta)
                .positiveText("Sair")
                .autoDismiss(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Intent i;
                        if (text.equals("Importação")) {
                           i = new Intent(dialog.getContext(), ImportacaoActivity.class);
                           startActivity(i);

                            dialog.dismiss();
                        } else if (text.equals("Exportação")) {
                            i = new Intent(dialog.getContext(), ExportacaoActivity.class);
                            startActivity(i);

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

    public void DialogsCliente() {
        boolean wrapInScrollView = true;
        MaterialDialog app = new MaterialDialog.Builder(this)
                .title("Dados de Clientes")
                .items(R.array.Array_tp_pessoa_consulta)
                .positiveText("Sair")
                .autoDismiss(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Intent i;
                        if (text.equals("Cliente")) {
                            i = new Intent(dialog.getContext(), ConsultaClienteActivity.class);
                            startActivity(i);
                            dialog.dismiss();
                        } else if (text.equals("Cidade")) {
                            i = new Intent(dialog.getContext(), ConsultaCidadeActivity.class);
                            startActivity(i);
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


    public void DialogsDadosRelatorio() {
        boolean wrapInScrollView = true;
        MaterialDialog app = new MaterialDialog.Builder(this)
                .title("Relatorio")
                .items(R.array.Array_tp_Relatorio_consulta)
                .positiveText("Sair")
                .autoDismiss(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Intent i;
                        if (text.equals("Relatorio de Pedido")) {
//                            i = new Intent(dialog.getContext(), ConsultaClienteActivity.class);
//                            startActivity(i);
                            Toast.makeText(dialog.getContext(),"em contrução", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else if (text.equals("Relatorio de Exportação")) {
                          /*  i = new Intent(dialog.getContext(), ConsultaCidadeActivity.class);
                            startActivity(i);*/
                            Toast.makeText(dialog.getContext(),"em contrução", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else if (text.equals("Relatorio de Importação")) {
                          /*  i = new Intent(dialog.getContext(), ConsultaCidadeActivity.class);
                            startActivity(i);*/
                            Toast.makeText(dialog.getContext(),"em contrução", Toast.LENGTH_SHORT).show();
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




    public void FazerPedido() {
        boolean wrapInScrollView = true;
        MaterialDialog app = new MaterialDialog.Builder(this)
                .title("Pedido")
                .items(R.array.Array_tp_pedido_consulta)
                .positiveText("Sair")
                .autoDismiss(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Intent i;
                        if (text.equals("Consulta Pedido")) {
                             i = new Intent(dialog.getContext(), ConsultaPedidoActivity.class);

                            startActivity(i);
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
    public void consultaDadosPedido() {
        boolean wrapInScrollView = true;
        MaterialDialog app = new MaterialDialog.Builder(this)
                .title("Consulta de Geral")
                .items(R.array.Array_tp_Dadospedido_consulta)
                .positiveText("Sair")
                .autoDismiss(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Intent i;
                         if (text.equals("Filial")) {
                            i = new Intent(dialog.getContext(), ConsultaFilialActivity.class);
                            startActivity(i);
                            dialog.dismiss();
                        } else if (text.equals("Vendedor")) {
                            i = new Intent(dialog.getContext(), ConsultaVendedorActivity.class);
                            startActivity(i);

                            dialog.dismiss();
                        } else if (text.equals("Condição Pagamento")) {
                            i = new Intent(dialog.getContext(), ConsultaCondicaPgtoActivity.class);
                             startActivity(i);
                            dialog.dismiss();
                        }
                        else if (text.equals("Produto")) {
                             i = new Intent(dialog.getContext(), ConsultaProdutoActivity.class);
                             startActivity(i);
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
            Toast.makeText(this.getBaseContext(), "Pressiona de volta para o login", Toast.LENGTH_SHORT).show();
        } else {

            super.onBackPressed();
            finish();
        }

    }
}


