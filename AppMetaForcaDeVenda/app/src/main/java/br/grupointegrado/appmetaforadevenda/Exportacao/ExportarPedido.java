package br.grupointegrado.appmetaforadevenda.Exportacao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.grupointegrado.appmetaforadevenda.Pedido.ItensPedido;
import br.grupointegrado.appmetaforadevenda.Pedido.Pedido;
import br.grupointegrado.appmetaforadevenda.Pessoa.Cidade;
import br.grupointegrado.appmetaforadevenda.Pessoa.Pessoa;
import br.grupointegrado.appmetaforadevenda.Util.ConvesorUtil;

/**
 * Created by eli on 03/12/2015.
 */
public class ExportarPedido {


      public static void exportarTxt(List<Pedido> Listpedido, List<ItensPedido> Listitens,File diretorio){

          File arquivo = new File(diretorio, "/meta-exportacao.txt");

          FileOutputStream fos = null;

          try {
              fos = new FileOutputStream(arquivo);
              PrintWriter writer = new PrintWriter(fos);
              for (Pedido pedido : Listpedido) {

                  writer.append("|1|")
                          .append(getDateTime())
                          .append("|")
                          .append(getHoras())
                          .append("|")
                          .println();

                  writer.append("|2|")
                          .append(pedido.getIdpedido() + "")
                          .append("|")
                          .append(pedido.getIdpessoa() + " ")
                          .append("|")
                          .append(pedido.getIdvendedor()+" ")
                          .append("|")
                          .append(pedido.getIdfilial()+" ")
                          .append("|")
                          .append(pedido.getIdCondicaopag()+" ")
                          .append("|")
                          .append(ConvesorUtil.dateParaString(pedido.getDatapedido()))
                          .append("|")
                          .append(String.format("%.2f", pedido.getTotal()))
                          .append("|")
                          .println();

              }
              writer.append("|9|")
                      .append(Listpedido.size() + "")
                      .append("|")
                      .println();

              for (ItensPedido itens : Listitens) {

                  writer.append("|2|")
                          .append(itens.getIdpedido() + "")
                          .append("|")
                          .append(itens.getIdpessoa() + " ")
                          .append("|")
                          .append(itens.getIdvendedor() + " ")
                          .append("|")
                          .append(itens.getIdProduto() + " ")
                          .append("|")
                          .append(String.format("%.2f", itens.getQuantidade()))
                          .append("|")
                          .append(String.format("%.2f", itens.getDesconto()))
                          .append("|")
                          .append(String.format("%.2f", itens.getTotal()))
                          .append("|")
                          .println();

              }
              writer.append("|9|")
                      .append(Listitens.size() + "")
                      .append("|")
                      .println();


              writer.flush();



          } catch (Exception ex) {
              ex.printStackTrace();
          } finally {
              try {
                  fos.close();
              } catch (Exception e) {
              }
          }
      }

    public static void exportarItensTxt(List<ItensPedido> Listitens, File arquivo){

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(arquivo);
            PrintWriter writer = new PrintWriter(fos);
            for (ItensPedido itens : Listitens) {

                writer.append("|1|")
                        .append(getDateTime())
                        .append("|")
                        .append(getHoras())
                        .append("|")
                        .println();

                writer.append("|2|")
                        .append(itens.getIdpedido() + "")
                        .append("|")
                        .append(itens.getIdpessoa() + " ")
                        .append("|")
                        .append(itens.getIdvendedor() + " ")
                        .append("|")
                        .append(itens.getIdProduto() + " ")
                        .append("|")
                        .append(itens.getQuantidade().toString())
                        .append("|")
                        .append(itens.getDesconto().toString())
                        .append("|")
                        .append(itens.getTotal().toString())
                        .append("|")
                        .println();

            }
            writer.append("|9|")
                    .append(Listitens.size() + "")
                    .append("|")
                    .println();

            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }


    private static String getDateTime() {
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return (day +"/" + mes + "/" + ano);
    }

    private static String getHoras() {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE );
        int segundo = c.get(Calendar.SECOND);


        return (hora +":"+minuto+":"+segundo);
    }



}
