package br.grupointegrado.appmetaforadevenda.Importacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.CondicaoPgtoDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.FilialDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Dao.PedidoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PessoaDao;
import br.grupointegrado.appmetaforadevenda.Dao.ProdutoDao;
import br.grupointegrado.appmetaforadevenda.Dao.VendedorDao;
import br.grupointegrado.appmetaforadevenda.Pedido.CondicaoPagamento;
import br.grupointegrado.appmetaforadevenda.Pedido.Filial;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pais;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Pessoa.Telefone;
import br.grupointegrado.appmetaforadevenda.Produtos.GrupoProdutos;
import br.grupointegrado.appmetaforadevenda.Produtos.Produtos;
import br.grupointegrado.appmetaforadevenda.Produtos.TabelaItenPreco;
import br.grupointegrado.appmetaforadevenda.Produtos.Tabelapreco;
import br.grupointegrado.appmetaforadevenda.Produtos.UnidadeMedida;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;
import br.grupointegrado.appmetaforadevenda.Vendedor.Vendedor;

import java.util.StringTokenizer;

/**
 * Created by eli on 03/12/2015.
 */
public class ImportacaoDados {


    public static Integer TotalRegistro;

    public static boolean ValidaArquivo(File arquivo) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(arquivo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String linha ;
            while ((linha = reader.readLine()) != null) {
                System.out.println("Linha do arquivo: " + linha);
                String[] partes = linha.split("\\|");

                if (partes[1].equals("Pais")){
                    fis.close();
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            return false;

    }
    public static void importarDados(File arquivo, EstadoDao estadodao, PaisDao paisdao, CidadeDao cidadedao,
                                     ProdutoDao produtodao, PessoaDao pessoadao,FilialDao filialdao ,CondicaoPgtoDao condicaodao, VendedorDao vendedordao) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(arquivo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String linha ;
            String tabela = null;
            while ((linha = reader.readLine()) != null) {
                System.out.println("Linha do arquivo: " + linha);
                String[] partes = linha.split("\\|");

                  if (partes[1].equals("Pais")){
                      tabela = partes[1];

                  }else if(partes[1].equals("Estado")){
                      tabela = partes[1];
                  }else if(partes[1].equals("Cidade")) {
                      tabela = partes[1];
                  }else if(partes[1].equals("Grupo")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("Unidade")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("Produto")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("Pessoa")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("Telefone")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("TabelaPreco")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("TabelaItemPreco")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("Filial")) {
                      tabela = partes[1];

                  }else if(partes[1].equals("CondicaoPgto")) {
                      tabela = partes[1];

                  }
                  else if(partes[1].equals("Vendedor")) {
                      tabela = partes[1];

                  }


                  if(tabela.equals("Pais")){
                      salvarPais(partes, paisdao);
                  }else if (tabela.equals("Estado")){
                      salvarEstado(partes, estadodao);
                  }else if (tabela.equals("Cidade")){
                      salvarCidade(partes,cidadedao);
                  }else if (tabela.equals("Grupo")){
                      salvarGrupo(partes,produtodao);
                  }else if (tabela.equals("Unidade")){
                      salvarUnidade(partes, produtodao);
                  }else if (tabela.equals("Produto")){
                      salvarProduto(partes, produtodao);
                  }else if (tabela.equals("Pessoa")){
                      salvarClientes(partes, pessoadao);
                  }else if (tabela.equals("Telefone")){
                      salvarTelefone(partes,pessoadao);
                  }
                  else if (tabela.equals("TabelaPreco")){
                      salvarTabela(partes,produtodao);
                  }
                  else if (tabela.equals("TabelaItemPreco")){
                      salvarTabelaItemPreco(partes,produtodao);
                  }
                  else if (tabela.equals("Filial")){
                      salvarFilial(partes,filialdao);
                  }
                  else if (tabela.equals("CondicaoPgto")){
                      salvarCondicaoPgto(partes,condicaodao);
                  }
                  else if (tabela.equals("Vendedor")){
                      salvarVendedor(partes,vendedordao);
                  }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();

            } catch (Exception e) {
            }
        }

    }


    public static void salvarPais(String[] partes, PaisDao paisdao){
        if (partes[1].equals("2")) {
            Pais pais = new Pais();
            pais.setIdpais(partes[2]);
            pais.setNome(partes[3]);

            paisdao.savePais(pais);
        } else if (partes[1].equals("9")) {

        }
    }


    public static void salvarEstado(String[] partes, EstadoDao estadodao){

        if (partes[1].equals("2")) {
            Estado estado = new Estado();
            estado.setIdpais(partes[2]);
            estado.setIdestado(partes[3]);
            estado.setDescricao(partes[4]);

            estadodao.saveEstado(estado);
        } else if (partes[1].equals("9")) {

        }
    }

    public static void salvarCidade(String[] partes, CidadeDao cidadedao){


        if (partes[1].equals("2")) {
            Cidade cidade = new Cidade();
            cidade.setIdcidade(Integer.parseInt(partes[2]));
            cidade.setIdestado(partes[3]);
            cidade.setDescricao(partes[4]);
            if (partes.length > 5 ){
                cidade.setIbge(partes[5]);
            }else {
                cidade.setIbge(" ");
            }
            cidade.setFlag("V");


            cidadedao.saveCidade(cidade);
        } else if (partes[1].equals("9")) {

        }
    }

    public static void salvarGrupo(String[] partes, ProdutoDao produtodao){
        if (partes[1].equals("2")) {
            GrupoProdutos grupo = new GrupoProdutos();
            grupo.setIdgrupoProduto(Integer.parseInt(partes[2]));
            grupo.setDescricao(partes[3]);

            produtodao.saveGrupoProduto(grupo);
        } else if (partes[1].equals("9")) {

        }
    }

    public static void salvarUnidade(String[] partes, ProdutoDao produtodao){
        if (partes[1].equals("2")) {
            UnidadeMedida unidade = new UnidadeMedida();
            unidade.setIdunidademedida(Integer.parseInt(partes[2]));
            unidade.setSigla(partes[3]);
            unidade.setDescricao(partes[4]);


            produtodao.saveUnidadeMedida(unidade);
        } else if (partes[1].equals("9")) {

        }
    }

    public static void salvarProduto(String[] partes, ProdutoDao produtodao){
        if (partes[1].equals("2")) {

            Produtos produto = new Produtos();
            produto.setIdproduto(Integer.parseInt(partes[2]));
            produto.setDescricao((partes[3]));
            produto.setIdUnidademedida(Integer.parseInt(partes[4]));
            produto.setIdgrupopoduto(Integer.parseInt(partes[5]));


            produtodao.saveProduto(produto);
        } else if (partes[1].equals("9")) {

        }
    }

    public static void salvarClientes(String[] partes, PessoaDao pessoadao) {
        if (partes[1].equals("2")) {

            Pessoa pessoa = new Pessoa();
            pessoa.setIdpessoa(Integer.parseInt(partes[2]));
            pessoa.setIdCidade(Integer.parseInt(partes[3]));
            pessoa.setCnpjCpf(partes[4]);
            pessoa.setRazaoSocialNome(partes[5]);
            pessoa.setFantasiaApelido(partes[6]);
            pessoa.setInscriEstadualRG(partes[7]);
            pessoa.setDataNascimento(ConvesorUtil.stringParaDate(partes[8]));
            pessoa.setEndereco(partes[9]);
            pessoa.setNumero(partes[10]);
            pessoa.setComplemento(partes[11]);
            pessoa.setCep(partes[12]);
            pessoa.setBairro(partes[13]);
            pessoa.setEmail(partes[14]);
            pessoa.setDataCadastro(ConvesorUtil.stringParaDate(partes[15]));
            pessoa.setDataUltimacompra(ConvesorUtil.stringParaDate(partes[16]));
            pessoa.setValorUltimacompra(ConvesorUtil.stringParaDouble(partes[17]));
            pessoa.setFlag("V");


            pessoadao.savePessoa(pessoa);
        } else if (partes[1].equals("9")) {

        }
    }
    public static void salvarTelefone(String[] partes, PessoaDao pessoadao){
        if (partes[1].equals("2")) {

            Telefone telefone = new Telefone();
            telefone.setIdTelefone(Integer.parseInt(partes[2]));
            telefone.setIdPessoa(Integer.parseInt(partes[3]));
            telefone.setCPF(partes[4]);
            telefone.setNumero((partes[5]));
            telefone.setTipo(partes[6]);


            pessoadao.saveTelefone(telefone);
        } else if (partes[1].equals("9")) {

        }
    }
    public static void salvarTabela(String[] partes, ProdutoDao produtodao){
        if (partes[1].equals("2")) {

            Tabelapreco tabela = new Tabelapreco();
            tabela.setIdTabelapreco(Integer.parseInt(partes[2]));
            tabela.setIdProduto(Integer.parseInt(partes[3]));
            tabela.setDescricao((partes[4]));




            produtodao.savePreco(tabela);
        } else if (partes[1].equals("9")) {

        }
    }
    public static void salvarTabelaItemPreco(String[] partes, ProdutoDao produtodao){
        if (partes[1].equals("2")) {

            TabelaItenPreco tabelaitem = new TabelaItenPreco();
            tabelaitem.setIdtabelaItenpreco(Integer.parseInt(partes[2]));
            tabelaitem.setIdtabelapreco(Integer.parseInt(partes[3]));
            tabelaitem.setDescricao((partes[4]));
            tabelaitem.setVlunitario(Double.parseDouble(partes[5]));


            produtodao.saveItemtabelaPreco(tabelaitem);
        } else if (partes[1].equals("9")) {

        }
    }
    public static void salvarFilial(String[] partes, FilialDao filialdao){
        if (partes[1].equals("2")) {

            Filial filial = new Filial();
            filial.setIdfilial(Integer.parseInt(partes[2]));
            filial.setDescricao((partes[3]));



            filialdao.saveFilial(filial);
        } else if (partes[1].equals("9")) {

        }
    }
    public static void salvarCondicaoPgto(String[] partes, CondicaoPgtoDao condicaodao){
        if (partes[1].equals("2")) {

             CondicaoPagamento condicao = new CondicaoPagamento();
            condicao.setIdcodicaopagamento(Integer.parseInt(partes[2]));
            condicao.setDescricao((partes[3]));
            condicao.setQuantidade(Double.parseDouble(partes[4]));
            condicao.setIntervalo(Integer.parseInt(partes[5]));


            condicaodao.saveCondPgto(condicao);
        } else if (partes[1].equals("9")) {

        }
    }
    public static void salvarVendedor(String[] partes, VendedorDao vendedordao){
        if (partes[1].equals("2")) {

            Vendedor vendededor = new Vendedor();
            vendededor.setIdvendedor(Integer.parseInt(partes[2]));
            vendededor.setNome((partes[3]));
            vendededor.setMax_desconto(Double.parseDouble(partes[4]));


            vendedordao.saveVendedor(vendededor);
        } else if (partes[1].equals("9")) {

        }
    }









}
