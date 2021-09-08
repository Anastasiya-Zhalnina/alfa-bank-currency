package ru.alfabank.currency.services;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * currency
 * 01.09.2021
 *
 * @author Anastasiya Zhalnina
 */
public interface GifService {
    ResponseEntity<Map> getGif(String tag);
}
