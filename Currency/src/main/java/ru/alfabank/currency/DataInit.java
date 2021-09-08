package ru.alfabank.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alfabank.currency.services.ExchangeRatesService;

import javax.annotation.PostConstruct;

/**
 * currency
 * 03.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@Component
public class DataInit {
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    public DataInit(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @PostConstruct
    public void firstDataInit() {
        exchangeRatesService.updateRates();
    }
}
