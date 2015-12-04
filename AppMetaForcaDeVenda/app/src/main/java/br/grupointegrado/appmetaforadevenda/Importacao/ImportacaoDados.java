package br.grupointegrado.appmetaforadevenda.Importacao;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import br.grupointegrado.appmetaforadevenda.Dao.CidadeDao;
import br.grupointegrado.appmetaforadevenda.Dao.EstadoDao;
import br.grupointegrado.appmetaforadevenda.Dao.PaisDao;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Estado;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pais;

import java.util.StringTokenizer;

/**
 * Created by eli on 03/12/2015.
 */
public class ImportacaoDados {




    public static void importarCidade(File arquivo, CidadeDao cidadedao) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(arquivo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String linha ;
            StringTokenizer linhacomToken;
            while ((linha = reader.readLine()) != null) {
                System.out.println("Linha do arquivo: " + linha);
                String[] partes = linha.split("\\|");

                System.out.println("tamanho:" + partes.length);
                if (partes[1].equals("1")) {
                    System.out.println("Cod.Unimake:" + partes[1]);
                    System.out.println("DataImportação:" + partes[2]);
                    System.out.println("Hora:" + partes[3]);
                }else if(partes.length > 5){
                    System.out.println("Cod.Unimake:" + partes[1]);
                    System.out.println("ID:" + partes[2]);
                    System.out.println("UF:" + partes[3]);
                    System.out.println("Nome:" + partes[4]);
                    System.out.println("Nome:" + partes[5]);
                    }else {
                    System.out.println("Cod.Unimake:" + partes[1]);
                    System.out.println("ID:" + partes[2]);
                    System.out.println("UF:" + partes[3]);
                    System.out.println("Nome:" + partes[4]);

                }

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


                    cidadedao.saveCidade(cidade);
                } else if (partes[1].equals("9")) {
                    break;
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
    public static void importarEstado(File arquivo, EstadoDao estadodao, PaisDao paisdao, CidadeDao cidadedao) {
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
                  }


                  if(tabela.equals("Pais")){
                      salvarPais(partes, paisdao);
                  }else if (tabela.equals("Estado")){
                      salvarEstado(partes, estadodao);
                  }else if (tabela.equals("Cidade")){
                      salvarCidade(partes,cidadedao);
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
        System.out.println("tamanho estado:" + partes.length);
        if (partes[1].equals("1")) {
            System.out.println("Cod.Unimake:" + partes[1]);
            System.out.println("DataImportação:" + partes[2]);
            System.out.println("Hora:" + partes[3]);
        }else if(partes.length > 4){
            System.out.println("Cod.Unimake:" + partes[1]);
            System.out.println("ID:" + partes[2]);
            System.out.println("UF:" + partes[3]);
            System.out.println("Nome:" + partes[4]);
        }

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
        System.out.println("tamanho:" + partes.length);
        if (partes[1].equals("1")) {
            System.out.println("Cod.Unimake:" + partes[1]);
            System.out.println("DataImportação:" + partes[2]);
            System.out.println("Hora:" + partes[3]);
        }else if(partes.length > 5){
            System.out.println("Cod.Unimake:" + partes[1]);
            System.out.println("ID:" + partes[2]);
            System.out.println("UF:" + partes[3]);
            System.out.println("Nome:" + partes[4]);
            System.out.println("IBGE:" + partes[5]);
        }

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


            cidadedao.saveCidade(cidade);
        } else if (partes[1].equals("9")) {

        }
    }





}
