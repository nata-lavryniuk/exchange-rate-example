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
      saveToFile(getCurrencyExchangeRateFromLiga()); //https://finance.liga.net/currency
      saveToFile(getCurrencyExchangeRateFromIUA()); //https://finance.i.ua/
      TimeUnit.SECONDS.sleep(5);
    }
  }

  public static String getCurrencyExchangeRateFromTSN() throws IOException {
    return "TSN;" + parserService.parse("https://tsn.ua", "//span[@class='o-title-sub']/text()");
  }

  public static String getCurrencyExchangeRateFromLiga() throws IOException {
    return "LIGA;" + parserService.parse("https://finance.liga.net/currency", "//span[@class='usd']/text()");
  }

  public static String getCurrencyExchangeRateFromIUA() throws IOException {
    return "IUA;" + parserService.parse("https://finance.i.ua/", "//td[@class='sell_rate']///text()/span");
  }

  private static void saveToFile(String result) throws IOException {
    String datetime = LocalDateTime.now().format(DATE_TIME_FORMAT);
    try(PrintStream printStream = new PrintStream(new FileOutputStream("exchangeRate.csv",true))) {
      printStream.append(datetime + ";" + result + "\n" + "\n");
    } catch(IOException ex) {
      System.out.println(ex.getMessage());
    }
  }

}
