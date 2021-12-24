package ru.otus.spring.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class AppConfigHolderTest {
    AppConfigHolder appConfigHolder = new AppConfigHolder("questionsFileName", ".csv", 0, "ru-RU");

    @Test
    void testWhenNoArgsConstructor() {
        var configHolder = new AppConfigHolder();
        Assertions.assertInstanceOf(AppConfigHolder.class, configHolder);
    }

    @Test
    void testGetQuestionsFileName() {
        String result = appConfigHolder.getQuestionsFileName();
        Assertions.assertEquals("questionsFileName_ru_RU.csv", result);
    }

    @Test
    void testGetLocale() {
        appConfigHolder.setLocale("ru-RU");
        Locale result = appConfigHolder.getLocale();
        Assertions.assertEquals(Locale.forLanguageTag("ru-RU"), result);
    }

    @Test
    void testSetQuestionsFileName() {
        appConfigHolder.setQuestionsFileName("questionsFileName");
        Assertions.assertEquals("questionsFileName_ru_RU.csv", appConfigHolder.getQuestionsFileName());
    }

    @Test
    void testSetQuestionsFileExtension() {
        appConfigHolder.setQuestionsFileExtension(".ext");
        Assertions.assertEquals(".ext", appConfigHolder.getQuestionsFileExtension());
    }

    @Test
    void testSetSuccessPercent() {
        appConfigHolder.setSuccessPercent(100);
        Assertions.assertEquals(100, appConfigHolder.getSuccessPercent());
    }

}