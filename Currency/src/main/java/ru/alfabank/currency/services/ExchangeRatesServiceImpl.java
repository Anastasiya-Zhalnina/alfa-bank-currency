package ru.alfabank.currency.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfabank.currency.client.ExchangeRatesClient;
import ru.alfabank.currency.models.ExchangeRates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * currency
 * 02.09.2021
 * Сервис для работы с REST API курсов валют - https://docs.openexchangerates.org/
 *
 * @author Anastasiya Zhalnina
 */
@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    private ExchangeRates prevRates;
    private ExchangeRates currentRates;

    private ExchangeRatesClient exchangeRatesClient;
    private SimpleDateFormat date;
    private SimpleDateFormat time;
    @Value("${openexchangerates.app.id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;

    @Autowired
    public ExchangeRatesServiceImpl(ExchangeRatesClient exchangeRatesClient, @Qualifier("date_bean") SimpleDateFormat date, @Qualifier("time_bean") SimpleDateFormat time) {
        this.exchangeRatesClient = exchangeRatesClient;
        this.date = date;
        this.time = time;
    }

    @Override
    public List<String> getCurrencyCodes() {
        List<String> result = null;
        if (this.currentRates.getRates() != null) {
            result = new ArrayList<>(this.currentRates.getRates().keySet());
        }
        return result;
    }

    @Override
    public int getKeyForTag(String currencyCode) {
        this.updateRates();
        Double prevCoefficient = this.getCoefficient(this.prevRates, currencyCode);
        Double currentCoefficient = this.getCoefficient(this.currentRates, currencyCode);
        if (prevCoefficient != null && currentCoefficient != null) {
            return Double.compare(currentCoefficient, prevCoefficient);
        }
        return 404;

    }

    @Override
    public void updateRates() {
        long currentTime = System.currentTimeMillis();
        this.updateCurrentRates(currentTime);
        this.updatePrevRates(currentTime);
    }

    /**
     * Подсчёт коэффициента по отношению к установленной в этом приложении валютной базе.
     *
     * @param rates        - объект, содержащий все курсы конвертации или обмена для всех доступных (или запрашиваемых) валют.
     * @param currencyCode - код валюты международного формата.
     * @return коэффициент по  по отношению к установленной в этом приложении валютной базе.
     */
    private Double getCoefficient(ExchangeRates rates, String currencyCode) {
        Double coefficient = null;
        Map<String, Double> ratesMap;
        Double valueRate = null;
        Double valueBaseRate = null;
        Double baseRate = null;
        if (rates != null && rates.getRates() != null) {
            ratesMap = rates.getRates();
            valueRate = ratesMap.get(currencyCode);
            valueBaseRate = ratesMap.get(this.base);
            baseRate = ratesMap.get(rates.getBase());
        }
        if (valueRate != null && valueBaseRate != null && baseRate != null) {
            coefficient = new BigDecimal(
                    (baseRate / valueBaseRate) * valueRate
            )
                    .setScale(6, RoundingMode.UP)
                    .doubleValue();

        }
        return coefficient;
    }

    /**
     * Обновление текущих курсов, с точностью до часа.
     *
     * @param updateTime - время обновления
     */
    private void updateCurrentRates(long updateTime) {
        if (this.currentRates == null || !time.format(Long.valueOf(this.currentRates.getTimestamp()) * 1000).equals(time.format(updateTime))) {
            this.currentRates = exchangeRatesClient.getLatestRates(this.appId);
        }
    }

    /**
     * Обновление курсов предыдущего дня.
     *
     * @param updateTime - время обновления
     */
    private void updatePrevRates(long updateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(updateTime);
        String currentDate = date.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String prevDate = date.format(calendar.getTime());
        if (this.prevRates == null || (!date.format(Long.valueOf(this.prevRates.getTimestamp()) * 1000).equals(prevDate) &&
                !date.format(Long.valueOf(this.prevRates.getTimestamp()) * 1000).equals(currentDate))) {
            this.prevRates = exchangeRatesClient.getHistoricalRates(prevDate, appId);
        }
    }
}
