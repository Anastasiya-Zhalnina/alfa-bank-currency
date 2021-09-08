package ru.alfabank.currency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * currency
 * 03.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@FeignClient(name = "giphyClient", url = "${giphy.url.general}")
public interface FeignGifClient extends GifClient {
    @Override
    @GetMapping("/random")
    ResponseEntity<Map> getRandomGif(@RequestParam("api_key") String apiKey, @RequestParam("tag") String tag);
}
