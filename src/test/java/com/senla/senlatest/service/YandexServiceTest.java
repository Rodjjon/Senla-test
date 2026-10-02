package com.senla.senlatest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YandexServiceTest {

    private YandexService yandexService;

    @BeforeEach
    void setUp() {
        yandexService = new YandexService();
    }

    @Test
    @DisplayName("Проверка расчета опций: 0 — обычный текст, 2 — с цифрами, 4 — с URL/email, 6 — с цифрами и URL")
    void calculateOptions_ShouldCalculateBitmaskCorrectly() {
        assertEquals(0, yandexService.calculateOptions("Привет мир"));
        assertEquals(2, yandexService.calculateOptions("Текст с числом 123"));
        assertEquals(4, yandexService.calculateOptions("Текст со ссылкой https://test.com"));
        assertEquals(4, yandexService.calculateOptions("Текст с почтой test@mail.ru"));
        assertEquals(6, yandexService.calculateOptions("Число 42 и ссылка https://test.com"));
    }

    @Test
    @DisplayName("Разбиение текста на фрагменты заданного размера")
    void splitText_ShouldDivideTextIntoChunks() {
        String text = "123456789012345";
        List<String> chunks = yandexService.splitText(text, 5);

        assertEquals(3, chunks.size());
        assertEquals("12345", chunks.get(0));
        assertEquals("67890", chunks.get(1));
        assertEquals("12345", chunks.get(2));
    }
}