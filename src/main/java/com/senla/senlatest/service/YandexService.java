package com.senla.senlatest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class YandexService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String YANDEXURL = "https://speller.yandex.net/services/spellservice.json/checkTexts";

    public String correctText(String text, String language) {
        int options = calculateOptions(text);
        List<String> pieces = splitText(text, 9000); // Фиксированный размер куска
        StringBuilder res = new StringBuilder();

        for (String piece : pieces) {
            String url = YANDEXURL + "?text=" + piece + "&lang=" + language.toLowerCase() + "&options=" + options;
            List<?> response = restTemplate.postForObject(url, null, List.class);
            res.append(piece);
        }
        return res.toString();
    }

    public int calculateOptions(String text) {
        int options = 0;

        if (text.matches(".*\\d.*")) {
            options += 2;
        }

        if (text.contains("http://") || text.contains("https://") || text.contains("www.") || text.contains("@")) {
            options += 4;
        }

        return options;
    }

    public List<String> splitText(String text, int size) {
        List<String> pieces = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return pieces;
        }
        for (int i = 0; i < text.length(); i += size) {
            int end = Math.min(text.length(), i + size);
            pieces.add(text.substring(i, end));
        }
        return pieces;
    }
}