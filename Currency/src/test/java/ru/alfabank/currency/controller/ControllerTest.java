package ru.alfabank.currency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.alfabank.currency.services.ExchangeRatesServiceImpl;
import ru.alfabank.currency.services.GifServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * currency
 * 06.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName(value = "Controller is working when")
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {
    @Value("${giphy.rich}")
    private String rich;
    @Value("${giphy.broke}")
    private String broke;
    @Value("${giphy.zero}")
    private String zero;
    @Value("${giphy.error}")
    private String error;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExchangeRatesServiceImpl exchangeRatesService;
    @MockBean
    private GifServiceImpl gifService;

    @Test
    public void return_list_of_currency_codes() throws Exception {
        List<String> answer = new ArrayList<>();
        answer.add("Test");
        Mockito.when(exchangeRatesService.getCurrencyCodes()).thenReturn(answer);
        mockMvc.perform((get("/getCurrencyCodes"))
                .content(mapper.writeValueAsString(answer))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0]").value("Test"));
    }

    @Test
    public void list_is_null() throws Exception {
        Mockito.when(exchangeRatesService.getCurrencyCodes()).thenReturn(null);
        mockMvc.perform(get("/getCurrencyCodes")
                .content(mapper.writeValueAsString(null))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void return_rich_gif() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.rich);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeRatesService.getKeyForTag(anyString()))
                .thenReturn(1);
        Mockito.when(gifService.getGif(this.rich))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/getgif/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.compareResult").value(this.rich));
    }

    @Test
    public void return_broke_gif() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.broke);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeRatesService.getKeyForTag(anyString()))
                .thenReturn(-1);
        Mockito.when(gifService.getGif(this.broke))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/getgif/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.compareResult").value(this.broke));
    }

    @Test
    public void return_zero_gif() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.zero);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeRatesService.getKeyForTag(anyString()))
                .thenReturn(0);
        Mockito.when(gifService.getGif(this.zero))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/getgif/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.compareResult").value(this.zero));
    }

    @Test
    public void return_error_gif_404() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.error);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeRatesService.getKeyForTag(anyString()))
                .thenReturn(404);
        Mockito.when(gifService.getGif(this.error))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/getgif/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.compareResult").value(this.error));
    }

    @Test
    public void return_error_gif_any_other_key() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", this.error);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeRatesService.getKeyForTag(anyString()))
                .thenReturn(3);
        Mockito.when(gifService.getGif(this.error))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/getgif/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.compareResult").value(this.error));
    }
}
