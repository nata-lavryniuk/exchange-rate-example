package com.github.liia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

class AppTest {

	@DisplayName("Test currency result from tsn")
	@Test
	void tsnTest() throws IOException {
		String resultForTest = App.getCurrencyExchangeRateFromTSN();
		String exchangeRateAsString = resultForTest.split(";")[1];
		assertThat(resultForTest).contains("TSN;");
		assertThat(exchangeRateAsString).describedAs("exchangeRateAsString").isNotNull().isNotEmpty();
		BigDecimal exchangeRateAsBigDecimal = new BigDecimal(exchangeRateAsString);
		assertThat(exchangeRateAsBigDecimal).describedAs("exchangeRateAsBigDecimal").isGreaterThan(BigDecimal.valueOf(0));
	}

	@DisplayName("Test currency result from liga")
	@Test
	void ligaTest() throws IOException {
		String resultForTest = App.getCurrencyExchangeRateFromLiga();
		String exchangeRateAsString = resultForTest.split(";")[1];
		assertThat(resultForTest).contains("LIGA;");
		assertThat(exchangeRateAsString).describedAs("exchangeRateAsString").isNotNull().isNotEmpty();
		BigDecimal exchangeRateAsBigDecimal = new BigDecimal(exchangeRateAsString);
		assertThat(exchangeRateAsBigDecimal).describedAs("exchangeRateAsBigDecimal").isGreaterThan(BigDecimal.valueOf(0));
	}

}
