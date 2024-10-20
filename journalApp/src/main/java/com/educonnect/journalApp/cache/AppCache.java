package com.educonnect.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.educonnect.journalApp.entity.ConfigJournalAppEntity;
import com.educonnect.journalApp.repository.ConfigJournalAppRespository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    public Map<String, String> APP_CACHE = new HashMap<>();

    @Autowired
    private ConfigJournalAppRespository configJournalAppRespository;

    // in init() we initialize appcache
    @PostConstruct
    public void init() {
        List<ConfigJournalAppEntity> all = configJournalAppRespository.findAll();
        log.info("Total records fetched: " + all.size()); // Add this to check if records are fetched correctly.
        log.info("Records: " + all);
        // log.info(all.get(0).getKey(), all.get(0).getValue());
        for (ConfigJournalAppEntity configJournalAppEntity : all) {

            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}
