package com.github.liia;

import com.github.liia.service.ParserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class App {

  private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd/hh/mm");
  private static final ParserService parserService = new ParserService();

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Exchange rate application running");
    while(true) {
      saveToFile(getCurrencyExchangeRateFromTSN());
//   saveToFile(getCurrencyExchangeRateFrom112());
      saveToFile(getCurrencyExchangeRateFromLiga()); //https://finance.liga.net/currency
      //saveToFile2(getCurrencyExchangeRateFromIUA()); //https://finance.i.ua/
      TimeUnit.SECONDS.sleep(5);
    }
  }

   private static String getCurrencyExchangeRateFromTSN() throws IOException {
    return "TSN;" + parserService.parse("https://tsn.ua", "//span[@class='o-title-sub']/text()");
  }

  private static String getCurrencyExchangeRateFromLiga() throws IOException {
    return "LIGA;" + parserService.parse("https://finance.liga.net/currency", "//span[@class='usd']/text()");
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
