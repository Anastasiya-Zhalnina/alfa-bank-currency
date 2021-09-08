package ru.alfabank.currency.services;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alfabank.currency.client.FeignExchangeRatesClient;
import ru.alfabank.currency.models.ExchangeRates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

/**
 * currency
 * 06.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName(value = "Exchange Rates Service is working when")
public class ExchangeRatesServiceImplTest {
    @Value("${openexchangerates.base}")
    private String base;
    @Autowired
    private ExchangeRatesServiceImpl exchangeRatesService;
    @MockBean
    private FeignExchangeRatesClient exchangeRatesClient;

    private ExchangeRates prevRates;
    private ExchangeRates currentRates;

    @BeforeEach
    public void init() {
        int time = 1631005200;
        this.currentRates = new ExchangeRates();
        this.currentRates.setTimestamp(time);
        this.currentRates.setBase("BASE");
        Map<String, Double> currentRatesMap = new HashMap<>();
        currentRatesMap.put("RATE1", 0.1);
        currentRatesMap.put("RATE2", 0.5);
        currentRatesMap.put("RATE3", 1.0);
        currentRatesMap.put(this.base, 73.218);
        currentRatesMap.put("BASE", 1.0);
        this.currentRates.setRates(currentRatesMap);

        time = 1630972798;
        this.prevRates = new ExchangeRates();
        this.prevRates.setTimestamp(time);
        this.prevRates.setBase("BASE");
        Map<String, Double> prevRatesMap = new HashMap<>();
        prevRatesMap.put("RATE1", 0.1);
        prevRatesMap.put("RATE2", 1.0);
        prevRatesMap.put("RATE3", 0.5);
        prevRatesMap.put(this.base, 73.218);
        prevRatesMap.put("BASE", 1.0);
        this.prevRates.setRates(prevRatesMap);
    }

    @Test
    public void the_rate_got_higher() {
        Mockito.when(exchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(exchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKeyForTag("RATE3");
        assertEquals(1, result);
    }

    @Test
    public void the_rate_became_lower() {
        Mockito.when(exchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(exchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKeyForTag("RATE2");
        assertEquals(-1, result);
    }

    @Test
    public void the_course_has_not_changed() {
        Mockito.when(exchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(exchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKeyForTag("RATE1");
        assertEquals(0, result);
    }

    @Test
    public void got_null_on_input() {
        Mockito.when(exchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(exchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKeyForTag(null);
        assertEquals(404, result);
    }

    @Test
    public void the_wrong_currency_code_is_treated() {
        Mockito.when(exchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(exchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKeyForTag("nonExistentCurrencyCode");
        assertEquals(404, result);
    }

    @Test
    public void the_base_is_changed() {
        this.currentRates.getRates().put(this.base, 73.945);
        Mockito.when(exchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(exchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKeyForTag(this.base);
        assertEquals(0, result);
    }

    @Test
    public void get_list() {
        Mockito.when(exchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(exchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        List<String> result = exchangeRatesService.getCurrencyCodes();
        assertThat(result, containsInAnyOrder("RATE1", "RATE2", "RATE3", base, "BASE"));
    }
}
