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
import android.widget.Button;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.ExportacaoDao;
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

    private Button btnExportarTXT;
    private Button btnExportarPDF;

    private RecyclerView recyclerviewpedido;
    private AdapterPedido adapterpedido;

    private Integer idpedido;
    private String nomepedido;
    private ListView listview;
    private ArrayList<String> listArquivos = new ArrayList<>();

    private List lista;
    private String Item;
    private File arquivoTxtPDF;
    private List<Pessoa> listapessoa;
    private List<Cidade> listacidade;
    private ExportacaoDao exportacaodao;


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
        exportacaodao = new ExportacaoDao(this);

        recyclerviewpedido = (RecyclerView)findViewById(R.id.RecyviewPedido);

        btnExportarPDF = (Button)findViewById(R.id.btnExportarPDF);
        btnExportarTXT = (Button)findViewById(R.id.btnExportarTXT);

        if(getIntent().getExtras() != null){
            fazendoExportacao = getIntent().getExtras().getBoolean("fazendo_exportacao",false);
        }


        btnExportarTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaitens = pedidodao.listitensExportacao();
                listapedido = pedidodao.listPedidoExportacaoTXT();

                if (!listapedido.isEmpty() && !listaitens.isEmpty()) {

                    String name = "/meta-exportacao" + getDateTime() + ".txt";
                    ExportarPedido.exportarTxt(listapedido, listaitens, nomeDiretorioExportacao(), name,exportacaodao);

                    File diretoriocriado = new File(nomeDiretorioExportacao(), name);
                    if (diretoriocriado.exists()) {

                        enviarArquivo(name);
                    }
                }else {
                    Toast.makeText(v.getContext(),"Não tem pedido para ser exportado",Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnExportarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item = criarPdf();

                System.out.println(Item);
                if (Item != null){

                    enviarArquivo(Item);
                }

            }
        });

        nomeDiretorioExportacao();


        ListaExportacao();
    }





    public void enviarArquivo(String nomeItem){

        arquivoTxtPDF = new File(nomeDiretorioExportacao().toString(),nomeItem);

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        email.putExtra(Intent.EXTRA_SUBJECT, "");
        email.putExtra(Intent.EXTRA_TEXT,"" );
        email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + arquivoTxtPDF.getAbsolutePath()));
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Enviar email  :"));
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
    public String criarPdf(){

        listapessoa = pessoadao.listExportacaoPDF();
        listacidade = cidadedao.listExportacaoPDF();
        listatelefone = pessoadao.listTelefoneExportacaoPdf();
        listapedido = pedidodao.listPessoaExportacaoPDF();

      if ((!listapessoa.isEmpty()) && (!listacidade.isEmpty()) && (!listatelefone.isEmpty()) && (!listapedido.isEmpty())) {


          Document documento = new Document();
          File pdffile = new File(nomeDiretorioExportacao(), "/Exportação" + getDateTime() + ".pdf");

          String name = "/Exportação" + getDateTime() + ".pdf";

          try {


                  PdfWriter.getInstance(documento, new FileOutputStream(pdffile));


                  documento.open();
                  addMetaData(documento);
                  addContent(documento, listacidade, listapessoa, listatelefone, listapedido, pedidodao);


                  documento.close();

                  Toast.makeText(this, "Arquivo criado com sucesso", Toast.LENGTH_SHORT).show();

                  return name;




          } catch (Exception e) {
              e.printStackTrace();
          }

      }else {
          Toast.makeText(this, "Não tem Arquivo para exportação .PDF", Toast.LENGTH_SHORT).show();
      }
        return "";

    }

    private static String getDateTime() {
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return ("-" + day +"-" + mes  + "-" + ano);
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


    private static void addContent(Document document,List<Cidade>listacidade,List<Pessoa> listapessoa,
                                   List<Telefone> listatelefone, List<Pedido> listapedido,PedidoDao pedidodao)throws DocumentException {




        Anchor anchor = new Anchor("Exportação", catFont);
        anchor.setName("Exportação de Dados");


        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Cadastro de Cidade ", subFont);
        Section subCatPart = catPart.addSection(subPara);

        createListCidade(subCatPart, listacidade);
        Paragraph paragraphCidade = new Paragraph();
        addEmptyLine(paragraphCidade, listacidade.size());
        subCatPart.add(paragraphCidade);

        subPara = new Paragraph("Cadastro de cliente", subFont);
        subCatPart = catPart.addSection(subPara);

        createListPessoa(subCatPart, listapessoa);
        Paragraph paragraphPessoa = new Paragraph();
        addEmptyLine(paragraphPessoa, listapessoa.size());
        subCatPart.add(paragraphPessoa);

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

    private static void createListCidade(Section subCatPart,List<Cidade>listacidade) {
        ListItem list = new ListItem();

        for(int x = 0; x < listacidade.size(); x++){
            list.add(new ListItem("Cidade  : "+ (x+1)));
            list.add(new ListItem("Nome   :"+ listacidade.get(x).getDescricao()));
            list.add(new ListItem("Estado : "+ listacidade.get(x).getIdestado()));
            list.add(new ListItem("Pais : "+listacidade.get(x).getPais()));
            list.add(new ListItem("Ibge : "+ listacidade.get(x).getIbge()));

        }
        subCatPart.add(list);


    }

    private static void createListPessoa(Section subCatPart,List<Pessoa>listapessoa) {
        ListItem list = new ListItem();


        for(int x = 0; x < listapessoa.size(); x++){
            list.add(new ListItem("Cidade  : "+ (x+1)));
            list.add(new ListItem("CPF/CNPJ  : "+listapessoa.get(x).getCnpjCpf()));
            list.add(new ListItem("RG        : "+listapessoa.get(x).getInscriEstadualRG()));
            list.add(new ListItem("Nome/RazãoSocial     : "+listapessoa.get(x).getRazaoSocialNome()));
            list.add(new ListItem("Apelido/Nome Fatasia : "+listapessoa.get(x).getFantasiaApelido()));
            list.add(new ListItem("Cidade     : "+listapessoa.get(x).getIdCidade()));
            list.add(new ListItem("Email      : "+listapessoa.get(x).getEmail()));
            list.add(new ListItem("Rua/Av     : "+listapessoa.get(x).getEndereco()));
            list.add(new ListItem("Bairro     : "+listapessoa.get(x).getBairro()));
            list.add(new ListItem("Numero     : "+listapessoa.get(x).getNumero()));
            list.add(new ListItem("Complemento: "+listapessoa.get(x).getComplemento()));
            list.add(new ListItem("CEP        : "+listapessoa.get(x).getCep()));
            list.add(new ListItem("Data Cadastro        : "+listapessoa.get(x).getDataCadastro().toString()));
            list.add(new ListItem("Data Utima venda     : "+listapessoa.get(x).getDataUltimacompra().toString()));
            list.add(new ListItem("Valor Utilma venda   : " +String.format("%.2f", listapessoa.get(x).getValorUltimacompra())));


        }
        subCatPart.add(list);


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
