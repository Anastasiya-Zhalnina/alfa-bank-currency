package ru.alfabank.currency.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * currency
 * 01.09.2021
 * Модель для работы с курсами валют. REST API курсов валют - https://docs.openexchangerates.org/
 *
 * @author Anastasiya Zhalnina
 */

@Data
@NoArgsConstructor
public class ExchangeRates {
    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private Map<String, Double> rates;
}
