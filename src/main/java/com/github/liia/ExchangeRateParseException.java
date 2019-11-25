package com.github.liia;

import java.io.IOException;

public class ExchangeRateParseException extends Exception {

    public ExchangeRateParseException(String url, String xpath, IOException e) {
        super("Unable to parse service " + url + " xpath: " + xpath + " because: " + e.getLocalizedMessage(), e);
    }

    public ExchangeRateParseException(String url, String xpath) {
        super("Unable to parse service " + url + " check xpath: " + xpath);
    }
}
