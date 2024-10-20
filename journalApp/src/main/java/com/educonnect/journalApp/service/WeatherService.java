package com.educonnect.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.educonnect.journalApp.apiResponse.WeatherResponse;
import com.educonnect.journalApp.cache.AppCache;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        // log.info("APP_CACHE content: " +);
        String finalAPI = appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace("<city>", city)
                .replace("<apiKey>", apiKey);
        log.info(finalAPI);
        log.info("Final API URL: " + finalAPI); // Check if it's correct.

        try {

            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null,
                    WeatherResponse.class);

            WeatherResponse body = response.getBody();
            log.info("Body Message", body);
            return body;
        } catch (Exception e) {
            log.error("Error while calling weather API: ", e);
            throw new RuntimeException("Weather API call failed");
        }
    }

}
