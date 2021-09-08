package ru.alfabank.currency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * currency
 * 02.09.2021
 *
 * @author Anastasiya Zhalnina
 */
@Configuration
public class Config {
    @Bean("date_bean")
    public SimpleDateFormat formatForDate() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    @Bean("time_bean")
    public SimpleDateFormat formatForTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH");
    }

}
