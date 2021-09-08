package ru.alfabank.currency.services;

import java.util.List;

/**
 * currency
 * 01.09.2021
 *
 * @author Anastasiya Zhalnina
 */
public interface ExchangeRatesService {
    /**
     * Возвращает список доступных для проверки валют
     *
     * @return список кодов
     */
    List<String> getCurrencyCodes();

    /**
     * Возвращает результат сравнения коэффициентов.
     *
     * @param currencyCode - код валюты
     * @return - результат сравнения коэффициентов. Если курсов или коэффициентов нет - возвращает 404.
     */
    int getKeyForTag(String currencyCode);

    /**
     * Обновление курсов
     */
    void updateRates();
}
