package br.grupointegrado.appmetaforadevenda.TelaExportacao;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Dao.PedidoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Exportacao.ExportarPedido;
import br.grupointegrado.appmetaforadevenda.Listagem.AdapterPedido;
import br.grupointegrado.appmetaforadevenda.Pedido.ItensPedido;
import br.grupointegrado.appmetaforadevenda.Pedido.Pedido;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pais;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.R;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;

public class ExportacaoActivity extends AppCompatActivity {

    private boolean fazendoExportacao = false;
    private boolean exportacaoConcluida = false;
    private Toolbar atoolbar;
    private PrintAttributes printAttributes;
    private Context context;

    private PaisDao paisdao;
    private EstadoDao estadodao;
    private CidadeDao cidadedao;
    private PessoaDao pessoadao;
    private PedidoDao pedidodao;


    private Pessoa pessoa;
    private Estado estado;
    private Pais pais;
    private Cidade cidade;
    private List<Telefone> listatelefone;
    private List<Pedido> listapedido;
    private List<ItensPedido> listaitens;

    private RecyclerView recyclerviewpedido;
    private AdapterPedido adapterpedido;

    private Integer idpedido;
    private String nomepedido;
    private ListView listview;
    private ArrayList<String> listArquivos = new ArrayList<>();

    private List lista;
    private String Item;
    private File arquivoTxtPDF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exportacao);



        atoolbar = (Toolbar) findViewById(R.id.tb_main);
        atoolbar.setTitle(" Exportação ");
        setSupportActionBar(atoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        estadodao = new EstadoDao(this);
        cidadedao = new CidadeDao(this);
        pessoadao = new PessoaDao(this);
        paisdao = new PaisDao(this);
        pedidodao = new PedidoDao(this);

        recyclerviewpedido = (RecyclerView)findViewById(R.id.RecyviewPedido);

        if(getIntent().getExtras() != null){
            fazendoExportacao = getIntent().getExtras().getBoolean("fazendo_exportacao",false);
        }


        recyclerviewpedido = (RecyclerView) findViewById(R.id.RecyviewPedido);

        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerviewpedido.setLayoutManager(llm);

        adapterpedido = new AdapterPedido(this, new ArrayList<Pedido>()) {
            @Override
            protected void onItemClickListener(int adapterPosition, int layoutPosition) {
                // evento de click simples


                Pedido pedido = adapterpedido.getItems().get(adapterPosition);
                if (fazendoExportacao) {
                    Intent data = new Intent();
                    data.putExtra("pedido_id", pedido.getIdpedido());
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    idpedido = pedido.getIdpedido();
                    nomepedido = pedido.getNome();
                    ExportarArquvo((pedido));

                }

            }

            @Override
            protected boolean onLongItemClickListener(int adapterPosition, int layoutPosition) {
                // evento e click longo
                Pedido  pedido = adapterpedido.getItems().get(adapterPosition);
                idpedido = pedido.getIdpedido();
                nomepedido = pedido.getNome();

                ExportarArquvo(pedido);




                return true;
            }
        };


        recyclerviewpedido.setAdapter(adapterpedido);


        nomeDiretorioExportacao();
        ConsultaPedido();

        ListaExportacao();
    }


    public void ExportarArquvo(final Pedido pedido) {
        new MaterialDialog.Builder(this)
                .title("Pedido")
                .items(R.array.Array_de_Exportacao)
                .negativeText("sair")
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text.equals("ExportarPDF")) {
                            criarPdf(pedido);
                            enviarEmail(pedido);

                            dialog.dismiss();
                        } else if (text.equals("ExportarTXT")) {

                            listaitens = pedidodao.listitens(pedido.getIdpedido().toString());
                            listapedido = pedidodao.listCodigo(pedido.getIdpedido().toString());
                            ExportarPedido.exportarTxt(listapedido, listaitens, nomeDiretorioExportacao());
                            PreencherLista(pedido);
                            enviarEmail(pedido);
                            dialog.dismiss();
                        }


                    }

                })
                .show();

    }
    public void enviarEmail(final Pedido pedido) {
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Exportar")
                .customView(R.layout.layout_dialogs_listaexportar, true)
                .positiveText("Salvar")
                .negativeText("Sair")
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();

         listview = (ListView)dialog.findViewById(R.id.listviewExportacao);

        ListaExportacao();

        lista = listArquivos;


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1, lista);

        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item = listview.getAdapter().getItem(position).toString();
                enviarArquivo(Item);

            }
        });

        dialog.show();

    }


    public void enviarArquivo(String nomeItem){

        arquivoTxtPDF = new File(nomeDiretorioExportacao().toString(),nomeItem);

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{pessoa.getEmail()});
        email.putExtra(Intent.EXTRA_SUBJECT, "");
        email.putExtra(Intent.EXTRA_TEXT,"" );
        email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + arquivoTxtPDF.getAbsolutePath()));
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Enviar email  :"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_exportacao, menu);
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

    @Override
    protected void onResume() {
        super.onResume();


            adapterpedido.setItems(pedidodao.list());
            adapterpedido.notifyDataSetChanged();



    }

    private void ConsultaPedido() {

        adapterpedido.setItems(pedidodao.list());
        adapterpedido.notifyDataSetChanged();


    }


    public void ListaExportacao(){

        File diretorio = new File(nomeDiretorioExportacao().toString());
        File[] arquivos = diretorio.listFiles();



        if(arquivos != null ) {
            listArquivos.clear();
            int length = arquivos.length;
            for(int i = 0; i < length; ++i)
            {
                File f = arquivos[i];
                if (f.isFile())
                {
                    listArquivos.add(f.getName());
                }
            }


        }

    }
  public void PreencherLista(Pedido pedido) {

      pessoa = pessoadao.retornaPessoa(pedido.getIdpessoa().toString());
      cidade = cidadedao.retornaCidade(pessoa.getIdCidade().toString());
      estado = estadodao.retornaEstado(cidade.getIdestado());
      pais = paisdao.retornaPais(estado.getIdpais());
      listatelefone = pessoadao.listTelefone(pessoa.getIdpessoa().toString());
      listapedido = pedidodao.listCodigoCliente(pessoa.getIdpessoa().toString());

  }

    public File nomeDiretorioExportacao() {

            File diretorio = android.os.Environment.getExternalStorageDirectory();
            diretorio = new File(diretorio, "Meta/Exportacao/");
            diretorio.mkdirs();
            return diretorio;
    }

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);


    //cria arquivo pdf
    public void criarPdf(Pedido pedido){

        PreencherLista(pedido);

        Document documento = new Document();
        File pdffile = new File(nomeDiretorioExportacao(), "/"+pessoa.getRazaoSocialNome()+".pdf");


        try {

            if (!pdffile.exists()){
                PdfWriter.getInstance(documento, new FileOutputStream(pdffile));


                documento.open();
                addMetaData(documento);
                addContent(documento,pais,estado,cidade,pessoa,listatelefone,listapedido,pedidodao);
                documento.close();



                documento.close();

                Toast.makeText(this, "Arquivo criado com sucesso", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Arquivo do cliente já existe", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addMetaData(Document document) {
        document.addTitle("Relatório de Cliente");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Meta 1.0");
        document.addCreator("Macedo Automação");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();

        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Relatório de cliente", catFont));

        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Relatório gerado by: " + System.getProperty("User.name") + ", " + new Date().toString(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph("Este documento contén dados restrito do cliente.",
                smallBold));

        addEmptyLine(preface, 8);



        document.add(preface);

        document.newPage();
    }


    private static void addContent(Document document,Pais pais,Estado estado,Cidade cidade, Pessoa pessoa,
                                   List<Telefone> listatelefone, List<Pedido> listapedido,PedidoDao pedidodao)throws DocumentException {




        Anchor anchor = new Anchor("Exportação", catFont);
        anchor.setName("Exportação de Dados");


        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Cadastro de Cidade ", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Nome   :"+ cidade.getDescricao()));
        subCatPart.add(new Paragraph("Estado :"+ cidade.getIdestado()));
        subCatPart.add(new Paragraph("País   :"+ cidade.getPais()));
        subCatPart.add(new Paragraph("IBGE   :"+ cidade.getIbge()));

        subPara = new Paragraph("Cadastro de cliente", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("CPF/CNPJ  : "+pessoa.getCnpjCpf()));
        subCatPart.add(new Paragraph("RG        : "+pessoa.getInscriEstadualRG()));
        subCatPart.add(new Paragraph("Nome/RazãoSocial     : "+pessoa.getRazaoSocialNome()));
        subCatPart.add(new Paragraph("Apelido/Nome Fatasia : "+pessoa.getFantasiaApelido()));
        subCatPart.add(new Paragraph("Data Nascimento      : "+pessoa.getDataNascimento().toString()));
        subCatPart.add(new Paragraph("Cidade     : "+pessoa.getIdCidade()));
        subCatPart.add(new Paragraph("Email      : "+pessoa.getEmail()));
        subCatPart.add(new Paragraph("Rua/Av     : "+pessoa.getEndereco()));
        subCatPart.add(new Paragraph("Bairro     : "+pessoa.getBairro()));
        subCatPart.add(new Paragraph("Numero     : "+pessoa.getNumero()));
        subCatPart.add(new Paragraph("Complemento: "+pessoa.getComplemento()));
        subCatPart.add(new Paragraph("CEP        : "+pessoa.getCep()));
        subCatPart.add(new Paragraph("Data Cadastro        : "+pessoa.getDataCadastro().toString()));
        subCatPart.add(new Paragraph("Data Utima venda     : "+pessoa.getDataUltimacompra().toString()));
        subCatPart.add(new Paragraph("Valor Utilma venda   : " +String.format("%.2f",pessoa.getValorUltimacompra())));

        subPara = new Paragraph("Cadastro de Telefone ", subFont);
        subCatPart = catPart.addSection(subPara);

        System.out.println(listatelefone.size());

        createList(subCatPart, listatelefone);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, listatelefone.size());
        subCatPart.add(paragraph);





        document.add(catPart);


        anchor = new Anchor("Pedido", catFont);
        anchor.setName("Pedido");


        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Dados do Pedido", subFont);
        subCatPart = catPart.addSection(subPara);

        createListPedido(subCatPart, listapedido, pedidodao);
        Paragraph paragraphPedido = new Paragraph();
        addEmptyLine(paragraphPedido, listapedido.size());
        subCatPart.add(paragraphPedido);





        document.add(catPart);

    }

    private static void createListPedido(Section subCatPart,List<Pedido>listapedido,PedidoDao pedidodao) {
        ListItem list = new ListItem();
        List<ItensPedido>listaItens = null;
        for(int x = 0; x < listapedido.size(); x++){
            list.add(new ListItem("  "));
            list.add(new ListItem("Pedido  : "+ (x+1)));
            list.add(new ListItem("Cliente  : "+ listapedido.get(x).getNome()));
            list.add(new ListItem("código condição Pgto : "+ listapedido.get(x).getIdCondicaopag()));
            list.add(new ListItem("código Filial   : "+ listapedido.get(x).getIdfilial()));
            list.add(new ListItem("código Vendedor : "+ listapedido.get(x).getIdvendedor()));
            list.add(new ListItem("Data Pedido : " + ConvesorUtil.dateParaString(listapedido.get(x).getDatapedido())));
            list.add(new ListItem("Total Pedido : " + String.format("%.2f", listapedido.get(x).getTotal())));

            listaItens = pedidodao.listitens(listapedido.get(x).getIdpedido().toString());
            for(int positens = 0; positens < listaItens.size(); positens++){
                list.add(new ListItem("Item : " +(positens+1)));
                list.add(new ListItem("Produto        : "+ listaItens.get(positens).getNomeproduto()));
                list.add(new ListItem("codigo Produto : "+ listaItens.get(positens).getIdProduto()));
                list.add(new ListItem("Quantidade     : "+ String.format("%.2f", listaItens.get(positens).getQuantidade())));
                list.add(new ListItem("Valor unitario : "+ String.format("%.2f", listaItens.get(positens).getVlunitario())));
                list.add(new ListItem("Percentual de Desconto : "+ String.format("%.2f", listaItens.get(positens).getDesconto())));
                list.add(new ListItem("Total : "+ String.format("%.2f",listaItens.get(positens).getTotal())));


            }

        }
        subCatPart.add(list);


    }

    private ListItem createListItens(Section subCatPart,List<ItensPedido>listaItens) {
        ListItem list = new ListItem();

        for(int x = 0; x < listaItens.size(); x++){
                list.add(new ListItem("Item : " +(x+1)));
                list.add(new ListItem("Produto        : "+ listaItens.get(x).getNomeproduto()));
                list.add(new ListItem("codigo Produto : "+ listaItens.get(x).getIdProduto()));
                list.add(new ListItem("Quantidade     : "+ listaItens.get(x).getQuantidade()));
                list.add(new ListItem("Valor unitario : "+ listaItens.get(x).getVlunitario()));
                list.add(new ListItem("Percentual de Desconto : "+ listaItens.get(x).getDesconto()));
                list.add(new ListItem("Total "+ listaItens.get(x).getTotal()));


        }
        return  list;


    }


    private static void createList(Section subCatPart,List<Telefone>listatelefone) {
        ListItem list = new ListItem();

        for(int x = 0; x < listatelefone.size(); x++){
            list.add(new ListItem("Telefone  : "+ (x+1)));
            list.add(new ListItem("Numero  : "+ listatelefone.get(x).getNumero()));
            list.add(new ListItem("Descrição : "+ listatelefone.get(x).getTipo()));

        }
        subCatPart.add(list);


    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }



}
