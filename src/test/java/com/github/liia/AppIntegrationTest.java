package com.github.liia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

class AppIntegrationTest {

	@DisplayName("Test currency result from tsn")
	@Test
	void tsnTest() throws IOException {
		String resultForTest = App.getCurrencyExchangeRateFromTSN();
		assertThat(resultForTest).contains("TSN;");
		assertExchangeRate(resultForTest);
	}

	@DisplayName("Test currency result from liga")
	@Test
	void ligaTest() throws IOException {
		String resultForTest = App.getCurrencyExchangeRateFromLiga();
		assertThat(resultForTest).contains("LIGA;");
		assertExchangeRate(resultForTest);
	}

	@DisplayName("Test currency result from liga")
	@Test
	void iUATest() throws IOException {
		String resultForTest = App.getCurrencyExchangeRateFromIUA();
		assertThat(resultForTest).contains("IUA;");
		assertExchangeRate(resultForTest);
	}

	private void assertExchangeRate(String resultForTest) {
		String exchangeRateAsString = resultForTest.split(";")[1];
		assertThat(exchangeRateAsString).describedAs("exchangeRateAsString").isNotNull().isNotEmpty();
		BigDecimal exchangeRateAsBigDecimal = new BigDecimal(exchangeRateAsString);
		assertThat(exchangeRateAsBigDecimal).describedAs("exchangeRateAsBigDecimal").isGreaterThan(BigDecimal.valueOf(0));
	}

}
