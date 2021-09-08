package ru.alfabank.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabank.currency.services.ExchangeRatesService;
import ru.alfabank.currency.services.GifService;

import java.util.List;
import java.util.Map;

/**
 * currency
 * 03.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@RestController
public class Controller {
    private ExchangeRatesService exchangeRatesService;
    private GifService gifService;

    @Value("${giphy.rich}")
    private String rich;
    @Value("${giphy.broke}")
    private String broke;
    @Value("error")
    private String error;
    @Value("zero")
    private String zero;

    @Autowired
    public Controller(ExchangeRatesService exchangeRatesService, GifService gifService) {
        this.exchangeRatesService = exchangeRatesService;
        this.gifService = gifService;
    }

    @GetMapping("/getCurrencyCodes")
    public List<String> getCodeRates() {
        return exchangeRatesService.getCurrencyCodes();
    }

    @GetMapping("/getgif/{currencyCode}")
    public ResponseEntity<Map> getGif(@PathVariable String currencyCode) {
        ResponseEntity<Map> result;
        int gifKey = 404;
        String tag = this.error;
        if (currencyCode != null) {
            gifKey = exchangeRatesService.getKeyForTag(currencyCode);
        }
        switch (gifKey) {
            case 1:
                tag = this.rich;
                break;
            case -1:
                tag = this.broke;
                break;
            case 0:
                tag = this.zero;
                break;
        }
        result = gifService.getGif(tag);
        return result;
    }
}
