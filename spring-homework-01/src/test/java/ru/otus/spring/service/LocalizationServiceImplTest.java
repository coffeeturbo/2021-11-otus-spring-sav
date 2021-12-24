package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.spring.config.AppLocaleHolder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalizationServiceImplTest {
    @Mock
    private MessageSource message;
    @Mock
    private AppLocaleHolder localeHolder;
    @InjectMocks
    private LocalizationServiceImpl localizationServiceImpl;


    @Test
    void testGetMessage() {
        when(localeHolder.getLocale())
                .thenReturn(null);

        when(message.getMessage("code", null, localeHolder.getLocale()))
                .thenReturn("expectedResult");

        String result = localizationServiceImpl.getMessage("code");
        Assertions.assertEquals("expectedResult", result);
    }
}