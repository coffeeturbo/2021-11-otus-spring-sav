package ru.otus.spring.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "app")
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppConfig {
    private String questionsFileName;
    private String questionsFileExtension;
    private int successPercent;
    private String locale;

    public String getQuestionsFileName() {
        var formattedLocale = locale.replace("-", "_");
        return questionsFileName + "_" + formattedLocale + questionsFileExtension;
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(locale);
    }
}
