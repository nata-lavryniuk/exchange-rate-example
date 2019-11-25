package com.github.liia.service;

import com.github.liia.ExchangeRateParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;

public class ParserService {

  public String parse(String url, String xpath) throws ExchangeRateParseException {
    String result = null;
    try {
      Document document = Jsoup.connect(url).get();
      result = Xsoup.compile(xpath).evaluate(document).get();
    } catch (IOException e) {
      throw new ExchangeRateParseException(url, xpath, e);
    }
    if (result == null) {
      throw new ExchangeRateParseException(url, xpath);
    }
    return result;
  }
}
