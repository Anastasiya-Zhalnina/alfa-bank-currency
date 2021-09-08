package ru.alfabank.currency.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alfabank.currency.client.GifClient;

import java.util.Map;

/**
 * currency
 * 03.09.2021
 * Сервис для работы с  https://giphy.com/
 *
 * @author Anastasiya Zhalnina
 */
@Service
public class GifServiceImpl implements GifService {
    private GifClient gifClient;

    @Value("${giphy.api.key}")
    private String apiKey;

    @Autowired
    public GifServiceImpl(GifClient gifClient) {
        this.gifClient = gifClient;
    }

    @Override
    public ResponseEntity<Map> getGif(String tag) {
        ResponseEntity<Map> result = gifClient.getRandomGif(this.apiKey, tag);
        result.getBody().put("compareResult", tag);
        return result;
    }
}
