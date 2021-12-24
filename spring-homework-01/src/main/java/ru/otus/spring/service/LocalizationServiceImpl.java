package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppLocaleHolder;


@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource message;
    private final AppLocaleHolder locale;

    public LocalizationServiceImpl(MessageSource message, AppLocaleHolder appLocaleHolder) {
        this.message = message;
        this.locale = appLocaleHolder;
    }

    @Override
    public String getMessage(String code) {
        return message.getMessage(code, null, locale.getLocale());
    }
}
