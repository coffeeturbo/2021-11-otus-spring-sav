package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocalizationServiceImplTest {

    @Autowired
    private LocalizationServiceImpl service;

    @Test
    void testGetMessage() {
        String result = service.getMessage("strings.app.input.name");
        Assertions.assertEquals("Введите свое ИМЯ", result);
    }
}