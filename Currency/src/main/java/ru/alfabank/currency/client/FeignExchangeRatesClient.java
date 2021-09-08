package ru.alfabank.currency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.currency.models.ExchangeRates;

/**
 * currency
 * 03.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@FeignClient(name = "ERClient", url = "${openexchangerates.url.general}")
public interface FeignExchangeRatesClient extends ExchangeRatesClient {
    @Override
    @GetMapping("/latest.json")
    ExchangeRates getLatestRates(@RequestParam("app_id") String appId);

    @Override
    @GetMapping("/historical/{date}.json")
    ExchangeRates getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String appId);
}
