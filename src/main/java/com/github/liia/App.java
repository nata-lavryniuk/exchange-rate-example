package com.github.liia;

import com.github.liia.service.ParserService;
import org.yaml.snakeyaml.Yaml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// https://www.site24x7.com/tools/xml-to-yaml.html
public class App {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd/hh/mm");
    private static final ParserService parserService = new ParserService();

    public App() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        System.out.println(obj);
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExchangeRateParseException {
        Yaml yaml = new Yaml();
        InputStream inputStream = App.class
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        System.out.println(obj);

        System.out.println("Exchange rate application running");
        while (true) {
            saveToFile(getCurrencyExchangeRateFromTSN());
            saveToFile(getCurrencyExchangeRateFromLiga()); //https://finance.liga.net/currency
            saveToFile(getCurrencyExchangeRateFromIUA()); //https://finance.i.ua/
            TimeUnit.SECONDS.sleep(5);
        }
    }

    public static String getCurrencyExchangeRateFromTSN() throws ExchangeRateParseException {
        return "TSN;" + parserService.parse("https://tsn.ua", "//span[@class='o-title-sub']/text()");
    }

    public static String getCurrencyExchangeRateFromLiga() throws ExchangeRateParseException {
        return "LIGA;" + parserService.parse("https://finance.liga.net/currency", "//span[@class='usd']/text()");
    }

    public static String getCurrencyExchangeRateFromIUA() throws ExchangeRateParseException {
        return "IUA;" + parserService.parse("https://finance.i.ua/", "//td[@class='sell_rate']///text()/span");
    }

    private static void saveToFile(String result) throws IOException {
        String datetime = LocalDateTime.now().format(DATE_TIME_FORMAT);
        try (PrintStream printStream = new PrintStream(new FileOutputStream("exchangeRate.csv", true))) {
            printStream.append(datetime + ";" + result + "\n" + "\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
