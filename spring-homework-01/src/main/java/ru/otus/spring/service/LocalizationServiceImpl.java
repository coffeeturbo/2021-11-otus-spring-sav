package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppLocale;


@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource message;
    private final AppLocale locale;

    public LocalizationServiceImpl(MessageSource message, AppLocale appLocale) {
        this.message = message;
        this.locale = appLocale;
    }

    @Override
    public String getMessage(String code) {
        return message.getMessage(code, null, locale.getLocale());
    }
}
