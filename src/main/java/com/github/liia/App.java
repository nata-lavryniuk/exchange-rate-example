package com.github.liia;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class App {

  private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd/hh/mm");

  public static void main(String[] args) throws IOException, InterruptedException {
    while(true) {
      saveToFile(getCurrencyExchangeRateFromTSN());
//   saveToFile(getCurrencyExchangeRateFrom112());
      saveToFile(getCurrencyExchangeRateFromLiga()); //https://finance.liga.net/currency
      //saveToFile2(getCurrencyExchangeRateFromIUA()); //https://finance.i.ua/
      TimeUnit.SECONDS.sleep(5);
    }
  }

   private static String getCurrencyExchangeRateFromTSN() throws IOException {
    Document document = Jsoup.connect("https://tsn.ua").get();
//    String result = document.getElementsByClass("c-currency-table").select(".o-title-sub").first().text();
    return "TSN;" + Xsoup.compile("//span[@class='o-title-sub']/text()").evaluate(document).get();
  }

  private static String getCurrencyExchangeRateFromLiga() throws IOException {
    Document document1 = Jsoup.connect("https://finance.liga.net/currency").get();
//    String object1 = document1.getElementsByClass("usd").first().text();
    return "LIGA;" + Xsoup.compile("//span[@class='usd']/text()").evaluate(document1).get();
  }



  /**private static String getCurrencyExchangeRateFromIUA() throws IOException {
    Document document2 = Jsoup.connect("https://finance.i.ua/").get();
    System.out.println(document2);
    String object = document2.getElementsByClass("decrease").get(1).text();
    return Xsoup.compile("//span[@class='decrease']/text()").evaluate(document2).get();


  }*/

  private static void saveToFile(String result) throws IOException {
    String datetime = LocalDateTime.now().format(DATE_TIME_FORMAT);
    try(PrintStream printStream = new PrintStream(new FileOutputStream("exchangeRate.csv",true))) {
      printStream.append(datetime + ";" + result + "\n");
    } catch(IOException ex) {
      System.out.println(ex.getMessage());
    }
  }

}
