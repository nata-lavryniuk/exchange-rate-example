package com.github.liia.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;

public class ParserService {

  public String parse(String url, String xpath) throws IOException {
    Document document = Jsoup.connect(url).get();
    return Xsoup.compile(xpath).evaluate(document).get();
  }


}
