package ru.alfabank.currency.client;

import ru.alfabank.currency.models.ExchangeRates;

/**
 * currency
 * 02.09.2021
 *
 * @author Anastasiya Zhalnina
 */
public interface ExchangeRatesClient {
    /**
     * Получение последнего курса обмена валют
     *
     * @param appId - уникальный идентификатор приложения
     * @return - курс валют
     */
    ExchangeRates getLatestRates(String appId);

    /**
     * Получить исторические обменные курсы на любую дату
     *
     * @param date  - дата обменного курса
     * @param appId - уникальный идентификатор приложения
     * @return - курс валют
     */
    ExchangeRates getHistoricalRates(String date, String appId);
}
