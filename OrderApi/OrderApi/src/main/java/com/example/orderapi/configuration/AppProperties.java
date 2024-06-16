package com.example.orderapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
@Data
@EnableAsync
public class AppProperties {
    Kafka kafka;
    @Data
    public static class Kafka{
        String customerTopic;
    }
}
