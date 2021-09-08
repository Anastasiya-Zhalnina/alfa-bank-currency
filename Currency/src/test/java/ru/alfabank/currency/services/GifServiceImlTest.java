package ru.alfabank.currency.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alfabank.currency.client.FeignGifClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * currency
 * 06.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName(value = "Successful server response")
@RunWith(SpringRunner.class)
@SpringBootTest
public class GifServiceImlTest {
    @Autowired
    private GifServiceImpl gifService;
    @MockBean
    private FeignGifClient gifClient;

    @Test
    public void when_get_gif() {
        ResponseEntity<Map> entity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
        Mockito.when(gifClient.getRandomGif(anyString(), anyString())).thenReturn(entity);
        ResponseEntity<Map> result = gifService.getGif("keyword");
        assertEquals("keyword", result.getBody().get("compareResult"));
    }
}
