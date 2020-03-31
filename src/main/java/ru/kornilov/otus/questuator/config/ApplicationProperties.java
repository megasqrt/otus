package ru.kornilov.otus.questuator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("application")
class ApplicationProperties {
    private String version;
    private String locale;
    private String qpath;
    private String messageBaseName;
    private String encoding;
}
