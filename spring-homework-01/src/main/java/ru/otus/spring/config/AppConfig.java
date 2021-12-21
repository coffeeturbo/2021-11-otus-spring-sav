package ru.otus.spring.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppConfig {
    private String questionsFile;
    private int successPercent;
    private String locale;
}
