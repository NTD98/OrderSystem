package com.example.orderprocessingconsumer.configuration;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class KafkaConfig {
    private final AppProperties appProperties;
    @Bean
    public NewTopic createTopic() {
        return new NewTopic(appProperties.getKafka().getCustomerTopic(), 1, (short) 1);
    }
}
