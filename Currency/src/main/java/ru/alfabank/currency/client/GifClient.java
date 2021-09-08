package ru.alfabank.currency.client;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * currency
 * 02.09.2021
 *
 * @author Anastasiya Zhalnina
 */
public interface GifClient {
    ResponseEntity<Map> getRandomGif(String apiKey, String tag);
}
